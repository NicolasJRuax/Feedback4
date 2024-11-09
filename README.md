https://github.com/NicolasJRuax/Feedback4.git

# Gestor de Novelas

Aplicación de Android para gestionar una lista de novelas, donde los usuarios pueden agregar sus novelas favoritas, visualizarlas en un widget y sincronizar los datos con Firestore.

## Funcionalidades Principales

- **Agregar y eliminar novelas de favoritos**: Los usuarios pueden marcar o desmarcar novelas como favoritas, y estos cambios se guardan en Firebase Firestore.
- **Sincronización de datos**: La aplicación sincroniza automáticamente las novelas con Firestore y actualiza la interfaz.
- **Visualizar detalles de una novela**: Los usuarios pueden ver la información detallada de cada novela al seleccionarla.
- **Widget de favoritos**: El widget muestra una lista de las novelas marcadas como favoritas y se actualiza automáticamente cuando hay cambios.

## Estructura del Proyecto

### DB
- **AddNovelActivity**: Actividad para agregar una nueva novela a la base de datos.
- **NovelDAO**: Clase de acceso a datos que interactúa con Firebase Firestore.

### Fragments
- **NovelListFragment**: Fragmento que muestra la lista de novelas.
- **NovelDetailFragment**: Fragmento que muestra los detalles de una novela seleccionada.

### Novelas
- **Novel**: Clase que representa una novela, con atributos como título, autor, año y estado de favorito.
- **NovelAdapter**: Adaptador de RecyclerView que maneja la visualización de la lista de novelas.
- **NovelLoader**: Cargador asíncrono que obtiene la lista de novelas desde el repositorio.
- **NovelRepository**: Repositorio que interactúa con Firestore para obtener y modificar datos de las novelas.
- **NovelViewModel**: ViewModel que proporciona la lista de novelas y permite insertar o eliminar novelas.

### Sync
- **ConnectivityReceiver**: Receptor para manejar cambios en la conectividad de red.
- **SyncDataTask**: Tarea asíncrona para sincronizar los datos con Firestore.
- **SyncUtils**: Clase de utilidades para manejo de sincronización.

### Widget
- **NewAppWidget**: Proveedor de widget que configura y actualiza el widget con la lista de favoritos.
- **NovelRemoteViewsFactory**: Fábrica de vistas para cargar y mostrar las novelas favoritas en el widget.
- **WidgetService**: Servicio para manejar la carga de datos del widget.

### MainActivity
Actividad principal que maneja la navegación entre los fragmentos y proporciona opciones para agregar y sincronizar datos.

## Requisitos

- Android Studio
- Firebase Firestore configurado en el proyecto

## Cómo Funciona

1. **Agregar Novelas**: Desde la interfaz principal, los usuarios pueden agregar novelas usando el botón "Agregar Novela".
2. **Marcar como Favorito**: En la lista, los usuarios pueden marcar una novela como favorita. Este estado se guarda en Firestore y el widget se actualiza para reflejar los cambios.
3. **Visualización en el Widget**: El widget muestra la lista de novelas favoritas y se actualiza automáticamente al cambiar el estado de favorito de cualquier novela.
4. **Sincronización de Datos**: La aplicación sincroniza los datos en intervalos regulares o al activar el botón de sincronización.


