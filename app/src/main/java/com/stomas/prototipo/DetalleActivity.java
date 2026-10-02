package com.stomas.prototipo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalleActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_DESCRIPCION = "descripcion";
    public static final String EXTRA_PRECIO = "precio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalle del item");

        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        String descripcion = getIntent().getStringExtra(EXTRA_DESCRIPCION);
        int precio = getIntent().getIntExtra(EXTRA_PRECIO, 0);

        ((TextView) findViewById(R.id.tvNombre)).setText(nombre);
        ((TextView) findViewById(R.id.tvDescripcion)).setText(descripcion);
        ((TextView) findViewById(R.id.tvPrecio)).setText("Precio: "
                + NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL")).format(precio));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}