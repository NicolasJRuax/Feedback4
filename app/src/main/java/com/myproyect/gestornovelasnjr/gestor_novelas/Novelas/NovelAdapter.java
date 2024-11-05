package com.myproyect.gestornovelasnjr.gestor_novelas.Novelas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import com.myproyect.gestornovelasnjr.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myproyect.gestornovelasnjr.gestor_novelas.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelHolder> {

    private List<Novel> novels = new ArrayList<>();
    private OnDeleteClickListener deleteListener;
    private Context context;
    private FirebaseFirestore db;


    public interface OnDeleteClickListener {
        void onDeleteClick(Novel novel);
    }



    public interface OnItemClickListener {
        void onItemSelected(Novel novel);
    }



    private OnItemClickListener itemClickListener;


    public NovelAdapter(Context context, OnDeleteClickListener deleteListener, OnItemClickListener itemClickListener) {
        this.context = context;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
        db = FirebaseFirestore.getInstance();
    }

    public NovelAdapter(MainActivity context, OnDeleteClickListener onDeleteClickListener) {
    }

    @NonNull
    @Override
    public NovelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_novel, parent, false);
        return new NovelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelHolder holder, int position) {
        Novel currentNovel = novels.get(position);
        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());

        // Actualizar el ícono del botón de favoritos según el estado
        if (currentNovel.isFavorite()) {
            holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_on); // Estrella llena
        } else {
            holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_off); // Estrella vacía
        }

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemSelected(currentNovel);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            deleteListener.onDeleteClick(currentNovel);
        });

        // Botón de ver detalles
        holder.buttonDetails.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(currentNovel.getTitle())
                    .setMessage("Autor: " + currentNovel.getAuthor()
                            + "\nAño: " + currentNovel.getYear()
                            + "\nSinopsis: " + currentNovel.getSynopsis())
                    .setPositiveButton("Cerrar", null)
                    .show();
        });

        // Botón de favorito
        holder.buttonFavorite.setOnClickListener(v -> {
            // Cambiar el estado de favorito
            boolean isFavorite = !currentNovel.isFavorite();
            currentNovel.setFavorite(isFavorite);

            // Actualizar en Firestore
            updateFavoriteStatus(currentNovel);

            // Actualizar el ícono
            if (isFavorite) {
                holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_on); // Estrella llena
                Toast.makeText(context, currentNovel.getTitle() + " añadido a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                holder.buttonFavorite.setImageResource(android.R.drawable.btn_star_big_off); // Estrella vacía
                Toast.makeText(context, currentNovel.getTitle() + " eliminado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
    }

    private void updateFavoriteStatus(Novel novel) {
        // Usamos el ID del documento para actualizar directamente
        if (novel.getId() != null) {
            db.collection("novels").document(novel.getId())
                    .update("favorite", novel.isFavorite())
                    .addOnSuccessListener(aVoid -> {
                        // Éxito al actualizar
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Error al actualizar favorito", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Manejar el caso en que el ID es nulo
            Toast.makeText(context, "No se pudo actualizar el estado de favorito", Toast.LENGTH_SHORT).show();
        }
    }

    class NovelHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;
        private ImageButton buttonDelete;
        private ImageButton buttonFavorite;
        private ImageButton buttonDetails;

        public NovelHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonFavorite = itemView.findViewById(R.id.buttonFavorite);
            buttonDetails = itemView.findViewById(R.id.buttonDetails);

            // Asignar iconos integrados a los botones
            buttonDelete.setImageResource(android.R.drawable.ic_menu_delete);
            buttonDetails.setImageResource(android.R.drawable.ic_menu_info_details);
        }
    }


}
