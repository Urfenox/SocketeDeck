import keyboard # pip install keyboard
import pyautogui # pip install pyautogui
import os, sys, time, json

def accion(id: int):
    try:
        fina_seleccion = [
            "gg ez", # 0 -> 1
            "ez bots", # 1 -> 2
            "K Y S", # 2 -> 3
            "stay mad", # 3 -> 4
            "SO BAD",
            "LMAO BYE",
            "facil tutorial",
            "zZzZzZzZz",
        ]
        pyautogui.press('enter')
        pyautogui.typewrite(fina_seleccion[id-1], interval=0.01)
        pyautogui.press('enter')
    except Exception as ex:
        print(ex)
