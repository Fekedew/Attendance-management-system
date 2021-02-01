package main.profile;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import fekedew.R;
import main.AppBase;

public class EditStudentActivity extends AppCompatActivity {
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__student);

        Button loadButton = findViewById(R.id.loadForEdit);
        assert loadButton != null;
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText adm = findViewById(R.id.register_);
                String qu = "SELECT * FROM STUDENT WHERE roll = '" + adm.getText().toString() + "'";
                Cursor cr = AppBase.handler.execQuery(qu);
                if (cr == null || cr.getCount() == 0) {
                    Toast.makeText(getBaseContext(), "No Such Student", Toast.LENGTH_LONG).show();
                } else {
                    cr.moveToFirst();
                    try {
                        EditText name = findViewById(R.id.edit_name_);
                        EditText roll = findViewById(R.id.roll_);
                        EditText contact = findViewById(R.id.contact_);
                        EditText age = findViewById(R.id.age_);
                        RadioButton male = findViewById(R.id.male_);
                        RadioButton female = findViewById(R.id.female_);

                        assert name != null;
                        name.setText(cr.getString(0));
                        assert roll != null;
                        roll.setText(cr.getString(2));
                        assert contact != null;
                        contact.setText(cr.getString(1));
                        assert age != null;
                        age.setText(cr.getString(4));
                        if (cr.getString(3).equals("M"))
                            male.setChecked(true);
                        else female.setChecked(true);
                    } catch (Exception e) {
                    }
                }
            }
        });
        Button saveEdit = findViewById(R.id.buttonSAVEEDITS);
        final String type = getIntent().getStringExtra("TYPE");
        if (type.equals("DELETE"))
            saveEdit.setText("Delete student");
        else
            saveEdit.setText("Save changes");
        assert saveEdit != null;
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.edit_name_);
                EditText roll = findViewById(R.id.roll_);
                EditText contact = findViewById(R.id.contact_);
                EditText adm = findViewById(R.id.register_);
                EditText age = findViewById(R.id.age_);
                RadioGroup gender = findViewById(R.id.gender_);
                String gn;
                if (gender.getCheckedRadioButtonId() == R.id.male_)
                    gn = "M";
                else gn = "F";

                String qu = null;
                if (name.getText().toString().isEmpty()) {
                    name.setError("Name can'\t be empty!");
                } else if (roll.getText().toString().isEmpty()) {
                    roll.setError("Roll can\'t be empty!");
                } else {
                    if (adm.getText().toString().isEmpty()) {
                        roll.setError("Roll can\'t be empty!");
                    } else if (type.equals("EDIT"))
                        qu = "UPDATE STUDENT SET name = '" + name.getText().toString() + "' , " +
                                " roll = " + roll.getText().toString() + " , contact = '" + contact.getText().toString() + "', " +
                                "age=" + age.getText().toString() + ", gender='" + gn + "' WHERE roll = '" + adm.getText().toString() + "'";
                    else
                        qu = "DELETE FROM STUDENT WHERE roll = '" + adm.getText().toString() + "'";
                    if (AppBase.handler.execAction(qu)) {
                        if (type.equals("DELETE"))
                            Toast.makeText(getBaseContext(), "Delete successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getBaseContext(), "Edit Saved", Toast.LENGTH_LONG).show();
                        activity.finish();

                    } else
                        Toast.makeText(getBaseContext(), "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
