@echo off
echo ================================
echo Subiendo cambios a GitHub...
echo ================================

git add .
git commit -m "Auto commit"
git push

echo ================================
echo Listo. No la has liado.
echo ================================
pause