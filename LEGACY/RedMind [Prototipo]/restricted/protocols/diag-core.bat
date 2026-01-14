@echo off
mode con: cols=200 lines=60
color 1F
title RedMind - CORE DIAGNOSTIC PROTOCOL

echo.
echo === CORE SYSTEM DIAGNOSTICS ===
echo Verifying core subsystems...
timeout /t 1 >nul
echo - Memory blocks: OK
timeout /t 1 >nul
echo - Neural Net Layer: OK
timeout /t 1 >nul
echo - Root Chain Integrity: OK
timeout /t 1 >nul
echo All diagnostics passed.

echo.
echo Do you want to reboot the core system? (Y/N)
set /p input=Choice: 
if /I "%input%"=="Y" (
    echo Rebooting core system...
    timeout /t 2 >nul
    echo System reboot complete.
) else (
    echo Aborted by user.
)
echo.
pause >nul
exit
