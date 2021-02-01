package main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fekedew.R;
import main.database.DatabaseHandler;

public class Register extends AppCompatActivity {

    public static DatabaseHandler handler;
    public static Activity activity;
    Button login;
    EditText uname, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        handler = new DatabaseHandler(this);
        activity = this;


        uname = findViewById(R.id.txtName);
        pass = findViewById(R.id.txtPwd);


        login = findViewById(R.id.btnReg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un, pa;
                pa = pass.getText().toString();
                un = uname.getText().toString();
                if (un.isEmpty()) {
                    uname.setError("User name can't be empty");
                } else if (pa.isEmpty()) {

                    pass.setError("Password can't be empty");
                } else {
                    String qu = "INSERT INTO user VALUES('" + un + "', '" + pa + "');";
                    if (MainActivity.handler.execQuery("SELECT * FROM user WHERE uname='"+un+"' AND password='"+pa+"';").getCount() > 1){
                        Toast.makeText(Register.this, "User name and password are already exist", Toast.LENGTH_LONG).show();
                    }else {
                        MainActivity.handler.execAction(qu);
                        qu = "SELECT * FROM user WHERE uname = '" + un + "' and password ='" + pa + "';";
                        if (MainActivity.handler.execQuery(qu).getCount() > 1) {
                            Toast.makeText(getBaseContext(), "Successfuly Registered", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(activity, AppBase.class);
                            startActivity(in);
                            Register.this.finish();
                        }
                    }
                }
            }
        });

    }
}
