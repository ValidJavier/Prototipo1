package com.stomas.prototipo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar del menú principal (sin flecha "Atrás", es la pantalla de inicio)
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Prototipo 2");

        // ---------- Menú de intents implícitos (cada uno abre su pantalla) ----------
        irA(R.id.btnMaps, MapsActivity.class);
        irA(R.id.btnWeb, WebActivity.class);
        irA(R.id.btnTelefono, TelefonoActivity.class);
        irA(R.id.btnCorreo, CorreoActivity.class);
        irA(R.id.btnCamara, CamaraActivity.class);

        // ---------- Menú de intents explícitos ----------
        abrirDetalle(R.id.btnItem1, "Notebook Pro", "Notebook de 16 GB RAM y 512 GB SSD", 799990, R.drawable.notebook);
        abrirDetalle(R.id.btnItem2, "Smartphone X", "Smartphone con cámara de 50 MP", 449990, R.drawable.smartphone);
        abrirDetalle(R.id.btnItem3, "Tablet Air", "Tablet de 11 pulgadas ideal para estudiar", 329990, R.drawable.tablet);
        irA(R.id.btnForm, FormActivity.class);
        irA(R.id.btnConfig, ConfigActivity.class);
    }

    // Navegación simple: botón -> Activity
    private void irA(int idBoton, Class<?> destino) {
        findViewById(idBoton).setOnClickListener(v ->
                startActivity(new Intent(this, destino)));
    }

    // Explícito #1: MainActivity -> DetalleActivity con extras
    private void abrirDetalle(int idBoton, String nombre, String descripcion, int precio, int imagen) {
        findViewById(idBoton).setOnClickListener(v -> {
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_NOMBRE, nombre);
            i.putExtra(DetalleActivity.EXTRA_DESCRIPCION, descripcion);
            i.putExtra(DetalleActivity.EXTRA_PRECIO, precio);
            i.putExtra(DetalleActivity.EXTRA_IMAGEN, imagen);
            startActivity(i);
        });
    }
}