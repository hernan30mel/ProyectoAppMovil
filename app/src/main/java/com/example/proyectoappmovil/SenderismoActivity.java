package com.example.proyectoappmovil;

import android.os.Bundle;
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

public class SenderismoActivity extends AppCompatActivity {

    RecyclerView miRecycler;
    miAdapter miAdapter;
    List<Ruta> listaRutasSenderismo;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_senderismo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        miRecycler = findViewById(R.id.miRecyclerSenderismo);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        listaRutasSenderismo = new ArrayList<>();
        miAdapter = new miAdapter(listaRutasSenderismo, this);
        miRecycler.setAdapter(miAdapter);

        cargarRutasSenderismo();

    }

    private void cargarRutasSenderismo() {
        db.collection("rutas")
                .whereEqualTo("tipo", "senderismo")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaRutasSenderismo.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ruta ruta = document.toObject(Ruta.class);
                            ruta.setId(document.getId());
                            listaRutasSenderismo.add(ruta);
                        }
                        miAdapter.setRutas(listaRutasSenderismo);
                        if (listaRutasSenderismo.isEmpty()) {
                            Toast.makeText(SenderismoActivity.this, "No se tienen rutas de senderismo.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SenderismoActivity.this, "Error al cargar rutas.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}