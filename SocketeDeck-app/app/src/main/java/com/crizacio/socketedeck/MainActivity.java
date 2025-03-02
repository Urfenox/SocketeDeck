package com.crizacio.socketedeck;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private GridLayout layout;

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

            // Asignar un OnClickListener al botón
            final int index = i; // Necesitamos capturar el valor de "i" en una variable final para usarlo en el listener
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Imprimir el nombre del botón y el valor correspondiente
                    char letra = (char) ('A' + index - 1); // Calcular la letra de acuerdo al botón presionado
                    // Enviar el mensaje
//                    String data = "btn" + index + ": " + letra;
                    String data = "" + index;
                    sendMessage(data);
                    System.out.println(data);
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

//            // Asignar una imagen única a cada botón
//            String imageName = "ic_button" + i; // Nombre de la imagen basado en el índice
//            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//            if (imageResId != 0) {
//                btn.setCompoundDrawablesWithIntrinsicBounds(
//                        getResources().getDrawable(imageResId),  // Imagen SVG única
//                        null,  // No hay imagen a la izquierda
//                        null,  // No hay imagen a la derecha
//                        null   // No hay imagen abajo
//                );
//            }

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

    // Método para enviar el mensaje al servidor
    private void sendMessage(String message) {
        new SendMessageTask().execute(message);
    }

    // Enviar el mensaje al servidor en segundo plano
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