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
    return json.dumps(configuracion)
