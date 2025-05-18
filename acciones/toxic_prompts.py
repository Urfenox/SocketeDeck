# SocketeDeck solo necesita las funciones:
#   - texto_acciones()
#   - accion()
# Si estas existen, todo irá bien. Lo demás lo organizas tú.
# A continuación, este es uno de los ejemplos.
#
# Overwatch Toxic Prompts | Version 1.0 | Urfenox
#
import pyautogui # pip install pyautogui

fina_seleccion = [
    "GG .i. EZ",
    "ez bots",
    "K .i. Y .i. S",

    "stay mad",
    "not even close",
    "keep trying",

    "facil tutorial",
    "zZzZzZzZz",
    "stfu",

    "casi me despierto",
    "u crying?",
    "got tilted? :)",

    "XDDDDDD",
    "dayum",
    "gg wp",
]

def textos_acciones()-> list:
    return fina_seleccion

def accion(id: int)-> str:
    elemento = fina_seleccion[id]
    pyautogui.press('enter')
    pyautogui.typewrite(elemento, interval=0.01)
    pyautogui.press('enter')
    return elemento
