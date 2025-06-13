package com.proyek.nusantara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.proyek.nusantara.adapters.ProfileKegiatanAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText etNama, etSearch;
    private TextView tvEmail;
    private Button btnLogout, btnUpdate;
    private FloatingActionButton fabback;
    private RecyclerView rvKegiatan;
    private ProfileKegiatanAdapter adapter;
    private FirebaseAuth mAuth;
    private SessionManager session;
    private String currentUid;
    private ImageView avatar;
    private ProgressBar loadingProgress;
    private Uri selectedImageUri;
    private List<Kegiatan> allKegiatan = new ArrayList<>();

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    avatar.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ProfileActivity", "onCreate dijalankan");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);
        mAuth = FirebaseAuth.getInstance();

        // Cek session
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return;
        }

        // Ambil currentUid dari session
        currentUid = session.getUserId();

        // Inisialisasi komponen UI
        etNama = findViewById(R.id.etNama);
        tvEmail = findViewById(R.id.tvEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnUpdate);
        fabback = findViewById(R.id.fabback);
        rvKegiatan = findViewById(R.id.rvKegiatanProfil);
        avatar = findViewById(R.id.avatar);
        loadingProgress = findViewById(R.id.loadingProgress);
        etSearch = findViewById(R.id.etSearch);

        loadingProgress.setVisibility(View.VISIBLE);

        // Ambil data pengguna dari Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String nama = documentSnapshot.getString("nama");
                        String email = documentSnapshot.getString("email");
                        String base64Image = documentSnapshot.getString("profileImage");

                        etNama.setText(nama);
                        tvEmail.setText("Email: " + (email != null ? email : "Tidak tersedia"));
                        if (base64Image != null && !base64Image.isEmpty()) {
                            Bitmap bitmap = base64ToBitmap(base64Image);
                            avatar.setImageBitmap(bitmap);
                        } else {
                            avatar.setImageResource(R.drawable.ic_launcher_foreground);
                        }
                    }
                    loadingProgress.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    loadingProgress.setVisibility(View.GONE);
                    Toast.makeText(this, "Gagal memuat data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Set up RecyclerView
        rvKegiatan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfileKegiatanAdapter(this, new ProfileKegiatanAdapter.OnProfileActionListener() {
            @Override
            public void onViewDetail(Kegiatan item) {
                Intent i = new Intent(ProfileActivity.this, DetailActivity.class);
                i.putExtra("postId", item.getId());
                startActivity(i);
            }

            @Override
            public void onEdit(Kegiatan item) {
                Intent i = new Intent(ProfileActivity.this, EditKegiatanActivity.class);
                i.putExtra("postId", item.getId());
                startActivity(i);
            }

            @Override
            public void onDelete(Kegiatan item) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Hapus Postingan")
                        .setMessage("Yakin ingin menghapus “" + item.getJudul() + "”?")
                        .setPositiveButton("Hapus", (d, w) -> deletePost(item.getId()))
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });
        rvKegiatan.setAdapter(adapter);

        // Load user posts
        loadUserPosts();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Back FAB
        fabback.setOnClickListener(v -> finish());

        // Logout button
        btnLogout.setOnClickListener(v -> confirmLogout());

        // button Update avatar
        avatar.setOnClickListener(v -> pickImage.launch("image/*"));
        btnUpdate.setOnClickListener(v -> {
            loadingProgress.setVisibility(View.VISIBLE);
            String newName = etNama.getText().toString().trim();

            if (newName.isEmpty()) {
                etNama.setError("Nama tidak boleh kosong");
                loadingProgress.setVisibility(View.GONE);
                return;
            }

            // Update name in Firestore
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentUid)
                    .update("nama", newName)
                    .addOnSuccessListener(aVoid -> {
                        if (selectedImageUri != null) {
                            String base64Image = uriToBase64(selectedImageUri);
                            FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(currentUid)
                                    .update("profileImage", base64Image)
                                    .addOnSuccessListener(aVoid2 -> {
                                        if (base64Image != null) {
                                            Bitmap bitmap = base64ToBitmap(base64Image);
                                            avatar.setImageBitmap(bitmap);
                                        }
                                        selectedImageUri = null;
                                        loadingProgress.setVisibility(View.GONE);
                                        Toast.makeText(ProfileActivity.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        loadingProgress.setVisibility(View.GONE);
                                        Toast.makeText(ProfileActivity.this, "Gagal mengupload foto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            loadingProgress.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        loadingProgress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "Gagal memperbarui profil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                if (query.isEmpty()) {
                    adapter.submitList(new ArrayList<>(allKegiatan));
                    return;
                }

                List<Kegiatan> filteredList = new ArrayList<>();
                for (Kegiatan kegiatan : allKegiatan) {
                    if (kegiatan.getJudul().toLowerCase().contains(query) ||
                            kegiatan.getIsiCerita().toLowerCase().contains(query)) {
                        filteredList.add(kegiatan);
                    }
                }
                adapter.submitList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya", (d, w) -> {
                    session.logout(); // Clear session
                    mAuth.signOut(); // Sign out from Firebase
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Tidak", null)
                .setCancelable(false)
                .show();
    }

    private void loadUserPosts() {
        Log.d("ProfileActivity", "Loading posts for userId: " + currentUid);
        loadingProgress.setVisibility(View.VISIBLE);

        FirebaseFirestore.getInstance()
                .collection("kegiatan")
                .whereEqualTo("userId", currentUid)
                .orderBy("tanggal", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    allKegiatan.clear();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Log.d("ProfileActivity", "Doc found: " + doc.getId());
                        Kegiatan kegiatan = doc.toObject(Kegiatan.class);
                        kegiatan.setId(doc.getId());
                        allKegiatan.add(kegiatan);
                    }
                    adapter.submitList(new ArrayList<>(allKegiatan));
                    loadingProgress.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    loadingProgress.setVisibility(View.GONE);
                    Toast.makeText(this, "Gagal memuat kegiatan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void deletePost(String postId) {
        loadingProgress.setVisibility(View.VISIBLE);

        FirebaseFirestore.getInstance()
                .collection("kegiatan")
                .document(postId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    loadingProgress.setVisibility(View.GONE);
                    Toast.makeText(this, "Kegiatan dihapus", Toast.LENGTH_SHORT).show();
                    loadUserPosts();
                })
                .addOnFailureListener(e -> {
                    loadingProgress.setVisibility(View.GONE);
                    Toast.makeText(this, "Gagal menghapus: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Akan mengambil ulang data dari Firestore
    @Override
    protected void onResume() {
        super.onResume();
        loadUserPosts();
    }

    private String uriToBase64(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap base64ToBitmap(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}