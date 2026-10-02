package com.stomas.prototipo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class WebActivity extends AppCompatActivity {

    private EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Página web");

        etUrl = findViewById(R.id.etUrl);
        findViewById(R.id.btnAbrirWeb).setOnClickListener(v -> abrirWeb());
    }

    // Implícito: ACTION_VIEW con https://
    private void abrirWeb() {
        String url = etUrl.getText().toString().trim();
        if (!url.startsWith("https://") || !Patterns.WEB_URL.matcher(url).matches()) {
            etUrl.setError("Ingresa una URL válida que empiece con https://");
            return;
        }
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay un navegador disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}