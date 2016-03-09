package com.blogspot.dbh4ck.sqlite_demo_db;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private Boolean exit = false;
    Button add_btn;
    ListView Contact_listview;
    ArrayList<Contact> contact_data = new ArrayList<Contact>();
    Contact_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Contact_listview = (ListView) findViewById(R.id.list);
            Contact_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);

            Set_Referash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }

        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent add_user = new Intent(MainActivity.this, Add_Update_User.class);
                add_user.putExtra("called", "add");
                add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(add_user);
                finish();
            }
        });
    }


    public void Set_Referash_Data() {
        contact_data.clear();
        db = new DatabaseHandler(this);
        ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

        for (int i = 0; i < contact_array_from_db.size(); i++) {

            int tidno = contact_array_from_db.get(i).getID();
            String name = contact_array_from_db.get(i).getName();
            String email = contact_array_from_db.get(i).getEmail();
          //  byte[] image = contact_array_from_db.get(i).getImage();

            Contact cnt = new Contact();
            cnt.setID(tidno);
            cnt.setName(name);
            cnt.setEmail(email);
         //   cnt.setImage(image);

            contact_data.add(cnt);
        }
        db.close();
        cAdapter = new Contact_Adapter(MainActivity.this, R.layout.listview_row, contact_data);
        Contact_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Set_Referash_Data();

    }


    @Override
    public void onBackPressed()
    {
        //   super.onBackPressed();
        if(exit){
            finish();
        }
        else{
            Toast.makeText(this, "Press Back Again To Exit" , Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent dbdito = new Intent(Intent.ACTION_MAIN);
                    dbdito.addCategory(Intent.CATEGORY_HOME);
                    dbdito.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dbdito);
                }
            }, 1000);
        }
    }


    public class Contact_Adapter extends ArrayAdapter<Contact> {

        Context context;
        int layoutResourceId;
        Contact user;
        ArrayList<Contact> data = new ArrayList<Contact>();

        public Contact_Adapter(Context context, int layoutResourceId, ArrayList<Contact> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.name = (TextView) row.findViewById(R.id.user_name_txt);
                holder.email = (TextView) row.findViewById(R.id.user_email_txt);

                holder.edit = (Button) row.findViewById(R.id.btn_update);
                holder.delete = (Button) row.findViewById(R.id.btn_delete);

                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            user = data.get(position);
            holder.edit.setTag(user.getID());
            holder.delete.setTag(user.getID());
            holder.name.setText(user.getName());
            holder.email.setText(user.getEmail());

            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_user = new Intent(context, Add_Update_User.class);
                    update_user.putExtra("called", "update");
                    update_user.putExtra("USER_ID", v.getTag().toString());
                    context.startActivity(update_user);

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub

                    // show a message while loader is loading

                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // MyDataObject.remove(positionToRemove);
                            DatabaseHandler dBHandler = new DatabaseHandler(context.getApplicationContext());
                            dBHandler.Delete_Contact(user_id);

                            Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();
                            //  MainActivity
                            MainActivity.this.onResume();
                        }

                    });
                    adb.show();
                }

            });
            return row;

        }

        class UserHolder {
            TextView name;
            TextView email;
            Button edit;
            Button delete;
        }

    }


}
