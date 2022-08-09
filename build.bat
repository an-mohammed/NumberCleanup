@echo off

cls
echo [INFO] Number-Cleanup Build Starts

echo [INFO] Cleaning all modules...
call mvn clean
echo [INFO] Cleaning completed...

echo [INFO] Installing all modules...
call mvn -DskipTests=true install
echo [INFO] Installation all modules completed...

echo [INFO] Number-Cleanup Build Ends

rem call startAll.cmd