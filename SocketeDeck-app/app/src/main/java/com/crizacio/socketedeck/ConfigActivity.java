package com.crizacio.socketedeck;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    EditText edtButtonCount, edtButtonRows, edtButtonColumns, edtButtonTextSize, edtServerIp, edtServerPort;
    Button btnAplicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        edtButtonCount = findViewById(R.id.edtButtonCount);
        edtButtonRows = findViewById(R.id.edtButtonRows);
        edtButtonColumns = findViewById(R.id.edtButtonColumns);
        edtButtonTextSize = findViewById(R.id.edtButtonTextSize);
        edtServerIp = findViewById(R.id.edtServerIp);
        edtServerPort = findViewById(R.id.edtServerPort);
        btnAplicar = findViewById(R.id.btnAplicar); btnAplicar.setOnClickListener(mAplicar);

        sharedPref = this.getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        edtButtonCount.setText(String.valueOf(sharedPref.getInt(MainActivity.PREF_NAME_BUTTON_COUNT, 12)));
        edtButtonRows.setText(String.valueOf(sharedPref.getInt(MainActivity.PREF_NAME_BUTTON_ROWS, 4)));
        edtButtonColumns.setText(String.valueOf(sharedPref.getInt(MainActivity.PREF_NAME_BUTTON_COLUMNS, 3)));
        edtButtonTextSize.setText(String.valueOf(sharedPref.getFloat(MainActivity.PREF_NAME_BUTTON_TEXT_SIZE, 15)));
        edtServerIp.setText(sharedPref.getString(MainActivity.PREF_NAME_SERVER_IP, "192.168.8.175"));
        edtServerPort.setText(String.valueOf(sharedPref.getInt(MainActivity.PREF_NAME_SERVER_PORT, 16100)));
    }

    android.view.View.OnClickListener mAplicar = new View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            editor.putInt(MainActivity.PREF_NAME_BUTTON_COUNT, Integer.parseInt(edtButtonCount.getText().toString().trim()));
            editor.putInt(MainActivity.PREF_NAME_BUTTON_ROWS, Integer.parseInt(edtButtonRows.getText().toString().trim()));
            editor.putInt(MainActivity.PREF_NAME_BUTTON_COLUMNS, Integer.parseInt(edtButtonColumns.getText().toString().trim()));
            editor.putFloat(MainActivity.PREF_NAME_BUTTON_TEXT_SIZE, Float.parseFloat(edtButtonTextSize.getText().toString().trim()));
            editor.putString(MainActivity.PREF_NAME_SERVER_IP, edtServerIp.getText().toString().trim());
            editor.putInt(MainActivity.PREF_NAME_SERVER_PORT, Integer.parseInt(edtServerPort.getText().toString().trim()));
            editor.apply(); editor.commit();
        }
    };
}