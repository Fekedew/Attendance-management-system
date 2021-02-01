package main.myClass;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;

public class MyClassActivity extends AppCompatActivity {

    Spinner studentListMark;
    Button addMark;
    DatabaseHandler handler = AppBase.handler;
    String selectedStu;
    public static EditText etone, ettwo, etthree, etfour, et60, et100;
    Activity activity;


    public static String stuId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_class);
        activity = this;


        studentListMark = findViewById(R.id.studentListMark);
        etone = findViewById(R.id.out10One);
        ettwo = findViewById(R.id.out10Two);
        etthree = findViewById(R.id.out10Three);
        etfour = findViewById(R.id.out10Four);
        et60 = findViewById(R.id.out60);
        et100 = findViewById(R.id.out100);

        ArrayList<String> stu = new ArrayList<>();

        String qu = "SELECT * FROM STUDENT WHERE 1 ORDER BY name";
        Cursor cursorx = handler.execQuery(qu);
        if (cursorx == null || cursorx.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Student Info Available", Toast.LENGTH_LONG).show();
        } else {
            cursorx.moveToFirst();
            while (!cursorx.isAfterLast()) {
                stu.add(cursorx.getString(0) + " (" + cursorx.getString(3) + ")=>"+cursorx.getString(2));
                cursorx.moveToNext();
            }
        }

        ArrayAdapter<String> stuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stu);
        stuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentListMark.setAdapter(stuAdapter);

        studentListMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStu = parent.getItemAtPosition(position).toString();
                String[] stuIds = selectedStu.split("=>");
                stuId = stuIds[1];


                String qu = "SELECT * FROM MARK WHERE roll='"+stuId+"';";
                Cursor cursors = handler.execQuery(qu);

                int c = 0;
                if (cursors == null || cursors.getCount() == 0) {
                    Toast.makeText(activity, "No mark for selected student.", Toast.LENGTH_LONG).show();
                    resetTextView();
                } else {
                    cursors.moveToFirst();
                    while (!cursors.isAfterLast()) {
                        etone.setText(cursors.getString(0));
                        ettwo.setText(cursors.getString(1));
                        etthree.setText(cursors.getString(2));
                        etfour.setText(cursors.getString(3));
                        et60.setText(cursors.getString(3));
                        et100.setText(cursors.getString(4));
                        et100.setFocusable(false);
                        et100.setEnabled(false);
                        et100.setCursorVisible(false);
                        et100.setKeyListener(null);

                        c++;
                        cursors.moveToNext();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addMark = findViewById(R.id.addMark);
        addMark.setOnClickListener(new View.OnClickListener() {
            String one;
            String two;
            String three, four;
            String sixty;

            @Override
            public void onClick(View v) {
                one = etone.getText().toString();
                two = ettwo.getText().toString();
                three = etthree.getText().toString();
                four = etfour.getText().toString();
                sixty = et60.getText().toString();
                int sum = 0;
                try {
                    sum += Integer.parseInt(one);
                }catch (NumberFormatException e){}
                try{
                    sum +=Integer.parseInt(two);
                }catch (NumberFormatException e){}
                try{
                    sum += Integer.parseInt(three);
                }catch (NumberFormatException e){ }
                try{
                    sum += Integer.parseInt(four);
                }catch (NumberFormatException e){ }
                try {
                    sum += Integer.parseInt(sixty);
                }catch (NumberFormatException e){}

                if (selectedStu == null)
                    Toast.makeText(MyClassActivity.this, "Please first add and select student.", Toast.LENGTH_LONG).show();
                else {
                    String qup = "UPDATE MARK SET out10one='" + one + "', out10two='" + two + "', out10three='" + three+ "', out10four='" + four + "', " +
                            "out60='" + sixty + "', out100='" + sum + "' WHERE roll='"+stuId+"';";
                    String qu = "INSERT INTO MARK VALUES ( '" + one + "', '" + two + "', '" + three+ "', '" + four + "', '" + sixty + "', " +
                            "'" + sum + "', '"+stuId+"');";
                    if (handler.execAction(qu)) {
                        Toast.makeText(MyClassActivity.this, "Successfully added.", Toast.LENGTH_LONG).show();
                        et100.setText(sum+"");
                    } else if (handler.execAction(qup)){
                        Toast.makeText(MyClassActivity.this, "Successfully updated student mark.", Toast.LENGTH_LONG).show();
                        et100.setText(sum+"");
                    }else{
                        Toast.makeText(MyClassActivity.this, "Fail to do this operation.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void resetTextView() {
        etone.setText(0+"");
        ettwo.setText(0+"");
        etthree.setText(0+"");
        etfour.setText(0+"");
        et60.setText(0+"");
        et100.setText(0+"");

    }
}
