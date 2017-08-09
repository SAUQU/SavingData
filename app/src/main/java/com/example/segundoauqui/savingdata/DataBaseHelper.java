package com.example.segundoauqui.savingdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by segundoauqui on 8/9/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase";


    public static final String TABLE_NAME = "Contacts";
    public static final String CONTACT_ID = "ID";
    public static final String CONTACT_NAME = "Name";
    public static final String CONTACT_NUMBER = "Number";
    public static final String CONTACT_EMAIL = "Email";
    public static final String CONTACT_COMPANY = "Company";
    public static final String CONTACT_CITY= "City";
    public static final String CONTACT_PHOTO = "photo";




    private static final String TAG = "savedata";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void delete(String id){

        SQLiteDatabase database = this.getWritableDatabase();

        String DELETE_ELEMENT = "DELETE FROM  " + TABLE_NAME + " WHERE " + CONTACT_ID + " = ? ";
        String [] parame = new String[]{id};



        Log.d(TAG, "Delete: "+DELETE_ELEMENT);

        database.execSQL(DELETE_ELEMENT, parame);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +

                CONTACT_ID + " Integer Primary Key Autoincrement, " +
                CONTACT_NAME + " TEXT, " +
                CONTACT_NUMBER + " TEXT, " +
                CONTACT_EMAIL + " TEXT, " +
                CONTACT_COMPANY + " TEXT, " +
                CONTACT_CITY + " TEXT, " +
                CONTACT_PHOTO + " BLOB "+
                ")";
        Log.d(TAG, "onCreate: "+CREATE_TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.d(TAG, "onUpgrade: ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }




    public void saveNewContact(MyContacs contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME, contact.getEtName());
        contentValues.put(CONTACT_NUMBER, contact.getEtPhoneNumber());
        contentValues.put(CONTACT_EMAIL, contact.getEtEmail());
        contentValues.put(CONTACT_COMPANY, contact.getEtCompany());
        contentValues.put(CONTACT_CITY, contact.getEtCity());
        contentValues.put(CONTACT_PHOTO, contact.getB());
        database.insert(TABLE_NAME,null,contentValues);

        Log.d(TAG, "saveNewContact: " + contact.getEtName() + " " + contact.getEtPhoneNumber() + " " + contact.getEtEmail() +
        " " + contact.getEtCompany() + " " + contact.getEtCity() + " " + contact.getB());
    }




    public ArrayList<MyContacs> getContacs(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        String [] parame = null;


        if(id.equals("-1")) {
            query = "SELECT * FROM " + TABLE_NAME;
        }
        else {
            query = "SELECT * FROM " + TABLE_NAME + " Where " + CONTACT_ID + " = ? " ;
            parame = new String[]{id};
        }
        Log.d(TAG, "getContacs: " + query+ " " + id);
        //Cursor cursor = database.rawQuery(query, new String[]{CONTACT_NAME,"Jose"});
        Cursor cursor = database.rawQuery(query, parame);
        ArrayList<MyContacs> contacts = new ArrayList();
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getContacts: Name:" + cursor.getString(0) + ", Phone: "+ cursor.getString(1));
                contacts.add(new MyContacs(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getBlob(6)));
            }while(cursor.moveToNext());
        }
        else{
            Log.d(TAG, "getContacts: empty");
        }
        return contacts;
    }

}
