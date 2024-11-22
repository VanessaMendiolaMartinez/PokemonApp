### 🎮 Pokemon App


### 📝 Descripción
Pokemon App es una aplicación Android que permite a los usuarios:

    1.Iniciar sesión.
    2. Ver una lista de Pokémon y sus detalles. 
    3. Modificar su perfil, incluyendo la opción de cambiar su avatar utilizando la cámara o la galería. 
    4. Esta app sigue MVVM, utiliza buenas prácticas de desarrollo y una arquitectura limpia para garantizar mantenibilidad y escalabilidad.



### 🚀 Características
    🔑 Login: Utiliza Room para el registro y consulta de usuarios.
    🐾 Listado de Pokémon: Consume la API pública de Pokémon para mostrar una lista en un RecyclerView.
    📄 Detalles de Pokémon: Muestra la información de cada Pokémon en un fragmento.
    📸 Perfil: Modifica el avatar del usuario utilizando cámara o galería.
    📐 Arquitectura MVVM: Separa responsabilidades entre las capas de la aplicación.
    🌐 Retrofit: Utilizado para consumir la PokeAPI.
    🔒 Validación de permisos: Maneja permisos para acceder a la cámara y galería.
    💉 Hilt: Inyección de dependencias para gestionar módulos.
    ✍️ Comentarios: El código está documentado para explicar cada función.



### 💻 Tecnologías Utilizadas

    -Android Studio: IDE para el desarrollo de la app.
    -Kotlin: Lenguaje de programación utilizado.
    -MVVM: Arquitectura Model-View-ViewModel.
    -Retrofit: Para la comunicación con la API de Pokémon.
    -Room: Base de datos local para almacenamiento de usuarios.
    -Hilt: Inyección de dependencias.
    -Coroutines: Para la ejecución de tareas en segundo plano.
    -Navigation Component: Para la gestión de la navegación entre pantallas.
    -Permissions: Gestión de permisos para acceder a la cámara y galería.
    -Clean Architecture: Para estructurar el proyecto en capas y mantener la escalabilidad.
    -SOLID Principles: Para seguir las mejores prácticas de diseño de software.



### 📂 Estructura del Proyecto

- app/
    - src/
        - main/
            - java/
                - com/
                    - example/
                        - pokemonapp/
                            - data/               # Capa de datos (Room, Retrofit, Repositorios)
                            - di/                 # Dependencias (Hilt)
                            - domain/             # Capa de dominio (Modelos y UseCases)
                            - ui/                 # Capa de presentación (Fragmentos, ViewModels)
                            - utils/              # Utilitarios (Validaciones)
            - res/
                - layout/               # Archivos XML de layouts
                - values/               # Dimens, strings, styles




### ⚙️ Cómo Configurar el Proyecto

        ### Requisitos:
            1. Android Studio 4.0 o superior.
            2. SDK de Android 30 o superior.
            3. Conexión a internet para descargar las dependencias.




### 📱 Navegación

    La app incluye las siguientes pantallas:

1. SplashFragment: Pantalla de inicio.
2. SignOnFragment: Registro de usuarios.
3. LoginFragment: Iniciar sesión.
4. PokemonListFragment: Lista de Pokémon.
5. PokemonDetailFragment: Detalles de un Pokémon seleccionado.
6. ProfileFragment: Configuración y cambio de avatar


### 🌐 API Utilizada
La aplicación consume datos de Pokémon utilizando la PokeAPI.
URL: https://pokeapi.co/api/v2/pokemon?offset=300&limit=100


### 🔒 Permisos
    La aplicación requiere permisos para:
    
    CAMERA: Tomar fotos desde la cámara para cambiar el avatar.
    READ_EXTERNAL_STORAGE: Seleccionar fotos desde la galería.
