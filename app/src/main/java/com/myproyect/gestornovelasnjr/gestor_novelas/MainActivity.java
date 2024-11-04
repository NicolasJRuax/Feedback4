package com.myproyect.gestornovelasnjr.gestor_novelas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproyect.gestornovelasnjr.R;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.Novel;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelAdapter;
import com.myproyect.gestornovelasnjr.gestor_novelas.Sync.SyncAlarmReceiver;
import com.myproyect.gestornovelasnjr.gestor_novelas.Sync.SyncDataTask;
import com.myproyect.gestornovelasnjr.gestor_novelas.Novelas.NovelViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddBook, buttonSyncData, buttonSettings;
    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelViewModel novelViewModel;
    private BroadcastReceiver syncReceiver;

    private void scheduleSyncAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SyncAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        long interval = AlarmManager.INTERVAL_HALF_DAY;
        long triggerAtMillis = System.currentTimeMillis() + interval;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, interval, pendingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyDarkMode(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddBook = findViewById(R.id.buttonAddBook);
        buttonSyncData = findViewById(R.id.buttonSyncData);
        buttonSettings = findViewById(R.id.buttonSettings); // Botón de configuración
        recyclerView = findViewById(R.id.recyclerView);

        buttonAddBook.setOnClickListener(v -> showAddNovelDialog());
        buttonSyncData.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Sincronizando datos...", Toast.LENGTH_SHORT).show();
            new SyncDataTask(MainActivity.this).execute();
        });
        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        novelAdapter = new NovelAdapter(this, novel -> {
            novelViewModel.delete(novel);
            Toast.makeText(this, "Novela eliminada: " + novel.getTitle(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(novelAdapter);

        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(this, novels -> {
            if (novels != null) {
                novelAdapter.setNovels(novels);
            }
        });

        syncReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.hasExtra("novels")) {
                    ArrayList<Novel> syncedNovels = intent.getParcelableArrayListExtra("novels");
                    if (syncedNovels != null) {
                        novelAdapter.setNovels(syncedNovels);
                    }
                    Toast.makeText(context, "Sincronización completada", Toast.LENGTH_SHORT).show();
                    scheduleSyncAlarm();
                }
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(syncReceiver, new IntentFilter("com.myproyect.gestornovelasnjr.SYNC_COMPLETE"), Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(syncReceiver, new IntentFilter("com.myproyect.gestornovelasnjr.SYNC_COMPLETE"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(syncReceiver);
    }

    private void showAddNovelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Novela");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_novel, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            EditText editTextTitle = customLayout.findViewById(R.id.editTextTitle);
            EditText editTextAuthor = customLayout.findViewById(R.id.editTextAuthor);
            EditText editTextYear = customLayout.findViewById(R.id.editTextYear);
            EditText editTextSynopsis = customLayout.findViewById(R.id.editTextSynopsis);

            String title = editTextTitle.getText().toString();
            String author = editTextAuthor.getText().toString();
            int year;
            try {
                year = Integer.parseInt(editTextYear.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Año inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            String synopsis = editTextSynopsis.getText().toString();

            if (!title.isEmpty() && !author.isEmpty() && year > 0 && !synopsis.isEmpty()) {
                Novel novel = new Novel(title, author, year, synopsis);
                novelViewModel.insert(novel);
                Toast.makeText(MainActivity.this, "Novela añadida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}


