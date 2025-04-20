package com.crizacio.socketedeck;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import org.json.JSONArray;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout layout;
    private List<Button> botones = new ArrayList<>(); // Lista para guardar las referencias a los botones

    private static final String SERVER_IP = "192.168.8.175";  // Cambia esta IP a la del PC
    private static final int SERVER_PORT = 16100;
    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ejecutar la tarea asincrónica para conectarse al servidor
        new ConnectTask().execute();

        // Crear el contenedor (GridLayout) con 3 columnas
        layout = new GridLayout(this);
        layout.setRowCount(4);
        layout.setColumnCount(3);
        layout.setId(View.generateViewId()); // Genera un ID único para el contenedor

        // Configurar el GridLayout para que ocupe toda la pantalla
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Definir el número de botones a crear
//        int numBotones = 6;
//        int numBotones = 9;
        int numBotones = 12;
//        int numBotones = 24;

        // Crear los botones dinámicamente
        for (int i = 1; i <= numBotones; i++) {
            Button btn = new Button(this);
            btn.setText("Botón " + i);
            btn.setId(View.generateViewId());
            // Guardamos el botón en la lista para poder acceder a él más tarde
            botones.add(btn);

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

        // Establecer el layout de la actividad
        setContentView(layout);
    }



    // Conectar al servidor y mantener la conexión abierta
    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                outputStream = socket.getOutputStream();
                inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Leer el mensaje JSON del servidor
                String jsonResponse = receiveMessage();
                if (jsonResponse != null) {
                    // Procesar el mensaje JSON
                    processJsonResponse(jsonResponse);
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
                Toast.makeText(MainActivity.this, "Conexión establecida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error al conectar al servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*
    * Recibe mensajes de forma asincronica desde el servidor.
    * */
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

    private void processJsonResponse(String jsonResponse) {
        try {
            // Convertir la cadena JSON en un JSONArray
            JSONArray jsonArray = new JSONArray(jsonResponse);

            // Asignar el texto de los botones según la posición de cada item en el array
            for (int i = 0; i < jsonArray.length(); i++) {
                if (i < botones.size()) {
                    String texto = jsonArray.getString(i);
                    if (texto.isEmpty()) { // si no hay nada. mantenemos el texto actual
                        continue;
                    }
                    // Actualizar el texto de los botones
                    System.out.println("btn" + i + ": " + texto);
                    botones.get(i).setText(texto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Para enviar mensajes al servidor.
    * */
    private void sendMessage(String message) {
        new SendMessageTask().execute(message);
    }
    /*
    * Este metodo envia el mensaje de forma asincronica al servidor.
    * */
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

    @Override
    public void onPause() {
        super.onPause();
        // Cerrar la conexión cuando la aplicación pase a segundo plano
        try {
            if (socket != null && !socket.isClosed()) {
                sendMessage("!D");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        // Reabrir la conexion cuando la aplicacion entra a primer plano
        new ConnectTask().execute();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la conexión cuando la aplicación se cierre
        try {
            if (socket != null && !socket.isClosed()) {
                sendMessage("!D");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}