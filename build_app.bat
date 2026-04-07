@echo off
REM Build script that forces Gradle to use system Java (Java 25)
REM Works around the Kotlin compiler Java 25 parsing issue

cd /d "%~dp0"

REM Set JAVA_HOME temporarily to system Java
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Run Gradle build with system properties to work around Kotlin issue
gradlew.bat clean assembleDebug -Dkotlin.daemon.jvm.options="-Xmx2g" --no-daemon --refresh-dependencies

pause

