# SocketeDeck solo necesita de:
#   - texto_acciones()
#   - accion()
#   - about: dict
# Si estas existen, todo irá bien. Lo demás lo organizas tú.
# A continuación, este es uno de los ejemplos.
#
# OBS Studio Controller | Version 2.0 | Urfenox
#
import os

textos = [
    "Iniciar grabacion",
    "Detener grabacion",
    "Pausar grabacion",
    "Reanudar grabacion",
    "", "",

    "Iniciar transmision",
    "Detener transmision",
    "Iniciar/Detener transmision",
]

def textos_acciones()-> list:
    return textos

def accion(id: int)-> str:
    # pip install obs-cli
    shortcuts = [
        lambda: os.system("obs-cli record start"),
        lambda: os.system("obs-cli record stop"),
        lambda: os.system("obs-cli hotkey trigger \"OBSBasic.PauseRecording\""),
        lambda: os.system("obs-cli hotkey trigger \"OBSBasic.UnpauseRecording\""),
        lambda: None, lambda: None,
        lambda: os.system("obs-cli stream start"),
        lambda: os.system("obs-cli stream stop"),
        lambda: os.system("obs-cli stream toggle"),
    ]
    shortcuts[id]()
    return textos[id]

about = {
    "nombre": "OBS Studio Controller",
    "descripcion": "",
    "autor": "Urfenox",
    "version": "2.0"
}
