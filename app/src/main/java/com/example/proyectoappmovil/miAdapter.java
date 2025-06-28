package com.example.proyectoappmovil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class miAdapter extends RecyclerView.Adapter<miAdapter.miViewHolder> {

    private List<Ruta> misRutas;

    public miAdapter(List<Ruta> misRutas) {
        this.misRutas = misRutas;
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
    }

    @Override
    public int getItemCount() {
        return this.misRutas.size();
    }


    public class miViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreRuta, tvDistanciaDificultad;

        public miViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreRuta = itemView.findViewById(R.id.tvNombreRuta);
            tvDistanciaDificultad = itemView.findViewById(R.id.tvDistanciaDificultad);
        }
    }
}