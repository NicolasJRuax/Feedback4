package com.myproyect.gestornovelasnjr.gestor_novelas.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelAdapter;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelViewModel;

public class NovelListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelViewModel novelViewModel;

    public NovelListFragment() {
        // Required empty public constructor
    }

    public static NovelListFragment newInstance() {
        return new NovelListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_novel_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        novelAdapter = new NovelAdapter(getContext(), novel -> {
            // Implementar acción de eliminación si es necesario
        }, novel -> {
            // Navegar al fragmento de detalles al hacer clic en una novela
            Bundle bundle = new Bundle();
            bundle.putParcelable("novel", novel);
            NovelDetailFragment detailFragment = new NovelDetailFragment();
            detailFragment.setArguments(bundle);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(novelAdapter);

        novelViewModel = new ViewModelProvider(requireActivity()).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(getViewLifecycleOwner(), novels -> {
            if (novels != null) {
                novelAdapter.setNovels(novels);
            }
        });

        return view;
    }
}
