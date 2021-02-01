package main.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import fekedew.R;
import main.AppBase;
import main.bookloan.BookActivity;
import main.bookloan.BorrowNew;

public class StudentRegistration extends AppCompatActivity {


    Activity activity = this;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registartion);
        save = findViewById(R.id.buttonSAVE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

    }


    public void saveToDatabase() {
        EditText name = findViewById(R.id.edit_name);
        EditText roll = findViewById(R.id.roll);
        EditText contact = findViewById(R.id.contact);
        EditText age = findViewById(R.id.age);
        RadioGroup gender = findViewById(R.id.gender);

        if (name.getText().length() < 2 || roll.getText().length() == 0 ||
                contact.getText().length() < 2) {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Invalid");
            alert.setMessage("Insufficient Data");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }
        int contactn = 0, rolln = 0, agen = 0;
        String gendern = "M";
        try {
            rolln = Integer.parseInt(roll.getText().toString());
            agen = Integer.parseInt(age.getText().toString());
            if (gender.getCheckedRadioButtonId() == R.id.male)
                gendern = "M";
            else gendern = "F";

            String qu = "INSERT INTO STUDENT VALUES('" + name.getText().toString() + "'," +
                    "'" + Integer.parseInt(contact.getText().toString()) + "'," + rolln + ", '" + gendern + "', " + agen + ");";
            if (AppBase.handler.execAction(qu)) {
                Toast.makeText(getBaseContext(), "Student Added", Toast.LENGTH_SHORT).show();
                Toast.makeText(StudentRegistration.this, "Successfully added.", Toast.LENGTH_LONG).show();
                Intent in = new Intent(StudentRegistration.this, ProfileActivity.class);
                startActivity(in);
                this.finish();
            }else{
                Toast.makeText(getBaseContext(), "Student roll already exists." ,Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(activity, "Please enter correct format", Toast.LENGTH_LONG).show();
        }
    }
}
