# SocketeDeck solo necesita de:
#   - texto_acciones()
#   - accion()
#   - about: dict
# Si estas existen, todo irá bien. Lo demás lo organizas tú.
# A continuación, este es uno de los ejemplos.
#
# Overwatch Chat | Version 1.0 | Urfenox
#
import pyautogui # pip install pyautogui
import pyperclip # pip install pyperclip

fina_seleccion = [
    ("ROJO", "<FGFF0000FF>"),
    ("AZUL", "<FG0000FFFF>"),
    ("VERDE", "<FG1BFF00FF>"),
    
    ("OW Coin", "<TXC00000000008E02>"),
    ("Mythic Coin", "<TXC0000000004C5D2>"),
    ("Pachimari", "<TXC0000000002C012>"),
    ("Elimination", "<TXC00000000021569>"),
    ("XP", "<TXC0000000003A50D>"),
    ("Quickplay", "<TXC00000000017C9E>"),
    ("Competitive", "<TXC00000000017C9C>"),
    ("Lag", "<TXC0000000000894F>"),
    ("ggs", "<FG1BFF00FF>ggs <TXC0000000002C012>"),
]

def textos_acciones()-> list:
    return [item[0] for item in fina_seleccion]

def accion(id: int)-> str:
    elemento = fina_seleccion[id][1]
    pyperclip.copy(elemento)
    pyautogui.hotkey("ctrl", "v")
    return elemento

about = {
    "nombre": "Overwatch Chat",
    "descripcion": "Add colors and emojis to chat",
    "autor": "Urfenox",
    "version": "1.0",
    "configuracion": {
        "buttons": len(fina_seleccion),
        "columnas": 3,
        "filas": 4,
    }
}
