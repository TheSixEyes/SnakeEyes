#!/bin/bash

# Navigate to project directory
cd /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice

# Remove old zip if it exists
rm -f /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice.zip

echo "Creating PIGDice.zip with all necessary project files..."
echo "Excluding build artifacts and temporary directories..."

# Create zip with all project files except build artifacts
zip -r /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice.zip \
  . \
  -x "build/*" "caches/*" ".gradle/*" ".idea/*" ".kotlin/*" ".tmp/*" "daemon/*" "kotlin-profile/*" "native/*" "wrapper/*" "*.zip" ".DS_Store" "Thumbs.db"

echo ""
echo "Zip file created successfully!"
echo ""
echo "File size:"
ls -lh /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice.zip
echo ""
echo "Contents of zip:"
unzip -l /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice.zip | head -20
echo "... (truncated)"
echo ""
echo "IMPORTANT: This zip contains the complete project source code and documentation."
echo "It excludes build artifacts for a clean submission."
echo ""
echo "ZIP FILE LOCATION: /Users/taylor/Desktop/Code/AndroidStudioProjects/PIGDice.zip"
