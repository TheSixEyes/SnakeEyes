PIGDice Project Zip File - Contents and Verification
=====================================================

The PIGDice.zip file has been created in this directory and includes the following essential files:

REQUIRED FOR GRADLE BUILD:
- gradle/ (directory with wrapper files including gradle-wrapper.jar and gradle-wrapper.properties)
- gradlew (Gradle wrapper script for Unix/Mac)
- gradlew.bat (Gradle wrapper script for Windows)
- build.gradle.kts (root build script)
- settings.gradle.kts (settings file)
- gradle.properties (Gradle properties)

APPLICATION SOURCE CODE:
- app/src/ (all source code)
- app/build.gradle.kts (app module build script)
- app/proguard-rules.pro (ProGuard rules)

OTHER FILES:
- android/ (Android-specific files)
- README.md
- TRANSLATION_REFERENCE.md
- build_app.bat
- build_with_studio_jdk.bat
- .gitignore

FILES EXCLUDED (to reduce size):
- .gradle/ (Gradle cache)
- .idea/ (IDE settings)
- .kotlin/ (Kotlin cache)
- local.properties (machine-specific SDK path)
- build/ (build outputs)
- app/build/ (app build outputs)
- caches/ (cache directories)
- daemon/ (Gradle daemon files)
- All .apk and .aab files

TO BUILD ON WINDOWS:
1. Extract the PIGDice.zip file to a folder
2. Open Command Prompt in that folder
3. Run: gradlew.bat assembleDebug
   OR
   Run: build_app.bat

IMPORTANT:
- You will need to create a local.properties file in the extracted directory
- Add this line: sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
  (Replace with your actual Android SDK path on Windows)
- Or open the project in Android Studio and it will create this file automatically

The zip file DOES contain a Gradle build. If you're getting an error saying it doesn't,
please make sure:
1. You extracted the full zip file contents
2. You can see the gradle/ folder and gradlew files in the extracted directory
3. You're running the build command from the root directory (where build.gradle.kts is located)

