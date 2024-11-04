package com.myproyect.gestornovelasnjr.gestor_novelas.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.Novel;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelAdapter;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelViewModel;
import java.util.List;

public class NovelListFragments extends Fragment {
    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelViewModel novelViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        novelAdapter = new NovelAdapter(getContext(), new NovelAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Novel novel) {
                // Aquí puedes implementar la acción para eliminar la novela.
                // Por ejemplo:
                novelViewModel.delete(novel);
                Toast.makeText(getContext(), "Novela eliminada: " + novel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(novelAdapter);

        novelViewModel = new ViewModelProvider(requireActivity()).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                novelAdapter.setNovels(novels);
            }
        });

        return view;
    }
}
