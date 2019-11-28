package com.practices.jorge.ut02;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.view.MenuItem;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.practices.jorge.ut02.controllers.UserAdapter;
import com.practices.jorge.ut02.models.Users;

public class MainActivity extends Activity {

    public ListView listViewUsers;
    public Users users = new Users();
    public UserAdapter adapter;
    public FloatingActionButton insertButtonUser;
    public AlertDialog.Builder windowsAlertInsertUsert;
    public AlertDialog.Builder getAlertDialogUpdateUser;
    public EditText nameInsertEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia a las vistas.
        getVistas();
    }

    private void getVistas() {

        this.insertButtonUser = findViewById(R.id.insertButtonUser);
        this.listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        this.adapter = new UserAdapter(this, users.getUsers());
        this.listViewUsers.setAdapter( this.adapter );


        onclickButtonInsert();

        // Creo el adaptador que usará dichos datos y un layout estándar.
        listViewUsers.setAdapter( this.adapter );


        onclickItemList();

        // Registro el ListView para que tenga menú contextual.
        registerForContextMenu( listViewUsers );
    }


    public void onclickButtonInsert(){
        this.insertButtonUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.messageRegistreUser), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getAlertDialogInsertUser();

            }
        });
    }

    public void onclickItemList(){
        // Creo el listener para cuando se hace click en un item de la lista.
        listViewUsers.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View viewRow,
                                    int posicion, long id) {
                // Muestro mensaje de que ha pulsado sobre usuario.
                messageToast(getString(R.string.messageClickItem) + lst.getItemAtPosition( posicion ));
            }
        });
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo menuInfo ) {

        // Si se ha hecho LongClick sobre la lista.
        if ( v.getId() == R.id.listViewUsers ) {
            // Obtengo la posición de la lista que se ha pulsado
            int position = ((AdapterContextMenuInfo) menuInfo).position;

            // Inflo el menú.
            this.getMenuInflater().inflate(R.menu.menu_main, menu);

            // Cambio el título de los menús para incluir el nombre del usuario.
            menu.findItem(R.id.itemMenuEditar).setTitle(getString( R.string.editButton  ) + this.users.getUsers().get( position).getName());
            menu.findItem(R.id.itemMenuDelete).setTitle(getString(R.string.eliminar) + this.users.getUsers().get( position).getName());

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
                messageToast(getString(R.string.editButton) + this.users.getUsers().get( position).getName());
                getAlertDialogUpdateUser( position );

                break;

            case R.id.itemMenuDelete:
                messageToast(getString(R.string.eliminar) + this.users.getUsers().get( position).getName());
                         this.users.deteteUser( position );
                         this.adapter.notifyDataSetChanged();

                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public void getAlertDialogInsertUser() {

        this.windowsAlertInsertUsert = new AlertDialog.Builder(this);
        this.windowsAlertInsertUsert.setTitle(R.string.titleWindowsAlertInsertUser);
        this.windowsAlertInsertUsert.setMessage(R.string.messageTitleWindowsAlertInsertUser);
        this.nameInsertEditText = new EditText(this);
        this.windowsAlertInsertUsert.setView(nameInsertEditText);
        this.windowsAlertInsertUsert.setCancelable(false);
        this.windowsAlertInsertUsert.setPositiveButton( R.string.buttonPositiveWindowsAlertInsertUser, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                String nameInsert = nameInsertEditText.getText().toString().trim();

                if ((nameInsert.length() != 0)) {
                    users.setUser( nameInsert );
                    messageToast( getString( R.string.messageUserInsertOK ) );
                } else {
                    messageToast(getString( R.string.messageTextNoInsertInsertWindowsAlertInsertUser ) );
                }
            }
        })
                .setNegativeButton(R.string.buttonCancelWindowsAlertInsertUser, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });
        AlertDialog alert = this.windowsAlertInsertUsert.create();
        alert.show();
    }

    public void getAlertDialogUpdateUser(final int position ) {

        this.getAlertDialogUpdateUser = new AlertDialog.Builder(this);
        this.getAlertDialogUpdateUser.setTitle(R.string.titleWindowsAlertInsertUser);
        this.getAlertDialogUpdateUser.setMessage(R.string.messageTitleWindowsAlertInsertUser);
        this.nameInsertEditText = new EditText(this);
        this.getAlertDialogUpdateUser.setView(nameInsertEditText);
        this.getAlertDialogUpdateUser.setCancelable(false);
        this.getAlertDialogUpdateUser.setPositiveButton( R.string.buttonPositiveWindowsAlertInsertUser, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                String nameInsert = nameInsertEditText.getText().toString().trim();

                if ((nameInsert.length() != 0)) {
                    users.getUsers().remove(position);
                    users.setUser( position, nameInsert );
                    adapter.notifyDataSetChanged();
                    messageToast( getString( R.string.messageUserUpdateOK ) );
                } else {
                    messageToast(getString( R.string.messageUserUpdateOK ) );
                }
            }
        })
                .setNegativeButton(R.string.buttonCancelWindowsAlertInsertUser, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });
        AlertDialog alert = this.getAlertDialogUpdateUser.create();
        alert.show();
    }

    // Muestra una tostada.
    private void messageToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }



}
