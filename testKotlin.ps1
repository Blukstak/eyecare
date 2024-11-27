# Step 1: Check if Kotlin is installed
Write-Output "Checking if Kotlin is accessible..."
$kotlinVersion = & kotlin -version 2>&1
if ($kotlinVersion -match "Kotlin version") {
    Write-Output "Kotlin is installed and accessible:"
    Write-Output $kotlinVersion
} else {
    Write-Output "Kotlin is not accessible. Please check the PATH configuration or Kotlin installation."
    exit
}

# Step 2: Check if Java is installed
Write-Output "`nChecking if Java is installed..."
$javaVersion = & java -version 2>&1
if ($javaVersion -match "version") {
    Write-Output "Java is installed and accessible:"
    Write-Output $javaVersion
} else {
    Write-Output "Java is not accessible. Please check the JAVA_HOME configuration or Java installation."
    exit
}

# Step 3: Check Kotlin extension directory
Write-Output "`nChecking Kotlin Language Client extension folder..."
$extensionDir = "$env:USERPROFILE\.vscode\extensions\fwcd.kotlin-0.2.35"
if (Test-Path $extensionDir) {
    Write-Output "Extension directory exists: $extensionDir"
} else {
    Write-Output "Kotlin extension directory not found. Try reinstalling the Kotlin extension in VS Code."
    exit
}

# Step 4: Check if Node.js is available
Write-Output "`nChecking Node.js installation..."
$nodeVersion = & node -v 2>&1
if ($nodeVersion -match "v") {
    Write-Output "Node.js is installed and accessible:"
    Write-Output $nodeVersion
} else {
    Write-Output "Node.js is not accessible. Ensure Node.js is installed and added to PATH."
    exit
}

# Step 5: Check PATH environment variables
Write-Output "`nChecking PATH environment variables..."
if ($env:PATH -match "kotlin" -and $env:PATH -match "java") {
    Write-Output "PATH appears to include Kotlin and Java paths:"
    Write-Output $env:PATH
} else {
    Write-Output "PATH is missing Kotlin or Java paths. Please check the configuration."
}

# Step 6: Test Kotlin language server directly (if possible)
Write-Output "`nAttempting to start Kotlin language server..."
$languageServerPath = Join-Path -Path $extensionDir -ChildPath "server\bin\kotlin-language-server"
if (Test-Path $languageServerPath) {
    Write-Output "Language server binary found: $languageServerPath"
    Start-Process $languageServerPath -NoNewWindow
} else {
    Write-Output "Kotlin language server binary not found in extension directory."
    exit
}

Write-Output "`nDebug script completed."
