# f1companion

Android companion app for Formula 1 — a Kotlin + Jetpack Compose app with Ktor networking.

This repository contains an Android application built with Kotlin, Jetpack Compose, and Ktor. It provides a small companion app (package id: `com.example.f1companion`) and is configured with modern Android tooling (Gradle Kotlin DSL, Compose, and AndroidX libraries).

## At a glance

- ApplicationId: `com.example.f1companion`
- Compile SDK: 36
- Min SDK: 36
- Target SDK: 36
- Kotlin Serialization plugin: 1.9.22
- UI: Jetpack Compose (Material3)
- Networking: Ktor client (Android)

## Project structure

- `app/` — Android application module (source, resources, Gradle config)
- `build.gradle.kts` — top-level Gradle Kotlin DSL build file
- `settings.gradle.kts` — project settings and included modules
- `gradle.properties` — Gradle configuration used by the project

## Key files

- `app/src/main/AndroidManifest.xml` — manifest and launcher activity
- `app/build.gradle.kts` — module configuration (compose, kotlin, dependencies)
- `gradle/libs.versions.toml` — centralized dependency versions (see `gradle/` folder)

## Prerequisites

- Java 11 (project targets Java 11)
- Android SDK (API 36) and Android platform tools
- Android Studio (recommended) or command-line Gradle
- Gradle wrapper included in the repo (`./gradlew`)

## Quick start — build and run

1. Open the project in Android Studio (recommended) and let it sync Gradle.
2. To build from the command line (macOS / zsh):

```bash
# build debug APK
./gradlew :app:assembleDebug

# install to a connected device (debug)
./gradlew :app:installDebug
```

3. Or, run via Android Studio Run/Debug targets.

## Tests

Unit and instrumentation tests are configured in the `app` module. Run:

```bash
# run unit tests
./gradlew test

# run connected instrumentation tests (if a device/emulator is available)
./gradlew connectedAndroidTest
```

## Dependencies & Tooling

The module uses the AndroidX and Jetpack Compose BOM for UI, Ktor for networking, and Kotlin Serialization for JSON. Version pins are centralized in `gradle/libs.versions.toml` and the `gradle` folder.

Notable dependencies (excerpt):

- androidx.core:core-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.activity:activity-compose
- androidx.compose.material3
- io.ktor:ktor-client-android
- kotlinx-serialization (via plugin)

## Contributing

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make changes, add tests, and ensure the project builds: `./gradlew assembleDebug`.
4. Commit and open a pull request against the `main` branch.

If you want me to open the README in a branch and push it for you, ensure your repo remote is configured and you have push access.

## License

No license file detected in the repository. Add a `LICENSE` file in the project root if you want to explicitly state terms (MIT, Apache 2.0, etc.).

## Contact

Repository / GitHub: `https://github.com/xplictly/f1companion`

---

Generated README created by an automated assistant using the repository configuration found in the project files. Edit or expand this file with screenshots, feature descriptions, and contributor guidelines as you see fit.
