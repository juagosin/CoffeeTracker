# AGENTS.md - CoffeeTracker Project Guide

Este documento proporciona una visión general del proyecto **CoffeeTracker** para ayudar a futuros desarrolladores (y agentes de IA) a entender rápidamente la arquitectura, el stack tecnológico y las convenciones de código.

## 🚀 Arquitectura: Clean Architecture

El proyecto sigue una estructura de **Clean Architecture** dividida en capas para garantizar la escalabilidad y mantenibilidad.

### 🧱 Capas del Proyecto

1.  **`domain`**: Contiene la lógica de negocio pura. Es independiente de cualquier otra capa.
    *   **Models**: Entidades de negocio (`Coffee`, `Achievement`).
    *   **Repository Interfaces**: Definiciones de cómo acceder a los datos.
    *   **Use Cases**: Clases individuales para cada acción del usuario (ej. `AddCoffeeUseCase`). Se agrupan en `CoffeeUseCases` para facilitar la inyección.
2.  **`data`**: Implementación de los repositorios y fuentes de datos.
    *   **Local Storage**: Uso de **Room** para la base de datos y **DataStore** para preferencias (`CoffeePreferencesManager`).
    *   **Mappers**: Funciones de extensión para convertir entre entidades de base de datos y modelos de dominio.
    *   **Repository Implementation**: Implementación real de las interfaces del dominio.
3.  **`ui`**: Capa de presentación.
    *   **Jetpack Compose**: UI declarativa.
    *   **MVI (Lite)**: Uso de `State`, `Event` y `Effect` (si aplica) para manejar el flujo de datos.
    *   **ViewModels**: Gestionan el estado de la UI usando `StateFlow` y ejecutan los Use Cases.

## 🛠️ Stack Tecnológico

*   **Language**: Kotlin (100%)
*   **Dependency Injection**: Hilt (Dagger)
*   **Database**: Room
*   **Preferences**: Jetpack DataStore
*   **UI Framework**: Jetpack Compose
*   **Asynchronous Flow**: Kotlin Coroutines & Flow
*   **Navigation**: Navigation Compose

## 📁 Estructura de Paquetes

```text
com.calleserpis.coffeetracker
├── data
│   ├── dao             # Room DAOs
│   ├── datastore       # Preferencias (Preferences Manager)
│   ├── mapper          # Mappers (Entity <-> Domain)
│   ├── repository      # Implementaciones de Repository
│   └── notification    # Helpers para notificaciones
├── di
│   └── AppModule       # Inyección de dependencias (Hilt)
├── domain
│   ├── model           # Entidades de dominio y stats
│   ├── repository      # Interfaces de Repository
│   └── use_case        # Casos de uso (Lógica de negocio)
└── ui
    ├── common          # Componentes reutilizables
    ├── navigation      # Rutas y grafos de navegación
    ├── screens         # Pantallas (Estado, Evento, ViewModel, Screen)
    └── theme           # Definiciones de colores y tipografía
```

## 📜 Estándares de Codificación

1.  **Inyección de Use Cases**: No inyectar `Repository` directamente en el `ViewModel`. Usar el wrapper `CoffeeUseCases` que agrupa todos los casos de uso relacionados.
2.  **Mappers**: Toda transformación entre capas debe hacerse mediante mappers claros (ej. `toDomain()`, `toEntity()`).
3.  **Estado de UI**: Usar `StateFlow` en los `ViewModel` para exponer un objeto de estado único (ej. `HomeState`). Actualizar el estado usando `.update { ... }`.
4.  **Eventos de UI**: Las interacciones del usuario se envían al `ViewModel` a través de clases `Event` (ej. `HomeEvent`).
5.  **Room Migrations**: Siempre definir migraciones explícitas al cambiar el esquema de la base de datos (`MIGRATION_X_Y`).

## 💡 Notas para Agentes

*   Al añadir una nueva funcionalidad, empieza por el `domain` (nuevo UseCase), sigue por el `data` (si necesitas persistencia) y termina en la `ui`.
*   Asegúrate de registrar los nuevos UseCases en `AppModule.kt`.
*   Sigue el patrón de "Screen, ViewModel, State, Event" para cada nueva vista.
