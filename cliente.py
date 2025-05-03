import json

def send_config():
    configuracion = [
        'Iniciar',
        'Detener',
        'Toggle',
        '',
        '',
        '',
        '',
        '',
        '',
        'Iniciar',
        'Detener',
        'Toggle',
    ]
    print("[BTNS] Enviando configuracion de botones...")
    return json.dumps(configuracion)
