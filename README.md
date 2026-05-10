# ☕ Coffee Tracker

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21%2B-blue?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-orange?logo=jetpackcompose)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Status](https://img.shields.io/badge/Version-4.2.1-brightgreen)
[![Get it on Google Play](https://img.shields.io/badge/Google_Play-Get_it_on-brightgreen?logo)](https://play.google.com/store/apps/details?id=com.calleserpis.coffeetracker)
[![GitHub](https://img.shields.io/badge/GitHub-juagosin-black?logo=github)](https://github.com/juagosin)
---

## 🧩 Descripción

**Coffee Tracker** es una aplicación Android para registrar y analizar tus hábitos de consumo de café.
Su objetivo es ofrecer una experiencia moderna y minimalista para llevar un control detallado de cuántos cafés tomas, qué tipos prefieres y cuándo los consumes.

La interfaz está disponible en **varios idiomas**, entre ellos **español**, **inglés** y **ruso** (recursos en `values-ru`), además de catalán, francés, italiano e indonesio.

---

## ✨ Características principales

- ☕ **Registro rápido de cafés**
Añade cafés con un solo toque. Elige entre 8 tipos diferentes.
- 🏠 **Pantalla principal (Home)**
Muestra el contador de hoy, el historial reciente de los últimos 7 días y tiempo desde el último café.
- 📲 **Widget en el escritorio**
Muestra el **total de cafés** registrados y los **de hoy**; al tocar puedes abrir la app o ir directo a **añadir un café**.
- 🏆 **Sistema de logros y retos**

- 🧠 **Arquitectura moderna (MVVM)**  
  Basada en buenas prácticas de Android para garantizar escalabilidad y mantenibilidad.

- 💉 **Inyección de dependencias con Dagger Hilt**

- 🗄️ **Persistencia local con Room Database**

- 🎨 **Interfaz construida en Jetpack Compose (Material 3)**

---

## 🛠️ Tecnologías utilizadas

| Categoría | Tecnología |
|------------|-------------|
| **Lenguaje** | [Kotlin](https://kotlinlang.org/) |
| **UI Toolkit** | [Jetpack Compose](https://developer.android.com/jetpack/compose) |
| **Arquitectura** | MVVM |
| **DI Framework** | [Dagger Hilt](https://dagger.dev/hilt/) |
| **Base de datos local** | [Room](https://developer.android.com/training/data-storage/room) |
| **Navegación** | [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) |
| **Asincronía** | Coroutines + Flow |
| **Estilo visual** | Material Design 3 |

---

## 📱 Capturas de pantalla


| Pantalla principal | Estadísticas | Nuevo Cafe|
|--------------------|-------------------|-------------------|
| ![Home Screenshot](screenshots/Home.png) | ![Estadistica Screenshot](screenshots/Estadisticas.png) | ![New Screenshot](screenshots/New.png) |

## 📱 Darkmode


| Pantalla principal | Estadísticas | Nuevo Cafe|
|--------------------|-------------------|-------------------|
| ![Home Screenshot](screenshots/HomeDark.png) | ![Estadistica Screenshot](screenshots/EstadisticasDark.png) | ![New Screenshot](screenshots/NewDark.png) |

---
## 📲 Descargar la app

[<img alt="Descargar en Google Play" src="https://play.google.com/intl/es/badges/static/images/badges/es_badge_web_generic.png" width="200" height="70"/>](https://play.google.com/store/apps/details?id=com.calleserpis.coffeetracker)


¡Descarga Coffee Tracker ahora y empieza a trackear tus cafés! ☕


---

## 🧪 Estado del proyecto

🟢 **Versión actual:** `v4.2.1`  
🔧 Proyecto en desarrollo activo.  
Se planifican futuras actualizaciones para:
- Refinamiento visual.
- 📊 Nuevas estadísticas

### Novedades en v4.2.1

- **Widget de inicio:** resumen de cafés (total y hoy) y acceso rápido para añadir uno nuevo.
- **Interfaz en ruso:** recursos de strings y del widget alineados con `values-ru`.

### Novedades en v4.1.0

- Navegación mejorada al usar botón atrás:
  - Desde `Home`, el botón atrás cierra la app.
  - Desde otras pantallas, vuelve a la pantalla anterior (o `Home` como fallback).
- Corrección en `Stats` para refrescar métricas resumen al volver a la pantalla y evitar mostrar datos vacíos cuando ya existen cafés registrados.

---

## 📝 Licencia

Este proyecto está bajo la licencia **[MIT](https://es.wikipedia.org/wiki/Licencia_MIT)**.  
Puedes usar, modificar y distribuir el código libremente, siempre que se mantenga la atribución correspondiente.

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas!  
Si encuentras errores o deseas proponer mejoras, por favor abre un **issue** o envía un **pull request**.

---
## ⭐ Dale una estrella
Si te gusta este proyecto, ¡dale una estrella! ⭐
Ayuda a otros desarrolladores a encontrarlo.

---

## 👨‍💻 Autor

**Juan Gómez**  
Desarrollador Android  
[![GitHub](https://img.shields.io/badge/GitHub-juagosin-black?logo=github)](https://github.com/juagosin)

---

> _"El café es el mejor amigo del hombre. Te ayuda a pensar, te mantiene despierto y te da energía." — Desconocido_
