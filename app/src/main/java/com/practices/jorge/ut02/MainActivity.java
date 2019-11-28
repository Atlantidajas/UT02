package com.practices.jorge.ut02;

import android.os.Bundle;

import android.view.View;
import android.view.MenuItem;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView listViewUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Referencia a las vistas.
        getVistas();
    }

    private void getVistas() {

        listViewUsers = (ListView) findViewById(R.id.listViewUsers);

        // Datos para el adaptador de la lista.
        String[] users = getResources().getStringArray(R.array.alumnos);

        // Creo el adaptador que usará dichos datos y un layout estándar.
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listViewUsers.setAdapter(adaptador);

        // Creo el listener para cuando se hace click en un item de la lista.
        listViewUsers.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View viewRow,
                                    int posicion, long id) {
                // Muestro mensaje de que ha pulsado sobre usuario.
                mostrarTostada(getString(R.string.messageClickItem) + lst.getItemAtPosition( posicion ));
            }
        });
        // Registro el ListView para que tenga menú contextual.
        registerForContextMenu(listViewUsers);
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo menuInfo ) {

        // Si se ha hecho LongClick sobre la lista.
        if (v.getId() == R.id.listViewUsers) {
            // Obtengo la posición de la lista que se ha pulsado
            int position = ((AdapterContextMenuInfo) menuInfo).position;

            // Inflo el menú.
            this.getMenuInflater().inflate(R.menu.menu_main, menu);

            // Cambio el título de los menús para incluir el nombre del usuario.
            menu.findItem(R.id.itemMenuEditar).setTitle(getString( R.string.editButton ) + listViewUsers.getItemAtPosition(position));
            menu.findItem(R.id.itemMenuDelete).setTitle(getString(R.string.eliminar) + listViewUsers.getItemAtPosition(position));

            // Establezco el título que se muestra en el encabezado del menú.
            menu.setHeaderTitle(R.string.elija_una_opcion);
        }
        // Llamo al OnCreateContextMenu del padre por si quiere
        // añadir algún elemento.
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Posición lista pulsado
        int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;

        // Información al usuario sobre menú pulsado.
        switch (item.getItemId()) {
            
            case R.id.itemMenuEditar:
                mostrarTostada(getString(R.string.editButton) +
                        listViewUsers.getItemAtPosition(position));
                break;

            case R.id.itemMenuDelete:
                mostrarTostada(getString(R.string.eliminar) +
                        listViewUsers.getItemAtPosition(position));
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    // Muestra una tostada.
    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

}
