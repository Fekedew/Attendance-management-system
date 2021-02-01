package main.bookloan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;

public class BorrowNew extends AppCompatActivity {

    Spinner subjectList, studentList;
    Button borrow;
    DatabaseHandler handler = AppBase.handler;
    String selectedSub, selectedStu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_new);


        subjectList = findViewById(R.id.subjectList);
        studentList = findViewById(R.id.studentList);

        ArrayList<String> stu = new ArrayList<>();
        ArrayList<String> sub = new ArrayList<>();

        String qu = "SELECT * FROM STUDENT WHERE 1";
        Cursor cursorx = handler.execQuery(qu);
        if (cursorx == null || cursorx.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Student Info Available", Toast.LENGTH_LONG).show();
        } else {
            cursorx.moveToFirst();
            while (!cursorx.isAfterLast()) {
                stu.add(cursorx.getString(0) + " (" + cursorx.getString(3) + ")");
                cursorx.moveToNext();
            }
        }

        String q = "SELECT * FROM SUBJECT WHERE 1";
        Cursor cursorsub = handler.execQuery(q);
        if (cursorsub == null || cursorsub.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Subject Info Available", Toast.LENGTH_LONG).show();
        } else {
            cursorsub.moveToFirst();
            while (!cursorsub.isAfterLast()) {
                sub.add(cursorsub.getString(0));
                cursorsub.moveToNext();
            }
        }

        ArrayAdapter<String> subAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sub);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectList.setAdapter(subAdapter);

        ArrayAdapter<String> stuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stu);
        stuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentList.setAdapter(stuAdapter);

        subjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSub = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        studentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStu = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        borrow = findViewById(R.id.borrowNew);
        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStu == null || selectedSub == null)
                    Toast.makeText(BorrowNew.this, "Please first add and select both subject and student.", Toast.LENGTH_LONG).show();
                else {
                    String qu = "INSERT INTO BORROWEDSUBJECT VALUES (null, '" + selectedStu + "', '" + selectedSub + "', 0);";
                    if (handler.execAction(qu)) {
                        Toast.makeText(BorrowNew.this, "Successfully added.", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(BorrowNew.this, BookActivity.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(BorrowNew.this, "Falied to add.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
