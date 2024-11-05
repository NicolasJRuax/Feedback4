package com.myproyect.gestornovelasnjr.gestor_novelas;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.MainActivity;

public class NovelAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.novel_appwidget);

            // Configurar el RemoteViewsService para el ListView
            Intent intent = new Intent(context, FavoriteNovelsWidgetService.class);
            views.setRemoteAdapter(R.id.widget_list_view, intent);

            // Configurar Intent al hacer clic en un ítem del widget
            Intent appIntent = new Intent(context, MainActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_IMMUTABLE);
            views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
