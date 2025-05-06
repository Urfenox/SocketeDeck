import json

def send_config():
    configuracion = [
        "gg ez", # 0 -> 1
        "ez bots", # 1 -> 2
        "K Y S", # 2 -> 3

        "stay mad", # 3 -> 4
        "not even close",
        "keep trying",

        "facil tutorial",
        "zZzZzZzZz",
        "stfu",

        "pal lobby perrin",
        "anda a llorar al lobby",
        "got tilted? :)",

        "XDDDDDD",
        "dayum",
        "gg wp",
    ]
    print("[BTNS] Enviando configuracion de botones...")
    return json.dumps(configuracion)
