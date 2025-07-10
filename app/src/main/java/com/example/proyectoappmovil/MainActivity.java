package com.example.proyectoappmovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button buttonSenderismo, buttonCiclismo, buttonFavoritos;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        buttonSenderismo = findViewById(R.id.buttonSenderismo);
        buttonCiclismo = findViewById(R.id.buttonCiclismo);
        buttonFavoritos = findViewById(R.id.buttonFavoritos);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        buttonCiclismo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CiclismoActivity.class);
            startActivity(intent);
        });

        buttonSenderismo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SenderismoActivity.class);
            startActivity(intent);
        });

        buttonFavoritos.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FavoritoActivity.class));
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Perfil":
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                return true;
            case "Cerrar Sesión":
                mAuth.signOut();
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}