# GymFlow Mobile

Android application for the GymFlow Gym Management System, built with Kotlin.

## Tech Stack

- **Kotlin** — Primary language
- **Retrofit 2** — HTTP client for API calls
- **OkHttp** — Network layer with logging
- **Gson** — JSON serialization
- **Coroutines** — Async operations
- **ViewBinding** — View interaction
- **Material Design 3** — UI components

## User Roles

| Role | Access |
|---|---|
| **Member** | Gym booking, class history, attendance history, profile |
| **Instruktur** | Class schedule, attendance marking, leave requests, history |
| **Manajer Operasional** | Instructor attendance management |

## Prerequisites

- Android Studio Meerkat (2024.3.2) or later
- Android SDK API 26+
- [gymflow-api](https://gymflow-api-production.up.railway.app/api/) running

## Getting Started
```bash
# Clone the repository
git clone https://github.com/xkendrickz/gymflow-android.git
```

Open the project in Android Studio, then update the base URL in `RetrofitClient.kt`:
```kotlin
// For emulator
private const val BASE_URL = "http://10.0.2.2:8000/api/"

// For physical device (use your computer's local IP)
private const val BASE_URL = "http://192.168.x.x:8000/api/"

// For production
private const val BASE_URL = "https://gymflow-api-production.up.railway.app/api/"
```

Run the app on an emulator or physical device via Android Studio.

## Project Structure
```
app/src/main/java/com/example/p3l_android/
├── api/
│   ├── ApiService.kt        # Retrofit interface
│   └── RetrofitClient.kt    # Retrofit singleton
├── models/                  # Data classes
├── adapters/                # RecyclerView adapters
├── memberView/              # Member screens & fragments
├── instrukturView/          # Instruktur screens & fragments
├── pegawaiView/             # Pegawai screens & fragments
└── MainActivity.kt          # Login screen
```

## Build Requirements
```
minSdk: 26 (Android 8.0)
targetSdk: 34
Gradle: 7.4
Java: 17
```

## Key Dependencies
```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:okhttp:4.10.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.10.0'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
```

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/b9934458-6fa0-45cc-a204-00c6c59399ad" width="150"/>
</p>

## Related Repositories

- [gymflow-web](https://github.com/YOUR_USERNAME/gymflow-web) — Vue.js frontend
- [gymflow-api](https://github.com/YOUR_USERNAME/gymflow-api) — Laravel REST API backend
- [gymflow](https://github.com/YOUR_USERNAME/gymflow) — Project overview
