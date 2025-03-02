import os, socket
import threading
import acciones
os.system("cls")

HEADER = 1024 # para saber cuantos bytes vamos a aceptar
# SERVER = socket.gethostbyname(socket.gethostname())
SERVER = "0.0.0.0" # para red local
PORT = 16100
ADDR = (SERVER, PORT)
FORMAT = "utf-8"
DISCONNECT_MESSAGE = "!D"
print("Escuchando en {}:{}...".format(SERVER, PORT))

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(ADDR)

def handle_cliente(conn: socket.socket, addr):
    print(f"[CONN] {addr} conectado!")
    connected = True
    while connected:
        msg = conn.recv(HEADER).decode(FORMAT)
        if msg == DISCONNECT_MESSAGE or msg == "":
            connected = False
            break
        acciones.accion(int(msg))
        print(F"[{addr}] {msg}")
    print(F"[DISC] {addr} desconectado!")
    conn.close()

def iniciar():
    server.listen() # empezamos a escuchar...
    while True:
        conn, addr = server.accept() # aceptamos la conexion y obtenemos valores conn y addr
        thread = threading.Thread(target=handle_cliente, args=(conn, addr)) # creamos un thread para el cliente
        thread.start()
        print(f"[COUNT] Conexiones {threading.active_count() - 1}")

iniciar()
