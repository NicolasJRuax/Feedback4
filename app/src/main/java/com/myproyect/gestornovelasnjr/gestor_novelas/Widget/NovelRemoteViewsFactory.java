package com.myproyect.gestornovelasnjr.gestor_novelas.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.Novel;
import java.util.ArrayList;
import java.util.List;

public class NovelRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Novel> favoriteNovels = new ArrayList<>();

    public NovelRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        // Initial data load
        loadFavoriteNovels();
    }

    @Override
    public void onDataSetChanged() {
        // Refresh data
        loadFavoriteNovels();
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

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("novel_id", novel.getId());
        views.setOnClickFillInIntent(R.id.widget_item_text, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null; // Default loading view
    }

    @Override
    public int getViewTypeCount() {
        return 1; // One type of view
    }

    @Override
    public long getItemId(int position) {
        return position; // Or novel IDs if unique
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void loadFavoriteNovels() {
        favoriteNovels.clear();
        // Load favorite novels from local storage or cache
        // For example purposes, adding dummy data
        favoriteNovels.add(new Novel("1", "Novela Favorita 1", "Autor 1", 2020, "Sinopsis 1", true));
        favoriteNovels.add(new Novel("2", "Novela Favorita 2", "Autor 2", 2021, "Sinopsis 2", true));
    }
}
