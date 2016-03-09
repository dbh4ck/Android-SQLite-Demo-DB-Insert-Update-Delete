package com.blogspot.dbh4ck.sqlite_demo_db;

/**
 * Created by DB on 08-03-2016.
 */

public class Contact {
    // private variables
    public int _id;
    public String _name;
    public String _email;
   // public byte[] _image;

    public Contact() {
    }

    // constructor
    public Contact(int id, String name, String _email) {
        this._id = id;
        this._name = name;
        this._email = _email;
       // this._image = image;

    }


    public Contact(String valid_name, String valid_email) {
        this._name = valid_name;
        this._email = valid_email;
       // this._image = theImage;
    }


    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting email
    public String getEmail() {
        return this._email;
    }

    // setting email
    public void setEmail(String email) {
        this._email = email;
    }

    // getting Image
    public void getImage() {
       // return this._image;
        return;
    }

    // setting Image
    public void setImage(byte[] image) {
        //  this._image = image;
        return;
    }
}
