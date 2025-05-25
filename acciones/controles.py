# SocketeDeck solo necesita de:
#   - texto_acciones()
#   - accion()
#   - about: dict
# Si estas existen, todo irá bien. Lo demás lo organizas tú.
# A continuación, este es uno de los ejemplos.
#
# Keyboard Assistant | Version 1.0 | Urfenox
#
import pyautogui # pip install pyautogui

botones = [
    "UNDO",
    "REDO",
    "SAVE",
    
    "COPY",
    "PASTE",
    "CUT",
    
    "QUIT",
    "WINDOWS",
    "ESC",
]

def textos_acciones()-> list:
    return botones

def accion(id: int)-> str:
    shortcuts = [
        lambda: pyautogui.hotkey('ctrl', 'z'),
        lambda: pyautogui.hotkey('ctrl', 'y'),
        lambda: pyautogui.hotkey('ctrl', 's'),
        
        lambda: pyautogui.hotkey('ctrl', 'c'),
        lambda: pyautogui.hotkey('ctrl', 'v'),
        lambda: pyautogui.hotkey('ctrl', 'x'),
        
        lambda: pyautogui.hotkey('alt', 'f4'),
        lambda: pyautogui.hotkey('win'),
        lambda: pyautogui.hotkey('esc'),
    ]
    shortcuts[id]()
    return botones[id]

about = {
    "nombre": "Keyboard Assistant",
    "descripcion": "",
    "autor": "Urfenox",
    "version": "1.0"
}
