import time, json, socket
import threading
import configuracion

HEADER = 1024 # para saber cuantos bytes vamos a aceptar
SERVER = "0.0.0.0" # para red local
PORT = 16100
ADDR = (SERVER, PORT)
FORMAT = "utf-8"
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(ADDR)
print("Escuchando en {}:{}...".format(SERVER, PORT))

contenido = {}
with open('config.json') as f:
    contenido = json.load(f)
accion = configuracion.obtener_modulo(contenido["configuracion"]["acciones"])
config = configuracion.obtener_configuracion(contenido["configuracion"]["acciones"])

def handle_cliente(conn: socket.socket, addr):
    global accion, config
    print(f"[CONN] {addr} conectado!")
    connected = True
    try:
        conn.send(json.dumps(config).encode(FORMAT))
        time.sleep(.5)
        while connected:
            msg = conn.recv(HEADER).decode(FORMAT)
            if msg.startswith("!D") or msg == "":
                connected = False
                break
            if msg.startswith("!A:"):
                nueva_accion = msg.split(":")[1]
                accion = configuracion.obtener_modulo(nueva_accion)
                config = configuracion.obtener_configuracion(nueva_accion)
                print(F"[CONF] {addr} cambio acciones a {nueva_accion}")
                continue
            msg = int(msg) - 1
            try:
                respuesta = accion.accion(msg)
                print(F"[{addr}] {msg} -> {respuesta}")
            except Exception as ex:
                print(F"[ERR] {addr} {ex}")
    except Exception as ex:
        print(F"[ERR] {addr} {ex}")
    print(F"[DISC] {addr} desconectado!")
    conn.close()
def abrir_servidor():
    server.listen() # empezamos a escuchar...
    while True:
        conn, addr = server.accept() # aceptamos la conexion y obtenemos valores conn y addr
        thread = threading.Thread(target=handle_cliente, args=(conn, addr)) # creamos un thread para el cliente
        thread.start()
        print(f"[COUNT] Conexiones {threading.active_count() - 1}")

abrir_servidor()
