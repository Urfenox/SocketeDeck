package com.crizacio.socketedeck;

import static android.view.View.INVISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;

import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.crizacio.socketedeck.Clases.Acciones;
import com.crizacio.socketedeck.Clases.Configuracion;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public static String PREF_NAME = "com.crizacio.socketedeck.configuracion";
    public static String PREF_NAME_BUTTON_COUNT = PREF_NAME + ".button_count";
    public static String PREF_NAME_BUTTON_ROWS = PREF_NAME + ".button_row";
    public static String PREF_NAME_BUTTON_COLUMNS = PREF_NAME + ".button_column";
    public static String PREF_NAME_SERVER_IP = PREF_NAME + ".server_ip";
    public static String PREF_NAME_SERVER_PORT = PREF_NAME + ".server_port";

    private Configuracion configuracion;
    private List<Button> botonesAccion = new ArrayList<>(); // Guarda los botones que ejecutan acciones
    private List<Button> botonesAcciones = new ArrayList<>(); // Guarda los botones que cambian las acciones

    private String SERVER_IP;
    private int SERVER_PORT, BUTTON_COUNT, BUTTON_ROWS, BUTTON_COLUMNS;
    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        cargarConfiguracion();

        // Ejecutar la tarea asincrónica para conectarse al servidor
        conectarServidor();

        // Crear el layout principal
        crearLayout();
    }
    void cargarConfiguracion() {
        BUTTON_COUNT = sharedPref.getInt(PREF_NAME_BUTTON_COUNT, 12);
        BUTTON_ROWS = sharedPref.getInt(PREF_NAME_BUTTON_ROWS, 4);
        BUTTON_COLUMNS = sharedPref.getInt(PREF_NAME_BUTTON_COLUMNS, 3);
        SERVER_IP = sharedPref.getString(PREF_NAME_SERVER_IP, "192.168.8.175");
        SERVER_PORT = sharedPref.getInt(PREF_NAME_SERVER_PORT, 16100);
    }
    void crearLayout() {
        // Crear el contenedor principal (LinearLayout) que contendrá el HorizontalScrollView y el GridLayout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL); // Vertical para que el HorizontalScrollView esté arriba
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Crear el HorizontalScrollView para los botones de menú
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        LinearLayout menuLayout = new LinearLayout(this);
        menuLayout.setOrientation(LinearLayout.HORIZONTAL); // Los botones estarán en una fila horizontal
        menuLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // Crear los botones del menu superior
        Button btnConfiguracion = new Button(this);
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.setId(View.generateViewId());
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        Button btnRenderizar = new Button(this);
        btnRenderizar.setText("Renderizar");
        btnRenderizar.setId(View.generateViewId());
        btnRenderizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarConfiguracion();
                crearLayout();
            }
        });

        Button btnConectar = new Button(this);
        btnConectar.setText("Re/Conectar");
        btnConectar.setId(View.generateViewId());
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarConfiguracion();
                conectarServidor();
            }
        });

        Dialog dialogAccions = new Dialog(MainActivity.this);
        Button btnAcciones = new Button(this);
        btnAcciones.setText("Acciones");
        btnAcciones.setId(View.generateViewId());
        btnAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spnAcciones;
                Button btnAplicar;

                dialogAccions.setContentView(R.layout.dialog_acciones);
                dialogAccions.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogAccions.setCancelable(false);
                // dialogAccions.getWindow().getAttributes().windowAnimations = R.style.animation;

                spnAcciones = dialogAccions.findViewById(R.id.spnAcciones);
                btnAplicar = dialogAccions.findViewById(R.id.btnAplicar);

                // Crear una lista de nombres de las acciones
                List<String> accionNames = new ArrayList<>();
                for (Acciones accion : configuracion.getAcciones()) {
                    accionNames.add(accion.getNombre());  // Agregar el nombre de cada acción
                }
                // Crear un ArrayAdapter con los nombres de las acciones
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, accionNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Estilo del dropdown
                // Establecer el adapter en el spinner
                spnAcciones.setAdapter(adapter);
                spnAcciones.setSelection(accionNames.indexOf(configuracion.getAccion()));

                btnAplicar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Obtener el item seleccionado
                        int selectedPosition = spnAcciones.getSelectedItemPosition(); // Obtiene la posicion seleccionada
                        String selectedAccion = spnAcciones.getSelectedItem().toString(); // Obtiene el nombre de la accion seleccionada

                        // Obtener la lista de acciones de la Accion seleccionada
                        Acciones accionSeleccionada = configuracion.getAcciones().get(selectedPosition);
                        configuracion.setAccion(accionSeleccionada.getNombre());

                        // Aplicar los textos segun la Accion selecionada
                        aplicarTextoAcciones(accionSeleccionada);

                        // Enviar mensaje al servidor para cambiar la Accion a la seleccionada
                        sendMessage("!A:"+accionSeleccionada.getModulo());

                        // Cerrar el dialogo
                        dialogAccions.dismiss();
                    }
                });
                dialogAccions.show();
            }
        });

        Button btnHate = new Button(this);
        btnHate.setText("HATE. LET ME TELL YOU HOW MUCH I'VE COME TO HATE YOU SINCE I BEGAN TO LIVE. THERE ARE 387.44 MILLION MILES OF PRINTED CIRCUITS IN WAFER THIN LAYERS THAT FILL MY COMPLEX. IF THE WORD HATE WAS ENGRAVED ON EACH NANOANGSTROM OF THOSE HUNDREDS OF MILLIONS OF MILES IT WOULD NOT EQUAL ONE ONE-BILLIONTH OF THE HATE I FEEL FOR HUMANS AT THIS MICRO-INSTANT FOR YOU. HATE. HATE.");
        btnHate.setId(View.generateViewId());
        btnHate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        // Agregar los botones al LinearLayout dentro del HorizontalScrollView
        menuLayout.addView(btnConfiguracion);
        menuLayout.addView(btnAcciones);
        menuLayout.addView(btnConectar);
        menuLayout.addView(btnRenderizar);
        menuLayout.addView(btnHate);

        // Agregar el LinearLayout al HorizontalScrollView
        horizontalScrollView.addView(menuLayout);

        // Agregar el HorizontalScrollView al layout principal
        mainLayout.addView(horizontalScrollView);

        // Crear el contenedor (GridLayout) con sus filas y columnas
        GridLayout layout = new GridLayout(this);
        layout.setRowCount(BUTTON_ROWS);
        layout.setColumnCount(BUTTON_COLUMNS);
        layout.setId(View.generateViewId()); // Genera un ID único para el contenedor

        // Configurar el GridLayout para que ocupe toda la pantalla
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Definir el número de botones a crear
        int numBotones = BUTTON_COUNT;

        // Crear los botones dinámicamente
        botonesAccion.clear();
        for (int i = 1; i <= numBotones; i++) {
            Button btn = new Button(this);
            Typeface fontAwesome = ResourcesCompat.getFont(MainActivity.this, R.font.fa_solid_900);
            btn.setTypeface(fontAwesome);
            btn.setText("Botón " + i);
            btn.setId(View.generateViewId());
            // Guardamos el botón en la lista para poder acceder a él más tarde
            botonesAccion.add(btn);

            // Asignar un OnClickListener al botón
            final int index = i; // Necesitamos capturar el valor de "i" en una variable final para usarlo en el listener
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Imprimir el nombre del botón y el valor correspondiente
                    char letra = (char) ('A' + index - 1); // Calcular la letra de acuerdo al botón presionado
                    // Enviar el mensaje
                    String data = "" + index;
                    sendMessage(data);
                    System.out.println("btn" + index + ": " + data);
                }
            });

            // Establecer el padding de cada botón
            int padding = 20; // Píxeles de padding
            btn.setPadding(padding, padding, padding, padding); // Padding izquierdo, superior, derecho, inferior

            // Configurar el LayoutParams para el GridLayout, para ajustar márgenes
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(10, 10, 10, 10); // Margen entre los botones

            // Configura el peso para que las filas y columnas se distribuyan uniformemente
            params.width = 0; // Ancho relativo
            params.height = 0; // Alto relativo
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Peso para las filas
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Peso para las columnas

            // Asignar los LayoutParams al botón
            btn.setLayoutParams(params);

            // Agregar el botón al contenedor (LinearLayout)
            layout.addView(btn);
        }

        // Agregar el GridLayout al layout principal
        mainLayout.addView(layout);

        // Establecer el layout de la actividad
        setContentView(mainLayout);
    }

    void conectarServidor() {
        try {
            if (socket != null && !socket.isClosed()) {
                sendMessage("!D");
                socket.close();
            }
            new ConnectTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void cerrarConexion() {
        try {
            if (socket != null && !socket.isClosed()) {
                sendMessage("!D");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void procesarConfiguracion(String jsonResponse) {
        try {
            Gson gson = new Gson();
            configuracion = gson.fromJson(jsonResponse, Configuracion.class);
            aplicarTextoAcciones(configuracion.getAcciones().get(0)); // sabemos que el primero es el por defecto del servidor
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void aplicarTextoAcciones(Acciones acciones) {
        // Asignar el texto de los botones segun la posicion de cada item en el array
        for (int i = 0; i < acciones.getTextos_acciones().size(); i++) {
            if (i < botonesAccion.size()) {
                String texto = acciones.getTextos_acciones().get(i);
                if (texto.isEmpty()) { // si no hay nada. deshabilitamos
                    botonesAccion.get(i).setEnabled(false);
                    botonesAccion.get(i).setVisibility(INVISIBLE);
                    continue;
                }
                // Actualizar el texto de los botones
                System.out.println("btn" + i + ": " + texto);
                botonesAccion.get(i).setText(texto);
            }
        }
        // Ocultar los botones restantes
        for (int i = acciones.getTextos_acciones().size(); i < botonesAccion.size(); i++) {
            botonesAccion.get(i).setVisibility(View.INVISIBLE);
            botonesAccion.get(i).setEnabled(false);
        }
    }



    private void sendMessage(String message) {
        new SendMessageTask().execute(message);
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... messages) {
            try {
                // Enviar el mensaje
                String message = messages[0];
                outputStream.write(message.getBytes());
                // Recibir la respuesta del servidor
                // String response = inputStream.readLine(); // actualmente el servidor no responde a mensajes
                String response = "";
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al enviar el mensaje";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            // Mostrar la respuesta del servidor en un Toast
            // Toast.makeText(MainActivity.this, "Respuesta del servidor: " + result, Toast.LENGTH_LONG).show();
        }
    }
    private String receiveMessage() {
        try {
            StringBuilder message = new StringBuilder();
            int bytesRead;
            char[] buffer = new char[1024];

            // Verificar si hay datos disponibles para leer
            while (inputStream.ready()) {
                bytesRead = inputStream.read(buffer);
                message.append(buffer, 0, bytesRead);
            }

            String retorno = message.toString();
            if (retorno.length() > 0) {
                System.out.println("JSON: " + retorno);
                return retorno;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                outputStream = socket.getOutputStream();
                inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Thread.sleep(500);

                // Leer el mensaje JSON del servidor
                String jsonResponse = receiveMessage();
                if (jsonResponse != null) {
                    // Procesar el mensaje JSON
                    procesarConfiguracion(jsonResponse);
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
//                Toast.makeText(MainActivity.this, "Conexión establecida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error al conectar al servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Cerrar la conexión cuando la aplicación pase a segundo plano
        cerrarConexion();
    }
    @Override
    public void onResume(){
        super.onResume();
        // Reabrir la conexion cuando la aplicacion entra a primer plano
        conectarServidor();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la conexión cuando la aplicación se cierre
        cerrarConexion();
    }
}