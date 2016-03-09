package com.blogspot.dbh4ck.sqlite_demo_db;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Add_Update_User extends Activity {
   // Bitmap theImage;
   // ImageView imageDetail;
    EditText add_name, add_email;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
    String valid_email = null, valid_name = null, Toast_msg = null, valid_user_id = "";
    int USER_ID, imageId;

    DatabaseHandler dbHandler = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_screen);

        // set screen
        Set_Add_Update_Screen();

        // set visibility of view as per activity calls
        String called_from = getIntent().getStringExtra("called");

        if (called_from.equalsIgnoreCase("add")) {
            add_view.setVisibility(View.VISIBLE);
            update_view.setVisibility(View.GONE);
        } else {

            update_view.setVisibility(View.VISIBLE);
            add_view.setVisibility(View.GONE);
            USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

            Contact c = dbHandler.Get_Contact(USER_ID);

            add_name.setText(c.getName());
            add_email.setText(c.getEmail());
            // dbHandler.close();
        }

        add_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Is_Valid_Email(add_email);
            }
        });

        add_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Is_Valid_Person_Name(add_name);
            }
        });

       // imageDetail = (ImageView) findViewById(R.id.imageView);
       // Intent intnt = getIntent();
       // theImage = (Bitmap) intnt.getParcelableExtra("imagename");
       // imageId = intnt.getIntExtra("imageid", 20);
       //  Log.d("Image ID:****", String.valueOf(imageId));
       // imageDetail.setImageBitmap(theImage);


        add_save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // check the value state is null or not
                if (valid_name != null && valid_email != null && valid_name.length() != 0 && valid_email.length() != 0) {

                    dbHandler.Add_Contact(new Contact(valid_name, valid_email));
                    Toast_msg = "Data Inserted Successfully";
                    Show_Toast(Toast_msg);
                    Reset_Text();

                }

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                valid_name = add_name.getText().toString();
                valid_email = add_email.getText().toString();

                // check the value state is null or not
                if (valid_name != null && valid_email != null && valid_name.length() != 0 && valid_email.length() != 0) {

                    dbHandler.Update_Contact(new Contact(USER_ID, valid_name, valid_email));
                    dbHandler.close();
                    Toast_msg = "Data Updated Successfully";
                    Show_Toast(Toast_msg);
                    Reset_Text();
                } else {
                    Toast_msg = "Sorry some fields are Missing.\nPlease fill up them.";
                    Show_Toast(Toast_msg);
                }

            }
        });

        update_view_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //  After Making necesssary updates we Return to MainActivity
                Intent view_user = new Intent(Add_Update_User.this, MainActivity.class);
                view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
                finish();
            }
        });

        add_view_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent view_user = new Intent(Add_Update_User.this, MainActivity.class);
                view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
                finish();
            }
        });

    }

    public void Set_Add_Update_Screen() {

        add_name = (EditText) findViewById(R.id.add_name);
        add_email = (EditText) findViewById(R.id.add_email);

        add_save_btn = (Button) findViewById(R.id.add_save_btn);
        update_btn = (Button) findViewById(R.id.update_btn);
        add_view_all = (Button) findViewById(R.id.add_view_all);
        update_view_all = (Button) findViewById(R.id.update_view_all);

        add_view = (LinearLayout) findViewById(R.id.add_view);
        update_view = (LinearLayout) findViewById(R.id.update_view);

        add_view.setVisibility(View.GONE);
        update_view.setVisibility(View.GONE);

    }


    public void Is_Valid_Email(EditText edt) {
        if (edt.getText().toString() == null) {
            edt.setError("Invalid Email Address");
            valid_email = null;
        } else if (isEmailValid(edt.getText().toString()) == false) {
            edt.setError("Invalid Email Address");
            valid_email = null;
        } else {
            valid_email = edt.getText().toString();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    } // end of email matcher

    public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            edt.setError("Accept Alphabets Only.");
            valid_name = null;
        } else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
            edt.setError("Only Alphabets Allowed!");
            valid_name = null;
        } else {
            valid_name = edt.getText().toString();
        }

    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {
        add_name.getText().clear();
        add_email.getText().clear();
    }

}
