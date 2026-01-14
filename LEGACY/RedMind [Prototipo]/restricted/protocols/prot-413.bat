@echo off
mode con: cols=200 lines=60
color 4F
title RedMind - EMERGENCY PROTOCOL 413

echo.
echo [!] EMERGENCY PROTOCOL 413 INITIATED
echo ---------------------------------------
echo Iniciating subsystem isolation...
timeout /t 2 >nul
echo Core modules: OFFLINE
timeout /t 1 >nul
echo Network Layer: DISCONNECTED
timeout /t 1 >nul
echo Visual interface: DISENGAGED
timeout /t 1 >nul
echo Command input: LIMITED
timeout /t 2 >nul
echo ---------------------------------------
echo [X] SYSTEM LOCKDOWN ACTIVE
echo.
echo Press any key to return control to manual override...
pause >nul
exit
