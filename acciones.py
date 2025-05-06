import keyboard # pip install keyboard
import pyautogui # pip install pyautogui
import os, sys, time, json


# def accion(id: int):
#     try:
#         shortcuts = [
#             lambda: pyautogui.hotkey('ctrl', 'z'),
#             lambda: pyautogui.hotkey('ctrl', 'y'),
#             lambda: pyautogui.hotkey('ctrl', 'c'),
#             lambda: pyautogui.hotkey('ctrl', 'v'),
#             lambda: pyautogui.hotkey('ctrl', 'esc'),
#             lambda: pyautogui.hotkey('alt', 'f4'),
#         ]
#         shortcuts[id-1]()
#     except Exception as ex:
#         print(ex)


def accion(id: int):
    try:
        fina_seleccion = [
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
        pyautogui.press('enter')
        pyautogui.typewrite(fina_seleccion[id-1], interval=0.01)
        pyautogui.press('enter')
    except Exception as ex:
        print(ex)


# def accion(id: int):
#     try:
#         shortcuts = [
#             lambda: os.system("obs-cli record start"),
#             lambda: os.system("obs-cli record stop"),
#             lambda: os.system("obs-cli record toggle"),

#             lambda: os.system("obs-cli stream start"),
#             lambda: os.system("obs-cli stream stop"),
#             lambda: os.system("obs-cli stream toggle"),
#         ]
#         shortcuts[id-1]()
#     except Exception as ex:
#         print(ex)

# def accion(id: int):
#     try:
#         pass
#     except Exception as ex:
#         print(ex)
