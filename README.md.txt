https://github.com/NicolasJRuax/GestorNovelasNJR.git

IMPORTANTE!!  El código de la parte 3 no está en el master, esta en la rama Parte 3

# Gestión de Novelas

## Descripción

**Gestión de Novelas** es una aplicación Android desarrollada para facilitar la organización y manejo de una colección de novelas. Los usuarios pueden agregar, editar, eliminar y visualizar detalles de sus novelas favoritas. La aplicación emplea **Android Room** para la gestión de la base de datos y **LiveData** para mantener la interfaz de usuario sincronizada automáticamente con los datos.

## Funcionalidades

- **Agregar Novelas**: Añade novelas nuevas proporcionando detalles como título, autor, año de publicación y sinopsis.
- **Eliminar Novelas**: Elimina novelas de la colección, con una confirmación para evitar borrados accidentales.
- **Ver Detalles**: Muestra una vista completa de los detalles de cada novela, incluyendo título, autor, año y sinopsis.
- **Lista de Novelas**: Presenta las novelas en una lista que permite navegación fácil y rápida.
- **Modo Oscuro**: Alterna entre modo claro y oscuro según las preferencias del usuario, adaptando los colores de la interfaz para mejorar la experiencia de lectura en diferentes condiciones de luz.

## Tecnologías Utilizadas

- **Android SDK**: Herramientas y API de Android para el desarrollo móvil.
- **Room Database**: Biblioteca de persistencia de datos en Android, basada en SQLite.
- **LiveData**: Componentes de arquitectura de Android para la gestión de datos observables y reactivos.
- **RecyclerView**: Componente eficiente para la visualización de listas.
- **Firebase Firestore**: Integración para la sincronización y almacenamiento de datos en la nube (opcional).
- **Material Components**: Componentes de diseño de Material para una interfaz moderna y responsiva.

## Estructura del Proyecto

- **MainActivity**: Actividad principal que gestiona la interfaz de usuario y muestra la lista de novelas.
- **SettingActivity**: Actividad dedicada a las configuraciones, incluyendo la opción de activar o desactivar el modo oscuro.
- **Novel**: Entidad de datos que representa una novela en la base de datos.
- **NovelDao**: Interfaz que define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad Novel.
- **NovelRepository**: Clase que encapsula la lógica de acceso a datos y se comunica con la base de datos.
- **NovelViewModel**: Intermediario entre la interfaz de usuario y el repositorio de datos, gestionando el ciclo de vida de los datos.
- **NovelAdapter**: Adaptador para el RecyclerView, encargado de la visualización de cada elemento de novela en la lista.
- **ThemeUtils**: Clase utilitaria para aplicar el modo oscuro o claro basado en las preferencias del usuario.
