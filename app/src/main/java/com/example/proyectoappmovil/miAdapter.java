package com.example.proyectoappmovil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class miAdapter extends RecyclerView.Adapter<miAdapter.miViewHolder> {

    List<Ruta> misRutas;
    Context context;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    public miAdapter(List<Ruta> misRutas, Context context) {
        this.misRutas = misRutas;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void setRutas(List<Ruta> nuevasRutas) {
        this.misRutas = nuevasRutas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public miAdapter.miViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rutas, parent, false);
        miViewHolder mivh = new miViewHolder(v);
        return mivh;
    }

    @Override
    public void onBindViewHolder(@NonNull miAdapter.miViewHolder holder, int position) {
        Ruta ruta = misRutas.get(position);
        if (ruta == null) return;

        holder.tvNombreRuta.setText(ruta.getNombre());
        holder.tvDistanciaDificultad.setText("Distancia: " + ruta.getDistancia() + " km | Dificultad: " + ruta.getDificultad());

        holder.buttonCompartir.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "¡Te recomiendo esta ruta! \n" +
                    "Nombre: " + ruta.getNombre() + "\n" +
                    "Distancia: " + ruta.getDistancia() + " km";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "¡Mira esta increíble ruta!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            context.startActivity(Intent.createChooser(shareIntent, "Compartir vía"));
        });

        if (currentUser != null && ruta.getId() != null) {
            DocumentReference favoriteRef = db.collection("users").document(currentUser.getUid())
                    .collection("favorites").document(ruta.getId());
            favoriteRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_off);
                }
            });

            holder.buttonFavorito.setOnClickListener(v -> {
                favoriteRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        favoriteRef.delete().addOnSuccessListener(aVoid -> {
                            holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_off);
                            Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        favoriteRef.set(ruta).addOnSuccessListener(aVoid -> {
                            holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_on);
                            Toast.makeText(context, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        return (misRutas != null) ? misRutas.size() : 0;
    }

    public class miViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreRuta, tvDistanciaDificultad;
        ImageButton buttonCompartir, buttonFavorito;

        public miViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreRuta = itemView.findViewById(R.id.tvNombreRuta);
            tvDistanciaDificultad = itemView.findViewById(R.id.tvDistanciaDificultad);
            buttonCompartir = itemView.findViewById(R.id.buttonCompartir);
            buttonFavorito = itemView.findViewById(R.id.buttonFavorito);
        }
    }
}