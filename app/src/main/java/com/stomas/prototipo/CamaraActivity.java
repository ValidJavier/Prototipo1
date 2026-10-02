package com.stomas.prototipo;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Map;

public class CamaraActivity extends AppCompatActivity {

    private ImageView ivFoto;
    private TextView tvEstado;
    private Uri fotoUri;

    // Resultado de la solicitud de permisos (se ejecuta cuando el usuario responde el diálogo)
    private final ActivityResultLauncher<String[]> permisosLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            (Map<String, Boolean> resultado) -> {
                if (!resultado.containsValue(false)) {
                    abrirCamara();
                } else {
                    tvEstado.setText("Permiso denegado: no se puede usar la cámara");
                    boolean camaraDenegada = ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
                    // Si marcó "no volver a preguntar", solo puede activarlo desde Ajustes
                    if (camaraDenegada && !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        mostrarDialogoAjustes();
                    }
                }
            });

    // Resultado de la cámara (MediaStore.ACTION_IMAGE_CAPTURE por debajo)
    private final ActivityResultLauncher<Uri> camaraLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            exito -> {
                if (exito) {
                    ivFoto.setImageURI(fotoUri);
                    tvEstado.setText("Foto guardada en la galería (Pictures/Prototipo2)");
                } else {
                    // Canceló: eliminamos el registro vacío creado en la galería
                    if (fotoUri != null) {
                        getContentResolver().delete(fotoUri, null, null);
                    }
                    tvEstado.setText("Captura cancelada");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cámara");

        ivFoto = findViewById(R.id.ivFoto);
        tvEstado = findViewById(R.id.tvEstado);
        findViewById(R.id.btnTomarFoto).setOnClickListener(v -> tomarFoto());
    }

    // 1) Revisar permisos ANTES de abrir la cámara
    private void tomarFoto() {
        if (tienePermisos()) {
            abrirCamara();
        } else {
            permisosLauncher.launch(permisosNecesarios());
        }
    }

    private String[] permisosNecesarios() {
        // Hasta Android 9 (API 28) guardar en la galería requiere permiso de almacenamiento
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        return new String[]{Manifest.permission.CAMERA};
    }

    private boolean tienePermisos() {
        for (String permiso : permisosNecesarios()) {
            if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 2) Crear el archivo en la galería y lanzar la cámara
    private void abrirCamara() {
        ContentValues valores = new ContentValues();
        valores.put(MediaStore.Images.Media.DISPLAY_NAME, "foto_" + System.currentTimeMillis() + ".jpg");
        valores.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            valores.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Prototipo2");
        }
        fotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valores);
        if (fotoUri == null) {
            Toast.makeText(this, "No se pudo crear el archivo de la foto", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            camaraLauncher.launch(fotoUri);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay una app de cámara disponible", Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarDialogoAjustes() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso necesario")
                .setMessage("Activa el permiso de cámara en Ajustes para poder tomar fotos.")
                .setPositiveButton("Abrir ajustes", (dialogo, which) -> {
                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(i);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}