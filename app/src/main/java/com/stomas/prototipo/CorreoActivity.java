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

public class CorreoActivity extends AppCompatActivity {

    private EditText etCorreo, etAsunto, etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Enviar correo");

        etCorreo = findViewById(R.id.etCorreo);
        etAsunto = findViewById(R.id.etAsunto);
        etMensaje = findViewById(R.id.etMensaje);
        findViewById(R.id.btnEnviarCorreo).setOnClickListener(v -> enviarCorreo());
    }

    // Implícito: ACTION_SENDTO con mailto: (asunto y cuerpo prellenados)
    private void enviarCorreo() {
        String correo = etCorreo.getText().toString().trim();
        String asunto = etAsunto.getText().toString().trim();
        String mensaje = etMensaje.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Correo inválido");
            return;
        }
        if (asunto.isEmpty()) {
            etAsunto.setError("El asunto es obligatorio");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo});
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay una app de correo disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}