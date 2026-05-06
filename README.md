# TuristApp

Aplicación móvil Android para explorar países del mundo y guardar destinos favoritos.

## Integrantes
- Ramiro
- Jeysson

---

## Funcionalidades principales

- Explorar países del mundo en una cuadrícula desplazable con banderas
- Buscar países por nombre en tiempo real
- Ver detalle de cada país: capital, región, población, área e idiomas
- Guardar y eliminar países favoritos localmente
- Navegación entre pantallas: Explorar → Detalle → Favoritos

---

## API utilizada

**REST Countries** — [https://restcountries.com/](https://restcountries.com/)

API pública y gratuita, sin necesidad de registro ni API key.
Campos usados: nombre, capital, región, subregión, población, área, banderas e idiomas.

---

## Capturas de pantalla

https://drive.google.com/drive/folders/1-IBetrI7IqoZ4W95AKw6jpKi7v2nTzzY

---

## Explicación de tecnologías utilizadas

### Room
Biblioteca de persistencia local de Android. Permite guardar datos en una base de datos
SQLite de forma estructurada usando anotaciones de Kotlin. En esta app se usa para
guardar y consultar los países marcados como favoritos, sin necesidad de conexión
a Internet.

Componentes usados:
- `@Entity` — define la tabla `lugares_turisticos`
- `@Dao` — contiene las operaciones insert, query, update y delete
- `@Database` — punto de acceso único a la base de datos local

### Retrofit
Biblioteca para consumir APIs REST desde Android. Convierte automáticamente las
respuestas JSON en objetos Kotlin usando Gson. En esta app se conecta a la API
de REST Countries para obtener la lista de países con sus datos e imágenes de banderas.

Componentes usados:
- `ServicioApi` — interfaz con los endpoints de la API
- `PaisDto` — modelo de datos que mapea el JSON recibido
- Corrutinas con `suspend` para llamadas asíncronas sin bloquear la UI

### Repository (Repositorio)
Capa intermedia entre los datos (Room y Retrofit) y la lógica de la app (ViewModel).
Su función es abstraer de dónde vienen los datos: el ViewModel no sabe si los datos
vienen de la base de datos local o de Internet, simplemente los solicita al repositorio.
Facilita las pruebas unitarias porque se puede reemplazar por un mock.

### ViewModel
Componente de arquitectura que sobrevive a los cambios de configuración (como rotar
la pantalla) y gestiona el estado de la interfaz. Expone los datos a Compose mediante
`StateFlow` y `Flow`, y llama al repositorio para obtener o modificar información.
En esta app hay dos ViewModels: `ExplorarViewModel` y `FavoritosViewModel`.

### Jetpack Compose
Framework moderno de Android para construir interfaces de usuario de forma declarativa
con Kotlin. En lugar de usar XML, la UI se describe como funciones `@Composable` que
se recomponen automáticamente cuando cambia el estado. En esta app se usan componentes
de Material Design 3: `Card`, `TopAppBar`, `LazyColumn`, `LazyVerticalGrid`,
`NavigationBar`, `OutlinedTextField` y `AlertDialog`, junto con animaciones como
`AnimatedVisibility` y `animateContentSize`.

---

## Dependencias principales

- Jetpack Compose + Material Design 3
- Room
- Retrofit + Gson
- Coil (carga de imágenes)
- Navigation Compose
- Coroutines

---
