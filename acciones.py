import keyboard # pip install keyboard
import pyautogui # pip install pyautogui
import os, sys, time, json

def accion(id: int):
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
    pyautogui.press('enter')
    pyautogui.typewrite(fina_seleccion[id], interval=0.01)
    pyautogui.press('enter')

# def accion(id: int):
#     shortcuts = [
#         lambda: os.system("obs-cli record start"),
#         lambda: os.system("obs-cli record stop"),
#         lambda: os.system("obs-cli hotkey trigger \"OBSBasic.PauseRecording\""),
#         lambda: os.system("obs-cli hotkey trigger \"OBSBasic.UnpauseRecording\""),
#         lambda: os.system("obs-cli stream start"),
#         lambda: os.system("obs-cli stream stop"),
#         lambda: os.system("obs-cli stream toggle"),
#     ]
#     shortcuts[id]()
