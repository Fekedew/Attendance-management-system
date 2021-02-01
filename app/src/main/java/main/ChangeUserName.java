package main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fekedew.R;
import main.database.DatabaseHandler;

public class ChangeUserName extends AppCompatActivity {

    EditText newPass, oldPass, newUserName;
    Button save;
    String newPassSt, newUserNameSt, oldPassSt;
    public DatabaseHandler handler = AppBase.handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_userandpass);


        newUserName = findViewById(R.id.txtName);
        oldPass = findViewById(R.id.txtOldPwd);
        newPass = findViewById(R.id.txtPwd);
        save = findViewById(R.id.btnChange);


        String un = "", pa = "";
        String qu = "SELECT * FROM user WHERE 1";
        Cursor cursor = handler.execQuery(qu);
        if (cursor == null){
            Toast.makeText(this, "cursor is null", Toast.LENGTH_LONG).show();
        }else if (cursor.getCount() == 0){
            Toast.makeText(this, "cursor count is zero", Toast.LENGTH_LONG).show();
        }else {
            cursor.moveToFirst();
            un = cursor.getString(cursor.getColumnIndex("uname"));
            pa = cursor.getString(cursor.getColumnIndex("password"));
        }


        newUserName.setText(un);
        oldPass.setText(pa);

        final String finalPa = pa;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPassSt = newPass.getText().toString();
                newUserNameSt = newUserName.getText().toString();
                oldPassSt = oldPass.getText().toString();
                if (newUserNameSt.equals("")){
                    newUserName.setError("Username can\'t be empty");
                }else if (newPassSt.equals("")){
                    newPass.setError("Password can\'t be empty");
                }else if (oldPassSt.equals("")){
                    oldPass.setError("Please enter old password");
                }else{
                    if (oldPassSt.equals(finalPa)){
                        String qu = "UPDATE user SET uname='"+newUserNameSt+"', password='"+newPassSt+"';";
                        if (handler.execAction(qu)){
                            Intent i = new Intent(ChangeUserName.this, AppBase.class);
                            startActivity(i);
                        }
                    }else{
                        oldPass.setError("Incorrect old password");
                    }
                }
            }
        });


    }
}
