@echo off
echo ================================
echo Subiendo cambios a GitHub...
echo ================================

git add .
git commit -m "Auto commit"
git push -u origin main --force

echo ================================
echo Listo. No la has liado.
echo ================================
pause