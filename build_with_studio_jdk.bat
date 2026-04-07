@echo off
REM This script builds the project using Android Studio's bundled JDK
REM Update the JAVA_HOME path below if your Android Studio is installed elsewhere

set "JAVA_HOME=F:\IDEs\Android\Android Studio\jbr"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Using Java from: %JAVA_HOME%
java -version

echo.
echo Building project...
call gradlew.bat assembleDebug

pause

