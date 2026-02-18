# Salt Launcher

Salt Launcher is an Android Minecraft: Java Edition launcher forked from Fold Craft Launcher (FCL) and rebranded to be fully independent.

## Highlights
- Modern UI with controller support and in‑app file browser.
- Multiple auth options: Microsoft accounts, Offline, AuthlibInjector; Ely.by server preloaded.
- Built‑in modpack install/download (CurseForge/Modrinth), profiles, JVM options, runtime installers.
- Update checker points only to this repository.
- Crash logs saved to `Android/data/com.saltlauncher.app/files/saltlauncher-crash.txt`.

## Build (CLI)
Prereqs: JDK 17, Android SDK with platforms 33 & 35, build-tools 33.0.2/34.0.0, NDK 27.

```bash
git clone https://github.com/syntexdevlol-ai/SaltLauncher.git
cd SaltLauncher
DISABLE_NDK_BUILD=1 ./gradlew --no-daemon FCL:assembleDebug
```
APK output: `FCL/build/outputs/apk/debug/`.

## CI
GitHub Actions workflow `.github/workflows/build.yml` builds `FCL:assembleDebug`, installs required SDK/NDK components in a user-writable path, and uploads `SaltLauncher-debug` artifact plus `build.log`.

## Branding
- App id: `com.saltlauncher.app`
- Theme: white primary, custom Salt “S” icon.

## License
GPL-3.0 (inherits from upstream FCL). See `LICENSE`.
