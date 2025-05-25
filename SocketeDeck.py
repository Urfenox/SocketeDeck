import os, time, json
import socket, threading
import configuracion
os.system("cls") if os.name == "nt" else os.system("clear")

SERVER = "0.0.0.0" # para red local
PORT = 16100
ADDR = (SERVER, PORT)

class Cliente:
    HEADER = 1024
    FORMAT = "utf-8"

    def __init__(self, conn: socket.socket, addr):
        self.conn = conn
        self.addr = addr
        self.accion = None
        self.config = None
        self.connected = True

        # Cargar configuracion inicial
        self.cargar_configuracion()

    def cargar_configuracion(self):
        contenido = {}
        with open('config.json') as f:
            contenido = json.load(f)
        self.accion = configuracion.obtener_modulo(contenido["configuracion"]["acciones"])
        self.config = configuracion.obtener_configuracion(contenido["configuracion"]["acciones"])

    def atender_cliente(self):
        print(f"[{self.addr}] conectado!")
        try:
            self.conn.send(json.dumps(self.config).encode(self.FORMAT))
            time.sleep(0.5)
            while self.connected:
                msg = self.conn.recv(self.HEADER).decode(self.FORMAT)
                if msg.startswith("!D") or msg == "":
                    self.connected = False
                    break
                if msg.startswith("!A:"):
                    nueva_accion = msg.split(":")[1]
                    self.accion = configuracion.obtener_modulo(nueva_accion)
                    self.config = configuracion.obtener_configuracion(nueva_accion)
                    print(f"[{self.addr}] cambio modulo a {nueva_accion}")
                    continue
                msg = int(msg) - 1
                try:
                    respuesta = self.accion.accion(msg)
                    print(f"[{self.addr}] {msg} -> {respuesta}")
                except Exception as ex:
                    print(f"[ERROR] {self.addr} {ex}")
        except Exception as ex:
            print(f"[ERROR] {self.addr} {ex}")
        finally:
            print(f"[{self.addr}] desconectado!")
            self.conn.close()

def abrir_servidor():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(ADDR)
    server.listen()
    print("Escuchando en {}:{}...".format(SERVER, PORT))
    while True:
        conn, addr = server.accept()
        cli = Cliente(conn, addr)
        thread = threading.Thread(target=cli.atender_cliente)
        thread.start()
        print(f"[COUNT] Conexiones {threading.active_count() - 1}")

if __name__ == "__main__":
    abrir_servidor()
