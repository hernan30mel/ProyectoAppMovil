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

import java.util.List;

public class miAdapter extends RecyclerView.Adapter<miAdapter.miViewHolder> {

    List<Ruta> misRutas;
    Context context;

    public miAdapter(List<Ruta> misRutas, Context context) {
        this.misRutas = misRutas;
        this.context = context;
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

        holder.buttonFavorito.setTag(0);
        holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_off);

        holder.buttonFavorito.setOnClickListener(v -> {
            int estadoFavorito = (int) holder.buttonFavorito.getTag();

            if (estadoFavorito == 0) {
                holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_on);
                holder.buttonFavorito.setTag(1);
                Toast.makeText(context, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                holder.buttonFavorito.setImageResource(android.R.drawable.btn_star_big_off);
                holder.buttonFavorito.setTag(0);
                Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.misRutas.size();
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