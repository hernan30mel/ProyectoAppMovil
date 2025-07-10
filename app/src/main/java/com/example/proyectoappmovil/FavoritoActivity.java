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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritoActivity extends AppCompatActivity {

    RecyclerView recyclerFavoritos;
    miAdapter favoritosAdapter;
    List<Ruta> listaFavoritos;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerFavoritos = findViewById(R.id.recyclerFavoritos);
        listaFavoritos = new ArrayList<>();
        recyclerFavoritos.setLayoutManager(new LinearLayoutManager(this));
        favoritosAdapter = new miAdapter(listaFavoritos, this);
        recyclerFavoritos.setAdapter(favoritosAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarFavoritos();
    }

    private void cargarFavoritos() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(user.getUid()).collection("favorites")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listaFavoritos.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ruta ruta = document.toObject(Ruta.class);
                                listaFavoritos.add(ruta);
                            }
                            favoritosAdapter.notifyDataSetChanged();

                            if (listaFavoritos.isEmpty()) {
                                Toast.makeText(FavoritoActivity.this, "Sin rutas favoritas.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(FavoritoActivity.this, "Error al crgar rutas", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Error de sistema", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}