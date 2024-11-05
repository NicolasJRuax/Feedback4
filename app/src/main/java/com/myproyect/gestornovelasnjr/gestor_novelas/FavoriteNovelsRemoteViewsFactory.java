package com.myproyect.gestornovelasnjr.gestor_novelas;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.Novel;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteNovelsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Novel> favoriteNovels = new ArrayList<>();
    private NovelRepository novelRepository;

    public FavoriteNovelsRemoteViewsFactory(Context applicationContext) {
        context = applicationContext;
        novelRepository = new NovelRepository();
    }

    @Override
    public void onCreate() {
        // Inicializar la lista
    }

    @Override
    public void onDataSetChanged() {
        // Actualizar la lista de novelas favoritas
        favoriteNovels.clear();
        favoriteNovels = novelRepository.getFavoriteNovels().getValue();
        if (favoriteNovels == null) {
            favoriteNovels = new ArrayList<>();
        }
    }

    @Override
    public void onDestroy() {
        favoriteNovels.clear();
    }

    @Override
    public int getCount() {
        return favoriteNovels.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Novel novel = favoriteNovels.get(position);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.widget_item_text, novel.getTitle());

        // Configurar Intent al hacer clic en un ítem
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("novel_id", novel.getId());
        views.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null; // Usar el layout por defecto
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Solo un tipo de vista
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
