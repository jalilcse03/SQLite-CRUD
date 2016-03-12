package bubtjobs.com.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Murtuza on 3/13/2016.
 */
public class Contact_Manager {
    private DataBaseHelper helper;
    private Contact contact;
    private SQLiteDatabase database;

    public Contact_Manager(Context context){
        helper=new DataBaseHelper(context);
    }

    private void open(){
        database=helper.getWritableDatabase();
    }

    private void close(){
        helper.close();
    }

    // add
    public boolean addContact(Contact contact){
        this.open();

        ContentValues contentValues=new ContentValues();

        contentValues.put(DataBaseHelper.COL_NAME,contact.getName());
        contentValues.put(DataBaseHelper.COL_PHONENO,contact.getPhoneNo());

        long inserted=database.insert(DataBaseHelper.TABLE_CONTACT,null,contentValues);

        database.close();

        if(inserted>0)
            return true;
        else
        return false;
    }
    // get data
    public Contact getContact(int id){
        this.open();

        // this is sqlite format
       // Cursor cursor=database.query(DataBaseHelper.TABLE_CONTACT, new String[]{DataBaseHelper.COL_ID, DataBaseHelper.COL_NAME, DataBaseHelper.COL_PHONENO}, DataBaseHelper.COL_ID + " = " + id, null, null, null, null);


        // this is normal sql format
        String query="select  * FROM "+DataBaseHelper.TABLE_CONTACT+" WHERE "+DataBaseHelper.COL_ID+" = "+id;

        Cursor cursor=database.rawQuery(query,null);

        cursor.moveToFirst();

        int mId=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
        String name=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_NAME));
        String phoneNo=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PHONENO));

        contact=new Contact(mId,name,phoneNo);
        this.close();
        return contact;
    }

    // get all data
    public ArrayList<Contact> getAllContacts(){
        this.open();
        ArrayList<Contact> contactList=new ArrayList<>();
        // sqlite formate
        //Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACT, null, null, null, null, null, null);
        String query="SELECT * FROM "+DataBaseHelper.TABLE_CONTACT;
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();

        if(cursor!=null && cursor.getCount()>0){
            for(int i=0;i<cursor.getCount();i++)
            {
                int mId=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String name=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_NAME));
                String phoneNo=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PHONENO));

                contact=new Contact(mId,name,phoneNo);
                contactList.add(contact);
                cursor.moveToNext();
            }
        }
        this.close();

        return contactList;
    }

}
