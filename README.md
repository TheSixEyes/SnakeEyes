# SnakeEyes

An Android dice game built with Kotlin and Jetpack Compose, featuring two-player gameplay (human vs computer AI), localization support, and customizable settings.

## Game Description

PIG is a two-player dice game where you compete against the computer to be the first to reach the winning score (default: 100 points, customizable from 50 to 9999).

### Rules

- **Rolling Dice**: Players roll two six-sided dice and score points equal to the sum shown
- **Rolling a 1 on one die**: Turn score resets to 0 and turn ends
- **Rolling 1 on both dice**: Total score resets to 0 and turn ends
- **Other rolls**: Points are added to your turn score
- **Hold**: Add turn score to total score and end your turn
- **Win Condition**: First player to reach the winning score wins
- **First Roll Protection**: Neither die can show a 1 on the first roll of a turn

### Features

- Two-player gameplay (Human vs Computer AI)
- Computer AI that rolls up to 3 times per turn with strategic decisions
- Smooth dice rolling animations with rotation and scaling effects
- Real-time score tracking with visual turn indicators
- Games won counter that persists across rounds
- Image-focused game over screens
- Customizable winning score (50-9999 points) via Settings
- About screen with developer information and support links
- Multi-language support with automatic locale detection (English and Spanish)
- Localized graphics for winner/loser screens per language

## Tech Stack

- Kotlin
- Jetpack Compose (Material Design 3)
- Kotlin Coroutines
- Android SDK (API 27+)

## Localization

### Supported Languages
- English (default)
- Spanish (Espanol)

The app automatically switches language based on device locale. All UI text, button labels, and winner/loser images are localized.

### Resource Locations
- English strings: `app/src/main/res/values/strings.xml`
- Spanish strings: `app/src/main/res/values-es/strings.xml`
- Localized images: `drawable/` and `drawable-es/` respectively

### Adding More Languages
1. Create `values-XX` folder (where XX is the language code)
2. Create `drawable-XX` folder for localized images
3. Copy and translate `strings.xml`

## Installation

### Requirements
- Android Studio (Ladybug or later recommended)
- JDK 17 or JDK 21
- Android SDK API 34+
- Gradle 8.7+

### Building
1. Clone this repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on an Android device or emulator (API 27+)

#### Command Line
```bash
# macOS/Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

APK output: `app/build/outputs/apk/debug/`

### Cross-Platform Java Setup

If you see a "Java home supplied is invalid" error, edit `gradle.properties` and either comment out `org.gradle.java.home` (recommended) or update it for your OS:

- **macOS**: `/Applications/Android Studio.app/Contents/jbr/Contents/Home`
- **Windows**: `C\:\\Program Files\\Android\\Android Studio\\jbr`
- **Linux**: `/opt/android-studio/jbr`

## License

Educational project - feel free to learn from and modify.

## Author

**Taylor Bethke** — [GitHub (@TheSixEyes)](https://github.com/TheSixEyes)
