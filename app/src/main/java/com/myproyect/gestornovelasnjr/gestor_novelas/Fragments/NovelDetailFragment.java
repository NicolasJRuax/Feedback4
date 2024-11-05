package com.myproyect.gestornovelasnjr.gestor_novelas.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.Novel;

public class NovelDetailFragment extends Fragment {

    private TextView textViewTitle, textViewAuthor, textViewYear, textViewSynopsis;

    public NovelDetailFragment() {
        // Required empty public constructor
    }

    public static NovelDetailFragment newInstance() {
        return new NovelDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_novel_detail, container, false);

        textViewTitle = view.findViewById(R.id.textViewDetailTitle);
        textViewAuthor = view.findViewById(R.id.textViewDetailAuthor);
        textViewYear = view.findViewById(R.id.textViewDetailYear);
        textViewSynopsis = view.findViewById(R.id.textViewDetailSynopsis);

        if (getArguments() != null) {
            Novel novel = getArguments().getParcelable("novel");
            if (novel != null) {
                textViewTitle.setText(novel.getTitle());
                textViewAuthor.setText(novel.getAuthor());
                textViewYear.setText(String.valueOf(novel.getYear()));
                textViewSynopsis.setText(novel.getSynopsis());
            }
        }

        return view;
    }
}
