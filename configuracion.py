from pathlib import Path
import importlib, json

def obtener_acciones()-> list:
    ruta_carpeta = Path('acciones')
    acciones = [archivo.name.replace('.py', '') for archivo in ruta_carpeta.iterdir() if archivo.is_file() and archivo.suffix == '.py' and '__init__' not in archivo.name]
    return acciones

def obtener_default()-> str:
    with open('config.json') as f:
        contenido = json.load(f)
    return str(contenido['configuracion']['acciones'])

def obtener_configuracion(accion_actual: str)-> dict:
    lista_acciones = obtener_acciones()
    acciones_disponibles = []
    # Garantiza que el preset actual este en la primera posicion
    _modulo = importlib.import_module(str('acciones.{}').format(accion_actual))
    _preset = {
        "nombre": _modulo.about['nombre'] or accion_actual,
        "descripcion": _modulo.about['descripcion'] or '',
        "autor": _modulo.about['autor'] or '',
        "version": _modulo.about['version'] or '',
        "modulo": accion_actual,
        "textos_acciones": _modulo.textos_acciones()
    }
    acciones_disponibles.append(_preset)
    for _accion in lista_acciones:
        if not _accion == accion_actual:
            _modulo = importlib.import_module(str('acciones.{}').format(_accion))
            _preset = {
                "nombre": _modulo.about['nombre'] or _accion,
                "descripcion": _modulo.about['descripcion'] or '',
                "autor": _modulo.about['autor'] or '',
                "version": _modulo.about['version'] or '',
                "modulo": _accion,
                "textos_acciones": _modulo.textos_acciones()
            }
            acciones_disponibles.append(_preset)
    return {
        "accion": accion_actual, # el preset accion activa por defecto
        "acciones": acciones_disponibles # toda la info de cada preset accion disponible
    }

def obtener_modulo(acciones: str):
    return importlib.import_module(str('acciones.{}').format(acciones))
