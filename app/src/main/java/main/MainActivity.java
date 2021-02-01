package main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fekedew.R;
import main.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHandler handler;
    public static Activity activity;
    Button login;
    EditText uname, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        handler = new DatabaseHandler(this);
        activity = this;
        uname = findViewById(R.id.txtName);
        pass = findViewById(R.id.txtPwd);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un, pa;
                pa = pass.getText().toString();
                un = uname.getText().toString();
                if (un.isEmpty()) {
                    uname.setError("User name can't be empry");
                } else if (pa.isEmpty()) {
                    pass.setError("Password can't be empty");
                } else {
                    if (un.equals("feke") & pa.equals("feke12@21")) {
                        Intent intent = new Intent(activity, Register.class);
                        startActivity(intent);
                    } else {
                        String qu = "SELECT * FROM user WHERE uname='" + un + "' and password='" + pa + "'";
                        Cursor cursor = handler.execQuery(qu);
                        if (cursor == null || cursor.getCount() == 0) {
                            Toast.makeText(activity, "Invalid user name or password.", Toast.LENGTH_LONG).show();
                        } else {
                            Intent in = new Intent(activity, AppBase.class);
                            startActivity(in);
                            MainActivity.this.finish();
                        }
                    }
                }
            }
        });

    }
}
