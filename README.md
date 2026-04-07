# SnakeEyes

An Android dice game built with Kotlin and Jetpack Compose, featuring an integrated About screen with web-based support pages.

## Game Description

PIG is a two-player dice game where you compete against the computer to be the first to reach the winning score (default: 100 points, customizable).

### Game Rules

- **Rolling Dice**: Players roll two six-sided dice and score points equal to the sum shown
- **Rolling a 1 on one die**: Turn score resets to 0 and turn ends
- **Rolling 1 on both dice**: Total score resets to 0 and turn ends
- **Other rolls**: Points are added to your turn score
- **Hold**: Add turn score to total score and end your turn
- **Win Condition**: First player to reach the winning score wins!
- **First Roll Protection**: Neither die can show a 1 on the first roll of a turn
- **Customizable Winning Score**: Change the target score from 50 to 9999 points via Settings

### Features

- ✨ Two-player gameplay (Human vs Computer AI)
- 🤖 Computer AI that rolls up to 3 times per turn with strategic decisions
- 🎲 Smooth dice rolling animations with rotation and scaling effects
- 📊 Real-time score tracking with visual turn indicators
- 🏆 Games won counter that persists across rounds
- 🎉 **Image-focused game over screens** - Winner/loser images take center stage with minimal text
- 🎨 Material Design 3 UI with modern aesthetics
- 🛡️ Strategic first-roll protection preventing immediate losses
- ⚙️ **Customizable winning score** - Set your own target (50-9999 points)
- ℹ️ **About screen** with developer information and support links
- 🌐 **Web-based support pages** for contact and company information
- 🌍 **Multi-language support** - Fully localized for English and Spanish
- 🔄 **Automatic language detection** - Switches based on device locale
- 🖼️ **Localized graphics** - Winner/loser images change based on language

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design**: Material Design 3
- **State Management**: Compose State
- **Async Operations**: Kotlin Coroutines
- **Web Pages**: HTML/CSS/JavaScript, PHP
- **Deployment**: GitHub Pages ready

## App Screens
### Main Game Screen
Displays:
1. Game title with Settings and About buttons
2. Who is currently rolling dice (highlighted)
3. Total Games won for each player
4. Total Score for current game
5. TURN score for current rolls
6. Dice images for current roll of both dice
7. Total number for the sum of both dice currently rolled
8. Appropriate Labels for all the scores
9. ROLL Button
10. HOLD button

### About Screen
Features:
- Developer name and information
- Educational background (CIS217, Spokane Community College)
- Tech stack and skills showcase
- Links to support website
- Links to company/about page
- Clean, professional design matching app theme

## Localization

The PIG Dice Game supports **multiple languages** with automatic locale detection.

### Supported Languages
- 🇺🇸 **English** (Default)
- 🇪🇸 **Spanish (Español)**

### Testing Localization

1. **Change Device Language:**
   - Go to Settings → System → Languages & input → Languages
   - Add or move "Español" to the top
   - Return to the app - all text will be in Spanish

2. **What Changes:**
   - Game title: "PIG" → "CERDO"
   - Buttons: "ROLL" → "TIRAR", "HOLD" → "MANTENER"
   - Labels: "You" → "Tú", "Computer" → "Computadora"
   - All UI text, messages, and About screen content
   - **Winner/Loser images** automatically switch to Spanish versions

### Localized Resources
- **Text Strings:**
  - English: `app/src/main/res/values/strings.xml`
  - Spanish: `app/src/main/res/values-es/strings.xml`
- **Images:**
  - English: `app/src/main/res/drawable/winner.jpg`, `loser.jpg`
  - Spanish: `app/src/main/res/drawable-es/winner.png`, `loser.jpg`

### Game Settings

Access the Settings menu to customize your game:
- **Winning Score**: Change from default 100 to any value between 1-9999
- Settings persist during the current session
- Access via "Settings" button on main screen

### Adding More Languages

To add a new language:
1. Create `values-XX` folder (where XX is language code)
2. Create `drawable-XX` folder for localized images
3. Copy and translate `strings.xml`
4. Add localized winner/loser images if desired

### Translation Files
- English: `app/src/main/res/values/strings.xml`
- Spanish: `app/src/main/res/values-es/strings.xml`

## Installation

### Requirements
- Android Studio (Ladybug or later recommended)
- JDK 17 or JDK 21 (Android Studio's bundled JBR works best)
- Android SDK API 34+
- Gradle 8.7+

### Cross-Platform Setup

#### For macOS Users

If you receive a build error `"Java home supplied is invalid"` when opening this project:

1. Open `gradle.properties` in the project root
2. Find the commented section about `org.gradle.java.home`
3. **Either:**
   - **Option A (Recommended)**: Keep the line commented out to let Android Studio auto-detect the JDK
   - **Option B**: Uncomment and update the path to:
     ```properties
     org.gradle.java.home=/Applications/Android Studio.app/Contents/jbr/Contents/Home
     ```
     (Adjust if Android Studio is installed elsewhere)
4. Delete `local.properties` if it exists (will be auto-generated)
5. Sync Gradle in Android Studio

#### For Windows Users

If you encounter Java version issues:

1. Open `gradle.properties`
2. Uncomment the `org.gradle.java.home` line and set it to:
   ```properties
   org.gradle.java.home=C\:\\Program Files\\Android\\Android Studio\\jbr
   ```
   (Adjust based on your Android Studio installation location)

#### For Linux Users

1. Open `gradle.properties`
2. Uncomment the `org.gradle.java.home` line and set it to:
   ```properties
   org.gradle.java.home=/opt/android-studio/jbr
   ```
   (Adjust based on your Android Studio installation location)

### Building

1. Clone this repository
2. Follow the platform-specific setup above if needed
3. Open in Android Studio
4. Sync Gradle files
5. Run on an Android device or emulator (API 27+)

#### Command Line Build

**macOS/Linux:**
```bash
./gradlew assembleDebug
```

**Windows:**
```cmd
gradlew.bat assembleDebug
```

APK location: `app/build/outputs/apk/debug/`

## Troubleshooting

### "Java home supplied is invalid" Error
This happens when `gradle.properties` contains a Java path from a different OS.

**Solution**: Edit `gradle.properties` and either comment out `org.gradle.java.home` or update it for your OS (see setup instructions above).

### "Java version 25" Error
Your system Java is too new. Android builds require JDK 17 or 21.

**Solution**: Set `org.gradle.java.home` in `gradle.properties` to Android Studio's bundled JDK (see platform instructions above).

### Gradle Sync Issues
1. **File** > **Invalidate Caches / Restart** in Android Studio
2. Delete `.gradle` and `build` folders
3. Re-sync Gradle

## Distribution Notes

When distributing this project (zip, git, etc.):

1. **Do NOT include** `local.properties` (machine-specific SDK paths)
2. **Do NOT include** build artifacts (`build/`, `.gradle/`, `*.apk`)
3. **Consider commenting out** `org.gradle.java.home` in `gradle.properties` for maximum compatibility
4. Direct recipients to this README for setup instructions

## Developer Information

**Name**: Taylor  
**Course**: CIS217 - Spokane Community College  
**Semester**: Fall 2025  
**Graduation**: After Winter Quarter 2026

### About the Developer
Full-stack developer passionate about desktop and mobile applications, modular UI, and the pursuit of clean, maintainable code and documentation that doesn't suck. Experienced with tens of thousands of hours on Windows, bringing both technical depth and practical experience to every project.

### Tech Stack
**Languages & Frameworks**: C# (.NET/WPF), C++, Rust, Kotlin, PHP, React (JSX), SQL, GO, Python, HTML5 & CSS, TypeScript  
**Platforms**: Windows 11, MacOS, Linux

## License

Educational project - feel free to learn from and modify.

## Author

**Taylor Bethke** — [GitHub (@TheSixEyes)](https://github.com/TheSixEyes)

