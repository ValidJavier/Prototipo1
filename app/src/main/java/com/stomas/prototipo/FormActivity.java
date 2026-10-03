package com.stomas.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class FormActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etEdad;
    private TextView tvResultado;

    // Explícito #4: FormActivity -> ConfirmActivity con resultado (registerForActivityResult)
    private final ActivityResultLauncher<Intent> confirmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    tvResultado.setText(result.getData().getStringExtra(ConfirmActivity.EXTRA_RESPUESTA));
                } else {
                    tvResultado.setText("Envío cancelado por el usuario");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Formulario");

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreoForm);
        etEdad = findViewById(R.id.etEdad);
        tvResultado = findViewById(R.id.tvResultado);

        findViewById(R.id.btnEnviar).setOnClickListener(v -> enviar());
    }

    private void enviar() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String edadTxt = etEdad.getText().toString().trim();

        if (nombre.length() < 3) {
            etNombre.setError("El nombre debe tener al menos 3 caracteres");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Correo inválido");
            return;
        }
        int edad;
        try {
            edad = Integer.parseInt(edadTxt);
        } catch (NumberFormatException e) {
            etEdad.setError("Ingresa un número");
            return;
        }
        if (edad < 1 || edad > 99) {
            etEdad.setError("La edad debe estar entre 1 y 99");
            return;
        }

        Intent i = new Intent(this, ConfirmActivity.class);
        i.putExtra(ConfirmActivity.EXTRA_NOMBRE, nombre);
        i.putExtra(ConfirmActivity.EXTRA_CORREO, correo);
        i.putExtra(ConfirmActivity.EXTRA_EDAD, edad);
        confirmLauncher.launch(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}