package com.myproyect.gestornovelasnjr.gestor_novelas;


import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteNovelsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteNovelsRemoteViewsFactory(this.getApplicationContext());
    }
}

