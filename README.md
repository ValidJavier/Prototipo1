# Prototipo 3 - Intents de Android

## 1. Resumen del proyecto

Aplicación Android desarrollada en Java como prototipo para demostrar el uso de **Intents implícitos y explícitos**.

La aplicación presenta un menú principal desde el cual se pueden probar acciones que delegan tareas a otras aplicaciones del dispositivo, además de navegación entre Activities propias de la aplicación mediante Intents explícitos.

### Tecnologías y configuración

- Lenguaje: **Java**
- Android Gradle Plugin (AGP): **9.0.1**
- Gradle Wrapper: **9.2.1**
- Compile SDK: **Android API 36.1**
- Target SDK: **Android API 36**
- Min SDK: **Android API 24**
- Java source/target compatibility: **Java 11**
- Application ID: `com.stomas.prototipo`
- Versión de la aplicación: `1.0`
- Material Components: **1.14.0**
- AndroidX AppCompat: **1.8.0**
- AndroidX Activity: **1.13.0**
- ConstraintLayout: **2.2.2**

## 2. Estructura principal

Las principales Activities utilizadas son:

- `MainActivity`: menú principal.
- `MapsActivity`: búsqueda de ubicaciones mediante una aplicación de mapas.
- `WebActivity`: apertura de páginas web.
- `TelefonoActivity`: apertura del marcador telefónico.
- `CorreoActivity`: composición de un correo electrónico.
- `CamaraActivity`: captura y almacenamiento de fotografías.
- `DetalleActivity`: muestra los datos de un producto recibidos mediante extras.
- `FormActivity`: formulario de datos.
- `ConfirmActivity`: confirmación de los datos enviados desde el formulario.
- `ConfigActivity`: configuración de preferencias mediante `SharedPreferences`.

## 3. Intents implícitos

La aplicación implementa **5 casos de uso con Intents implícitos**.

### 3.1 Google Maps - `ACTION_VIEW`

**Activity:** `MapsActivity`

Se utiliza:

```java
Intent intent = new Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:0,0?q=" + Uri.encode(lugar))
);
```

El usuario introduce un lugar o dirección y la aplicación solicita al sistema una aplicación compatible con el esquema `geo:`.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Google Maps**.
3. Escribir una ubicación, por ejemplo `Plaza de Armas Santiago`.
4. Presionar **Abrir en Google Maps**.
5. Android debe mostrar una aplicación de mapas compatible.

---

### 3.2 Página web - `ACTION_VIEW`

**Activity:** `WebActivity`

Se utiliza:

```java
startActivity(
        new Intent(Intent.ACTION_VIEW, Uri.parse(url))
);
```

La URL debe comenzar con `https://` y pasar la validación de `Patterns.WEB_URL`.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Página web**.
3. Escribir una URL válida, por ejemplo `https://www.google.com`.
4. Presionar **Abrir página**.
5. Android debe abrir el navegador disponible.

---

### 3.3 Teléfono - `ACTION_DIAL`

**Activity:** `TelefonoActivity`

Se utiliza:

```java
startActivity(
        new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel))
);
```

Se utiliza `ACTION_DIAL`, por lo que se abre el marcador con el número indicado sin realizar automáticamente la llamada.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Llamar**.
3. Escribir un número telefónico válido.
4. Presionar **Llamar**.
5. Android debe abrir el marcador telefónico con el número preparado.

---

### 3.4 Correo electrónico - `ACTION_SENDTO`

**Activity:** `CorreoActivity`

Se utiliza:

```java
Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
intent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo});
intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
intent.putExtra(Intent.EXTRA_TEXT, mensaje);
startActivity(intent);
```

El destinatario, asunto y mensaje se envían como información del Intent para que una aplicación de correo compatible pueda abrir el mensaje.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Enviar correo**.
3. Introducir un correo válido.
4. Introducir un asunto.
5. Escribir un mensaje.
6. Presionar **Enviar correo**.
7. Android debe mostrar una aplicación de correo compatible con los datos preparados.

---

### 3.5 Cámara - `TakePicture` / captura mediante cámara

**Activity:** `CamaraActivity`

La aplicación utiliza:

```java
ActivityResultContracts.TakePicture()
```

El contrato permite solicitar una captura de imagen mediante una aplicación de cámara disponible en el dispositivo. Antes de abrirla se solicitan los permisos correspondientes y se crea un `Uri` mediante `MediaStore` para guardar la fotografía.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Cámara**.
3. Conceder el permiso de cámara cuando Android lo solicite.
4. Tomar una fotografía.
5. Confirmar la fotografía desde la aplicación de cámara.
6. Volver a la aplicación.
7. Comprobar que la fotografía aparece en la vista previa y que se indica que fue guardada en la galería.

---

## 4. Intents explícitos

La aplicación contiene **3 flujos de navegación explícita** entre Activities propias.

### 4.1 Catálogo → Detalle

**Origen:** `MainActivity`

**Destino:** `DetalleActivity`

La navegación se realiza mediante un Intent explícito y se envían tres extras:

```java
Intent i = new Intent(this, DetalleActivity.class);
i.putExtra(DetalleActivity.EXTRA_NOMBRE, nombre);
i.putExtra(DetalleActivity.EXTRA_DESCRIPCION, descripcion);
i.putExtra(DetalleActivity.EXTRA_PRECIO, precio);
startActivity(i);
```

Se utiliza para los productos:

- Notebook Pro
- Smartphone X
- Tablet Air

**Pasos de prueba:**

1. Abrir la aplicación.
2. Ir a la sección **Catálogo**.
3. Seleccionar cualquiera de los tres productos.
4. Se debe abrir `DetalleActivity`.
5. Comprobar que aparecen nombre, descripción y precio recibidos mediante extras.

---

### 4.2 Formulario → Confirmación con resultado

**Origen:** `FormActivity`

**Destino:** `ConfirmActivity`

Este flujo utiliza un Intent explícito y `ActivityResultLauncher` para recibir una respuesta desde la Activity de confirmación.

Datos enviados:

```java
Intent i = new Intent(this, ConfirmActivity.class);
i.putExtra(ConfirmActivity.EXTRA_NOMBRE, nombre);
i.putExtra(ConfirmActivity.EXTRA_CORREO, correo);
i.putExtra(ConfirmActivity.EXTRA_EDAD, edad);
confirmLauncher.launch(i);
```

Al confirmar, `ConfirmActivity` devuelve un resultado mediante:

```java
setResult(RESULT_OK, data);
finish();
```

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Formulario**.
3. Introducir un nombre de al menos 3 caracteres.
4. Introducir un correo válido.
5. Introducir una edad entre 1 y 120.
6. Presionar **Enviar**.
7. Comprobar que se abre la pantalla **Confirmar datos**.
8. Presionar **Confirmar**.
9. Volver a `FormActivity` y comprobar el mensaje de resultado.
10. También se puede probar **Cancelar** para comprobar el resultado cancelado.

---

### 4.3 Menú principal → Configuración

**Origen:** `MainActivity`

**Destino:** `ConfigActivity`

Se utiliza un Intent explícito para abrir la pantalla de configuración:

```java
startActivity(new Intent(this, ConfigActivity.class));
```

La pantalla permite modificar las opciones de notificaciones y sonido. Los valores se almacenan mediante `SharedPreferences`.

**Pasos de prueba:**

1. Abrir la aplicación.
2. Seleccionar **Configuración**.
3. Activar o desactivar **Notificaciones**.
4. Activar o desactivar **Sonido**.
5. Comprobar el mensaje **Ajuste guardado**.
6. Salir y volver a entrar en Configuración.
7. Comprobar que los valores seleccionados se mantienen.

> `ConfirmActivity` también está declarada en el `AndroidManifest.xml`, pero forma parte del flujo explícito Formulario → Confirmación. Por ello el proyecto se documenta como **3 flujos explícitos**, no como cuatro.

## 5. Validaciones implementadas

La aplicación incluye validaciones antes de ejecutar las acciones principales:

- Google Maps: mínimo 3 caracteres.
- Página web: URL válida y comienzo obligatorio con `https://`.
- Teléfono: entre 8 y 12 dígitos, con `+` opcional.
- Correo: validación mediante `Patterns.EMAIL_ADDRESS`.
- Formulario: nombre mínimo de 3 caracteres, correo válido y edad entre 1 y 120.
- Cámara: comprobación y solicitud de permisos en tiempo de ejecución.

Cuando una aplicación externa no está disponible, se controla `ActivityNotFoundException` y se muestra un mensaje al usuario.

## 6. Capturas de pantalla



### Menú principal

![Pantalla principal](screenshots/pantalla principal.jepg)




### Intents implícitos



### Catálogo y detalle



### Formulario y confirmación



## 7. Generación del APK Debug

El APK de depuración se genera mediante Gradle con:

### Windows

Desde la carpeta raíz del proyecto:

```powershell
.\gradlew.bat assembleDebug
```

### Linux/macOS

```bash
./gradlew assembleDebug
```

El archivo generado se encontrará en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

También puede generarse desde Android Studio mediante:

```text
Build → Generate App Bundles or APKs → Generate APKs
```

El APK debug resultante queda dentro de:

```text
app/build/outputs/apk/debug/
```

## 8. Instalación del APK Debug

Con un dispositivo Android conectado mediante ADB:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

También se puede instalar directamente desde Android Studio seleccionando un dispositivo físico o un emulador y ejecutando la aplicación.

## 9. Permisos

El proyecto declara los siguientes permisos relacionados con la funcionalidad de cámara y almacenamiento:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />
```

La cámara se declara como característica opcional para permitir la instalación en dispositivos que no dispongan de hardware de cámara.

## 10. Compilación desde cero

1. Abrir el proyecto `Prototipo3` en Android Studio.
2. Esperar a que finalice la sincronización de Gradle.
3. Comprobar que esté instalado un SDK compatible con API 36.
4. Conectar un dispositivo Android o crear un emulador.
5. Ejecutar la aplicación con **Run**.
6. Para generar el APK debug, ejecutar:

```powershell
.\gradlew.bat assembleDebug
```

7. Comprobar el resultado en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 11. Paquetes y dependencias

Las dependencias principales están definidas en `gradle/libs.versions.toml` y `app/build.gradle.kts`.

No se utilizan bases de datos ni servicios externos para la funcionalidad de los intents. Las acciones externas dependen de las aplicaciones compatibles instaladas en el dispositivo, como navegador, aplicación de mapas, teléfono, correo y cámara.
