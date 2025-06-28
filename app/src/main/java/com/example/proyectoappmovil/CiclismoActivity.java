package com.example.proyectoappmovil;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CiclismoActivity extends AppCompatActivity {

    private RecyclerView miRecycler;
    private miAdapter miAdapter;
    private List<Ruta> listaRutasCiclismo;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ciclismo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        miRecycler = findViewById(R.id.miRecycler);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        listaRutasCiclismo = new ArrayList<>();
        miAdapter = new miAdapter(listaRutasCiclismo);
        miRecycler.setAdapter(miAdapter);

        cargarRutasCiclismo();
    }

    private void cargarRutasCiclismo() {
        db.collection("rutas")
                .whereEqualTo("tipo", "ciclismo")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaRutasCiclismo.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ruta ruta = document.toObject(Ruta.class);
                            ruta.setId(document.getId());
                            listaRutasCiclismo.add(ruta);
                        }
                        miAdapter.setRutas(listaRutasCiclismo);
                        if (listaRutasCiclismo.isEmpty()) {
                            Toast.makeText(CiclismoActivity.this, "No se tienen rutas.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CiclismoActivity.this, "Error al cargar rutas.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}