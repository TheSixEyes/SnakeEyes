## PIGDice Project - Windows Build Instructions

### What Was Fixed

The previous zip file was excluding critical Gradle build files. The issue has been resolved and a new **PIGDice.zip** file has been created with all necessary files.

### What's Included in PIGDice.zip

✅ **Gradle Wrapper Files** (required for building):
- `gradle/wrapper/gradle-wrapper.jar`
- `gradle/wrapper/gradle-wrapper.properties`
- `gradlew` (Unix/Mac script)
- `gradlew.bat` (Windows script)

✅ **Gradle Build Scripts**:
- `build.gradle.kts` (root build file)
- `settings.gradle.kts` (settings file)
- `gradle.properties` (Gradle properties)
- `app/build.gradle.kts` (app module build file)

✅ **Source Code**:
- `app/src/` (all application source code)
- `app/proguard-rules.pro`
- `android/` directory

✅ **Documentation & Scripts**:
- `README.md`
- `TRANSLATION_REFERENCE.md`
- `build_app.bat` (Windows build script)
- `build_with_studio_jdk.bat` (Alternative Windows build script)

### How to Extract and Build on Windows

#### Method 1: Using Android Studio (Recommended)

1. **Extract the ZIP file**
   - Right-click PIGDice.zip → "Extract All..." or use 7-Zip/WinRAR
   - Extract to a folder like `C:\AndroidProjects\PIGDice`

2. **Open in Android Studio**
   - Launch Android Studio
   - File → Open → Select the extracted PIGDice folder
   - Android Studio will automatically:
     - Create the `local.properties` file
     - Download Gradle if needed
     - Sync the project

3. **Build the App**
   - Build → Make Project, or
   - Build → Build Bundle(s) / APK(s) → Build APK(s)

#### Method 2: Using Command Line

1. **Extract the ZIP file**
   - Extract PIGDice.zip to `C:\AndroidProjects\PIGDice`

2. **Set up Android SDK path**
   - Create a file named `local.properties` in the root folder (same level as build.gradle.kts)
   - Add this line (adjust path to your Android SDK):
     ```
     sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
     ```
   - Common Android SDK locations:
     - `C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk`
     - `C:\\Android\\Sdk`

3. **Build using the command line**
   - Open Command Prompt
   - Navigate to the project folder:
     ```
     cd C:\AndroidProjects\PIGDice
     ```
   - Run the build:
     ```
     gradlew.bat assembleDebug
     ```
   - Or use the batch file:
     ```
     build_app.bat
     ```

### Verifying the ZIP File Contents

After extracting, you should see:
```
PIGDice/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── app/
│   ├── src/
│   └── build.gradle.kts
├── android/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── ...other files
```

### Troubleshooting

**Error: "Gradle build not found"**
- Make sure you extracted the ENTIRE zip file
- Verify that `gradle/wrapper/gradle-wrapper.jar` exists in the extracted folder
- Verify that `gradlew.bat` exists in the root folder

**Error: "SDK location not found"**
- You need to create `local.properties` file (see Method 2, step 2)
- Or open the project in Android Studio which creates it automatically

**Error: "Permission denied" or "gradlew: command not found"**
- On Windows, use `gradlew.bat` not `gradlew`
- On Mac/Linux, use `./gradlew` after making it executable: `chmod +x gradlew`

**Build fails with "Could not download gradle-8.13"**
- Make sure you have internet connection
- The Gradle wrapper will automatically download Gradle on first build
- If behind a proxy, you may need to configure Gradle proxy settings

### Additional Notes

- The zip file excludes build outputs, cache files, and IDE-specific files to keep it small
- The `local.properties` file is intentionally excluded as it contains machine-specific paths
- All source code and Gradle configuration files ARE included

### Need Help?

If you're still having issues:
1. Verify you have Java Development Kit (JDK) 17 or later installed
2. Verify you have Android SDK installed (comes with Android Studio)
3. Check that your `local.properties` file has the correct SDK path
4. Try opening in Android Studio first - it handles configuration automatically

---

The PIGDice.zip file is now ready to be transferred to your Windows PC and built successfully!

