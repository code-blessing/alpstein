#!/bin/bash

# Install dependencies
npm install

# Create src/assets directory
mkdir -p src/assets

# Create empty favicon.ico
touch src/favicon.ico

# Make the script executable
chmod +x init.sh 