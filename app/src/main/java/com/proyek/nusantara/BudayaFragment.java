package com.proyek.nusantara;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.proyek.nusantara.provinsi.Aceh;
import com.proyek.nusantara.provinsi.Bali;
import com.proyek.nusantara.provinsi.BangkaBelitung;
import com.proyek.nusantara.provinsi.Banten;
import com.proyek.nusantara.provinsi.Bengkulu;
import com.proyek.nusantara.provinsi.DkiJakarta;
import com.proyek.nusantara.provinsi.Gorontalo;
import com.proyek.nusantara.provinsi.Jambi;
import com.proyek.nusantara.provinsi.JawaBarat;
import com.proyek.nusantara.provinsi.JawaTengah;
import com.proyek.nusantara.provinsi.JawaTimur;
import com.proyek.nusantara.provinsi.KalimantanBarat;
import com.proyek.nusantara.provinsi.KalimantanSelatan;
import com.proyek.nusantara.provinsi.KalimantanTengah;
import com.proyek.nusantara.provinsi.KalimantanTimur;
import com.proyek.nusantara.provinsi.KalimantanUtara;
import com.proyek.nusantara.provinsi.KepulauanRiau;
import com.proyek.nusantara.provinsi.Lampung;
import com.proyek.nusantara.provinsi.Maluku;
import com.proyek.nusantara.provinsi.MalukuUtara;
import com.proyek.nusantara.provinsi.NusaTenggaraBarat;
import com.proyek.nusantara.provinsi.NusaTenggaraTimur;
import com.proyek.nusantara.provinsi.Papua;
import com.proyek.nusantara.provinsi.PapuaBarat;
import com.proyek.nusantara.provinsi.PapuaBaratDaya;
import com.proyek.nusantara.provinsi.PapuaPegunungan;
import com.proyek.nusantara.provinsi.PapuaSelatan;
import com.proyek.nusantara.provinsi.PapuaTengah;
import com.proyek.nusantara.provinsi.Riau;
import com.proyek.nusantara.provinsi.SulawesiBarat;
import com.proyek.nusantara.provinsi.SulawesiSelatan;
import com.proyek.nusantara.provinsi.SulawesiTengah;
import com.proyek.nusantara.provinsi.SulawesiTenggara;
import com.proyek.nusantara.provinsi.SulawesiUtara;
import com.proyek.nusantara.provinsi.SumateraBarat;
import com.proyek.nusantara.provinsi.SumateraSelatan;
import com.proyek.nusantara.provinsi.SumateraUtara;
import com.proyek.nusantara.provinsi.Yogyakarta;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudayaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudayaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudayaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudayaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudayaFragment newInstance(String param1, String param2) {
        BudayaFragment fragment = new BudayaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Data budaya (nama dan id LinearLayout)
    private static class BudayaItem {
        String nama;
        int layoutId;
        BudayaItem(String nama, int layoutId) {
            this.nama = nama;
            this.layoutId = layoutId;
        }
    }

    private List<BudayaItem> budayaList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budaya, container, false);

        // Pindah menu profil
        ImageView imgProfile = view.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(v -> {
            Log.d("BudayaFragment", "Profile diklik");
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Aceh
        LinearLayout layoutAceh = view.findViewById(R.id.layoutAceh);
        layoutAceh.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Aceh.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sumatera Utara
        LinearLayout layoutSumateraUtara = view.findViewById(R.id.layoutSumateraUtara);
        layoutSumateraUtara.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SumateraUtara.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sumatera Selatan
        LinearLayout layoutSumateraSelatan = view.findViewById(R.id.layoutSumateraSelatan);
        layoutSumateraSelatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SumateraSelatan.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sumatera Barat
        LinearLayout layoutSumateraBarat = view.findViewById(R.id.layoutSumateraBarat);
        layoutSumateraBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SumateraBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Bengkulu
        LinearLayout layoutBengkulu = view.findViewById(R.id.layoutBengkulu);
        layoutBengkulu.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Bengkulu.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Riau
        LinearLayout layoutRiau = view.findViewById(R.id.layoutRiau);
        layoutRiau.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Riau.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kepulauan Riau
        LinearLayout layoutKepulauanRiau = view.findViewById(R.id.layoutKepulauanRiau);
        layoutKepulauanRiau.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KepulauanRiau.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Jambi
        LinearLayout layoutJambi = view.findViewById(R.id.layoutJambi);
        layoutJambi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Jambi.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Lampung
        LinearLayout layoutLampung = view.findViewById(R.id.layoutLampung);
        layoutLampung.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Lampung.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Bangka Belitung
        LinearLayout layoutBangkaBelitung = view.findViewById(R.id.layoutBangkaBelitung);
        layoutBangkaBelitung.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BangkaBelitung.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kalimantan Barat
        LinearLayout layoutKalimantanBarat = view.findViewById(R.id.layoutKalimantanBarat);
        layoutKalimantanBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KalimantanBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kalimantan Timur
        LinearLayout layoutKalimantanTimur = view.findViewById(R.id.layoutKalimantanTimur);
        layoutKalimantanTimur.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KalimantanTimur.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kalimantan Selatan
        LinearLayout layoutKalimantanSelatan = view.findViewById(R.id.layoutKalimantanSelatan);
        layoutKalimantanSelatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KalimantanSelatan.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kalimantan Tengah
        LinearLayout layoutKalimantanTengah = view.findViewById(R.id.layoutKalimantanTengah);
        layoutKalimantanTengah.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KalimantanTengah.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Kalimantan Utara
        LinearLayout layoutKalimantanUtara = view.findViewById(R.id.layoutKalimantanUtara);
        layoutKalimantanUtara.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KalimantanUtara.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Banten
        LinearLayout layoutBanten = view.findViewById(R.id.layoutBanten);
        layoutBanten.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Banten.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Dki Jakarta
        LinearLayout layoutDkiJakarta = view.findViewById(R.id.layoutDkiJakarta);
        layoutDkiJakarta.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DkiJakarta.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Jawa Barat
        LinearLayout layoutJawaBarat = view.findViewById(R.id.layoutJawaBarat);
        layoutJawaBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), JawaBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Jawa Tengah
        LinearLayout layoutJawaTengah = view.findViewById(R.id.layoutJawaTengah);
        layoutJawaTengah.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), JawaTengah.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Yogyakarta
        LinearLayout layoutYogyakarta = view.findViewById(R.id.layoutYogyakarta);
        layoutYogyakarta.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Yogyakarta.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Jawa Timur
        LinearLayout layoutJawaTimur = view.findViewById(R.id.layoutJawaTimur);
        layoutJawaTimur.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), JawaTimur.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Bali
        LinearLayout layoutBali = view.findViewById(R.id.layoutBali);
        layoutBali.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Bali.class);
            startActivity(intent);
        });
        // Pindah Menu Provinsi Nusa Tenggara Timur
        LinearLayout layoutNusaTenggaraTimur = view.findViewById(R.id.layoutNusaTenggaraTimur);
        layoutNusaTenggaraTimur.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NusaTenggaraTimur.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Nusa Tenggara Barat
        LinearLayout layoutNusaTenggaraBarat = view.findViewById(R.id.layoutNusaTenggaraBarat);
        layoutNusaTenggaraBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NusaTenggaraBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Gorontalo
        LinearLayout layoutGorontalo = view.findViewById(R.id.layoutGorontalo);
        layoutGorontalo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gorontalo.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sulawesi Barat
        LinearLayout layoutSulawesiBarat = view.findViewById(R.id.layoutSulawesiBarat);
        layoutSulawesiBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SulawesiBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sulawesi Tengah
        LinearLayout layoutSulawesiTengah = view.findViewById(R.id.layoutSulawesiTengah);
        layoutSulawesiTengah.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SulawesiTengah.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sulawesi Utara
        LinearLayout layoutSulawesiUtara = view.findViewById(R.id.layoutSulawesiUtara);
        layoutSulawesiUtara.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SulawesiUtara.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sulawesi Tenggara
        LinearLayout layoutSulawesiTenggara = view.findViewById(R.id.layoutSulawesiTenggara);
        layoutSulawesiTenggara.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SulawesiTenggara.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Sulawesi Selatan
        LinearLayout layoutSulawesiSelatan = view.findViewById(R.id.layoutSulawesiSelatan);
        layoutSulawesiSelatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SulawesiSelatan.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Maluku Utara
        LinearLayout layoutMalukuUtara = view.findViewById(R.id.layoutMalukuUtara);
        layoutMalukuUtara.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MalukuUtara.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Maluku
        LinearLayout layoutMaluku = view.findViewById(R.id.layoutMaluku);
        layoutMaluku.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Maluku.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua Barat
        LinearLayout layoutPapuaBarat = view.findViewById(R.id.layoutPapuaBarat);
        layoutPapuaBarat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PapuaBarat.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua
        LinearLayout layoutPapua = view.findViewById(R.id.layoutPapua);
        layoutPapua.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Papua.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua Tengah
        LinearLayout layoutPapuaTengah = view.findViewById(R.id.layoutPapuaTengah);
        layoutPapuaTengah.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PapuaTengah.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua Pegunungan
        LinearLayout layoutPapuaPegunungan = view.findViewById(R.id.layoutPapuaPegunungan);
        layoutPapuaPegunungan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PapuaPegunungan.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua Selatan
        LinearLayout layoutPapuaSelatan = view.findViewById(R.id.layoutPapuaSelatan);
        layoutPapuaSelatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PapuaSelatan.class);
            startActivity(intent);
        });

        // Pindah Menu Provinsi Papua Barat Daya
        LinearLayout layoutPapuaBaratDaya = view.findViewById(R.id.layoutPapuaBaratDaya);
        layoutPapuaBaratDaya.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PapuaBaratDaya.class);
            startActivity(intent);
        });

        // Inisialisasi data budaya sesuai id LinearLayout XML
        budayaList.add(new BudayaItem("ACEH", R.id.layoutAceh));
        budayaList.add(new BudayaItem("SUMATERA UTARA", R.id.layoutSumateraUtara));
        budayaList.add(new BudayaItem("SUMATERA SELATAN", R.id.layoutSumateraSelatan));
        budayaList.add(new BudayaItem("SUMATERA BARAT", R.id.layoutSumateraBarat));
        budayaList.add(new BudayaItem("BENGKULU", R.id.layoutBengkulu));
        budayaList.add(new BudayaItem("RIAU", R.id.layoutRiau));
        budayaList.add(new BudayaItem("KEPULAUAN RIAU", R.id.layoutKepulauanRiau));
        budayaList.add(new BudayaItem("JAMBI", R.id.layoutJambi));
        budayaList.add(new BudayaItem("LAMPUNG", R.id.layoutLampung));
        budayaList.add(new BudayaItem("BANGKA BELITUNG", R.id.layoutBangkaBelitung));
        budayaList.add(new BudayaItem("KALIMANTAN BARAT", R.id.layoutKalimantanBarat));
        budayaList.add(new BudayaItem("KALIMANTAN TIMUR", R.id.layoutKalimantanTimur));
        budayaList.add(new BudayaItem("KALIMANTAN SELATAN", R.id.layoutKalimantanSelatan));
        budayaList.add(new BudayaItem("KALIMANTAN TENGAH", R.id.layoutKalimantanTengah));
        budayaList.add(new BudayaItem("KALIMANTAN UTARA", R.id.layoutKalimantanUtara));
        budayaList.add(new BudayaItem("BANTEN", R.id.layoutBanten));
        budayaList.add(new BudayaItem("DKI JAKARTA", R.id.layoutDkiJakarta));
        budayaList.add(new BudayaItem("JAWA BARAT", R.id.layoutJawaBarat));
        budayaList.add(new BudayaItem("JAWA TENGAH", R.id.layoutJawaTengah));
        budayaList.add(new BudayaItem("DAERAH ISTIMEWA YOGYAKARTA", R.id.layoutYogyakarta));
        budayaList.add(new BudayaItem("JAWA TIMUR", R.id.layoutJawaTimur));
        budayaList.add(new BudayaItem("BALI", R.id.layoutBali));
        budayaList.add(new BudayaItem("NUSA TENGGARA TIMUR", R.id.layoutNusaTenggaraTimur));
        budayaList.add(new BudayaItem("NUSA TENGGARA BARAT", R.id.layoutNusaTenggaraBarat));
        budayaList.add(new BudayaItem("GORONTALO", R.id.layoutGorontalo));
        budayaList.add(new BudayaItem("SULAWESI BARAT", R.id.layoutSulawesiBarat));
        budayaList.add(new BudayaItem("SULAWESI TENGAH", R.id.layoutSulawesiTengah));
        budayaList.add(new BudayaItem("SULAWESI UTARA", R.id.layoutSulawesiUtara));
        budayaList.add(new BudayaItem("SULAWESI TENGGARA", R.id.layoutSulawesiTenggara));
        budayaList.add(new BudayaItem("SULAWESI SELATAN", R.id.layoutSulawesiSelatan));
        budayaList.add(new BudayaItem("MALUKU UTARA", R.id.layoutMalukuUtara));
        budayaList.add(new BudayaItem("MALUKU", R.id.layoutMaluku));
        budayaList.add(new BudayaItem("PAPUA BARAT", R.id.layoutPapuaBarat));
        budayaList.add(new BudayaItem("PAPUA", R.id.layoutPapua));
        budayaList.add(new BudayaItem("PAPUA TENGAH", R.id.layoutPapuaTengah));
        budayaList.add(new BudayaItem("PAPUA PEGUNUNGAN", R.id.layoutPapuaPegunungan));
        budayaList.add(new BudayaItem("PAPUA SELATAN", R.id.layoutPapuaSelatan));
        budayaList.add(new BudayaItem("PAPUA BARAT DAYA", R.id.layoutPapuaBaratDaya));

        EditText searchEditText = view.findViewById(R.id.searchEditText);
        GridLayout gridLayout = view.findViewById(R.id.gridLayoutBudaya);

        // Listener untuk tombol Enter
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAndSortBudaya(view, searchEditText.getText().toString().trim());
                return true;
            }
            return false;
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                searchAndSortBudaya(view, s.toString().trim());
            }
        });

        return view;
    }

    private void searchAndSortBudaya(View view, String query) {
        String lowerQuery = query.toLowerCase();
        List<BudayaItem> exactMatch = new ArrayList<>();
        List<BudayaItem> partialMatch = new ArrayList<>();

        for (BudayaItem item : budayaList) {
            String name = item.nama.toLowerCase();
            if (name.equals(lowerQuery)) {
                exactMatch.add(item);
            } else if (name.contains(lowerQuery) && !lowerQuery.isEmpty()) {
                partialMatch.add(item);
            }
        }

        // Gabungkan hasil
        List<BudayaItem> result = new ArrayList<>();
        result.addAll(exactMatch);
        result.addAll(partialMatch);

        // Sembunyikan semua
        for (BudayaItem item : budayaList) {
            LinearLayout layout = view.findViewById(item.layoutId);
            layout.setVisibility(View.GONE);
        }

        // Tampilkan dan pindahkan ke atas sesuai urutan result
        GridLayout grid = view.findViewById(R.id.gridLayoutBudaya);
        int childIndex = 0;
        for (BudayaItem item : result) {
            LinearLayout layout = view.findViewById(item.layoutId);
            layout.setVisibility(View.VISIBLE);
            grid.removeView(layout);
            grid.addView(layout, childIndex++);
        }

        // Jika query kosong, tampilkan semua sesuai urutan awal
        if (lowerQuery.isEmpty()) {
            for (BudayaItem item : budayaList) {
                LinearLayout layout = view.findViewById(item.layoutId);
                layout.setVisibility(View.VISIBLE);
                grid.removeView(layout);
                grid.addView(layout);
            }
        }
    }
}