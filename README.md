# SocketeDeck

Con Python y una aplicación Android puedes tener una especie StreamDeck gratuito y funcional. Personalizable 100%*.  

> _\* Requiere de conocimientos en programación._  

## Why?

Necesitaba controlar OBS Studio y poder escribir rápidamente en el chat de texto de Overwatch. Resultó en un proyecto interesante, con una utilidad perfecta y un potencial que no puedo imaginar.  

## Funcionamiento

Al iniciar `SocketeDeck.py`, se creará un servidor TCP/IP en el puerto `16100`. La aplicación móvil debe ser configurada para conectarse a la IP y al puerto del servidor.  

Una vez la aplicación conectada, el servidor enviará la configuración inicial, la cual incluye el preset activo por defecto declarado en `config.json` y también se incluyen todos los presets disponibles en la carpeta `acciones/`. En nuestro caso, `toxic_prompts` es el preset por defecto.  

Dentro de la carpeta `acciones/` se encuentran dos módulos de ejemplo:  

 1. `toxic_prompts.py`: Ayuda a escribir en el chat de texto de Overwatch mensajes de un jugador tóxico.  
 2. `obs.py`: Permite controlar una instancia de OBS Studio mediante el uso de sockets con `obs-cli` para Python.  

Puedes programar tu propio módulo para que haga las cosas que tú quieras. Eso sí, en cada módulo deben existir las funciones:  

 - `textos_acciones()-> list`: Debe devolver los textos a renderizar en la aplicación móvil.  
 - `accion(id: int)-> str`: Es la función que procesa la acción según el botón presionado, debe devolver una `string`.  
 - `about: dict`: Contiene información sobre el preset de acciones.  

En la aplicación móvil puedes seleccionar qué preset activar con el botón Acciones.  

## Setup

[Descarga](https://github.com/Urfenox/SocketeDeck/archive/refs/heads/master.zip) o clona este repositorio en tu máquina, luego extrae el contenido comprimido en una carpeta.  

Dentro de la carpeta en donde está el repositorio, inicia una instancia de `bash` (Linux) o `cmd` (Windows).  

Crea un entorno virtual de Python  
```sh
py -m venv .venv
```

Activa el entorno virtual de Python  
```sh
source .venv/bin/activate
```
En Windows  
```sh
.venv/Scripts/activate
```

Finalmente, inicia el servidor  
```sh
py SocketeDeck.py
```

## Roadmap

Aunque el proyecto para mí está terminado, ya que cumple la función por la que fue desarrollado, me gustaría agregarle algunas otras cosas.  

 - Iconos _*v1_: Set de iconos preestablecidos (seguramente de Bootstrap o Font Awesome) que se puedan ver en la app móvil.*

> - ~~Padding de botones: Comandos vacíos para saltar botones en la app móvil.~~  
> - ~~Información de acciones: Darle un nombre y descripción, junto con el autor y versión de una acción.~~  
