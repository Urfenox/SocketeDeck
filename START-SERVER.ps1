# Si 'uv' esta disponible, se usa.
if (Test-Path -Path "uv.lock" -PathType Leaf) {
    # Ejecuta el script principal de SocketeDeck
    uv run SocketeDeck.py
} else {
    # Carga el entorno virtual de Python
    .\.venv\Scripts\activate
    # Ejecuta el script principal de SocketeDeck
    py SocketeDeck.py
}