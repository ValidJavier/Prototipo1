package com.stomas.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ConfirmActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_CORREO = "correo";
    public static final String EXTRA_EDAD = "edad";
    public static final String EXTRA_RESPUESTA = "respuesta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Confirmar datos");

        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        String correo = getIntent().getStringExtra(EXTRA_CORREO);
        int edad = getIntent().getIntExtra(EXTRA_EDAD, 0);

        ((TextView) findViewById(R.id.tvDatos)).setText(
                "Nombre: " + nombre + "\nCorreo: " + correo + "\nEdad: " + edad);

        findViewById(R.id.btnConfirmar).setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra(EXTRA_RESPUESTA, "Datos de " + nombre + " confirmados correctamente");
            setResult(RESULT_OK, data);
            finish();
        });

        findViewById(R.id.btnCancelar).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}
