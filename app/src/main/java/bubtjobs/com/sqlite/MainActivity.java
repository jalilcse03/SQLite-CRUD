package bubtjobs.com.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nameEt;
    EditText phoneNoEt;
    private  Contact contact;
    private Contact_Manager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt=(EditText)findViewById(R.id.name);
        phoneNoEt=(EditText)findViewById(R.id.phone);

        manager=new Contact_Manager(this);
    }

    public void save(View view){
        String name=nameEt.getText().toString();
        String phoneno=phoneNoEt.getText().toString();
        contact=new Contact(name,phoneno);

        boolean inserted=manager.addContact(contact);

        if (inserted)
            Toast.makeText(getApplicationContext(),"Inserted Successful",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),"Inserted Failed",Toast.LENGTH_LONG).show();
    }

    //get id
    public void retrive(View view){
        contact=manager.getContact(1);

        Toast.makeText(getApplicationContext(),"Id: "+contact.getId()+"Name: "+contact.getName()+"PhoneNo: "+contact.getPhoneNo(),Toast.LENGTH_LONG).show();

    }

    // get all data

    public void getAll(View view) {
        ArrayList<Contact> contactList = manager.getAllContacts();

        if (contactList != null)
        {
            for (Contact obj:contactList)
            {
                Toast.makeText(getApplicationContext(),"Id: "+obj.getId()+"Name: "+obj.getName()+"Phone: "+obj.getPhoneNo(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
