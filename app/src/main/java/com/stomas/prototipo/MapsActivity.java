package com.stomas.prototipo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class MapsActivity extends AppCompatActivity {

    private EditText etLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Google Maps");

        etLugar = findViewById(R.id.etLugar);
        findViewById(R.id.btnAbrirMaps).setOnClickListener(v -> abrirMaps());
    }

    // Implícito: ACTION_VIEW con geo:0,0?q=texto
    private void abrirMaps() {
        String lugar = etLugar.getText().toString().trim();
        if (lugar.length() < 3) {
            etLugar.setError("Ingresa al menos 3 caracteres");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(lugar)));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay una app de mapas disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}