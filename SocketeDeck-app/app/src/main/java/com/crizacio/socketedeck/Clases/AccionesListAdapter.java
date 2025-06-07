package com.crizacio.socketedeck.Clases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crizacio.socketedeck.R;

import java.util.List;

public class AccionesListAdapter extends ArrayAdapter<Acciones> {

    private Activity context;
    List<Acciones> acciones;

    public AccionesListAdapter(Activity context, List<Acciones> acciones) {
        super(context, R.layout.lista_doble, acciones);
        this.context = context;
        this.acciones = acciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista_doble, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.txtTitulo);
        TextView textViewSubtitle= (TextView) listViewItem.findViewById(R.id.txtSubtitulo);
        TextView textViewAuthor= (TextView) listViewItem.findViewById(R.id.txtAutor);
        TextView textViewVersion= (TextView) listViewItem.findViewById(R.id.txtVersion);

        Acciones accion = acciones.get(position);

        textViewTitle.setText(accion.getNombre());
        textViewSubtitle.setText(accion.getDescripcion());
        textViewAuthor.setText(accion.getAutor());
        textViewVersion.setText(accion.getVersion());

        return listViewItem;
    }
}