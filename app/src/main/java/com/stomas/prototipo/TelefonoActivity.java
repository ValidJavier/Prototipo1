package com.stomas.prototipo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class TelefonoActivity extends AppCompatActivity {

    private EditText etTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Marcador telefónico");

        etTelefono = findViewById(R.id.etTelefono);
        findViewById(R.id.btnMarcar).setOnClickListener(v -> abrirMarcador());
    }

    // Implícito: ACTION_DIAL con tel: (solo muestra el marcador, no requiere CALL_PHONE)
    private void abrirMarcador() {
        String tel = etTelefono.getText().toString().trim().replace(" ", "");
        if (!tel.matches("^\\+?[0-9]{8,12}$")) {
            etTelefono.setError("Teléfono inválido (8 a 12 dígitos, puede iniciar con +)");
            return;
        }
        try {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay una app de teléfono disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}