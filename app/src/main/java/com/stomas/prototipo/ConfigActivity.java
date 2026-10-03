package com.stomas.prototipo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Configuración");

        SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
        Switch swNotif = findViewById(R.id.swNotificaciones);
        Switch swSonido = findViewById(R.id.swSonido);
        swNotif.setChecked(prefs.getBoolean("notificaciones", true));
        swSonido.setChecked(prefs.getBoolean("sonido", false));

        swNotif.setOnCheckedChangeListener((CompoundButton b, boolean on) -> guardar(prefs, "notificaciones", on));
        swSonido.setOnCheckedChangeListener((CompoundButton b, boolean on) -> guardar(prefs, "sonido", on));
    }

    private void guardar(SharedPreferences prefs, String clave, boolean valor) {
        prefs.edit().putBoolean(clave, valor).apply();
        Toast.makeText(this, "Ajuste guardado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}