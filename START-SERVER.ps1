# Si 'uv' esta disponible, se usa.
if (Test-Path -Path "uv.lock" -PathType Leaf) {
    Write-Host "Usando uv para iniciar el servidor..."; Sleep 1
    # Ejecuta el script principal de SocketeDeck
    uv run SocketeDeck.py
} else {
    Write-Host "Usando pyenv para iniciar el servidor..."; Sleep 1
    # Carga el entorno virtual de Python
    .\.venv\Scripts\activate
    # Ejecuta el script principal de SocketeDeck
    py SocketeDeck.py
}
