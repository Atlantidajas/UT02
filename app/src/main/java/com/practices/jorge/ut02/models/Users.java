package com.practices.jorge.ut02.models;

import java.util.ArrayList;

public class Users {

    ArrayList<User> users = new ArrayList<>();

    public void setUser( String name ) {
        users.add( new User( name) );
    }

    public void setUser( int id, String name ) {
        users.add( new User( id, name ) );
    }

    public int getId( int id ){
        return this.users.get( id ).getId();
    }

    public String getName( int id ){
        return this.users.get( id ).getName();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> deteteUser(int id ){
        this.getUsers().remove(id);
        return this.getUsers();
    }

    public boolean searchUserByID( int id ){
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for ( int i = 0; i < this.getUsers().size(); i++){
            ids.add( this.getUsers().get( i ).getId() );
        }
        return ids.contains( id );
    }


}
