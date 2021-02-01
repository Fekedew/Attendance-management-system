package main.attendance;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;
import main.components.ListAdapter;

public class AttendanceActivity extends AppCompatActivity {

    public static int day;
    public static int month;
    public static int year;
    public static String edit;
    ListView listView;
    ListAdapter adapter;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> names;
    ArrayList<Integer> registers;
    Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        day = getIntent().getIntExtra("DATE", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        edit = getIntent().getStringExtra("EDIT");

        listView = findViewById(R.id.attendanceListViwe);
        names = new ArrayList<>();
        registers = new ArrayList<Integer>();
        if (edit.equals("YES")) loadListView1();
        else loadListView();

        Button btnx = findViewById(R.id.buttonSaveAttendance);
        assert btnx != null;
        btnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Saving", Toast.LENGTH_LONG).show();
                adapter.saveAll();
            }
        });

    }

    public void loadListView() {
        names.clear();
        registers.clear();
        String qu = "SELECT * FROM STUDENT WHERE 1 ORDER BY name";
        Cursor cursor = AppBase.handler.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "Attendance does not exit.", Toast.LENGTH_LONG).show();
        } else {
            int ctr = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(0) + " (" + cursor.getInt(2) + ')');
                cursor.moveToNext();
                ctr++;
            }
            if (ctr == 0) {
                Toast.makeText(getBaseContext(), "No Students Found", Toast.LENGTH_LONG).show();
            }
        }
        adapter = new ListAdapter(thisActivity, names, registers, "");
        listView.setAdapter(adapter);
    }

    public void loadListView1() {
        names.clear();
        registers.clear();
        String qu = "SELECT * FROM ATTENDANCE WHERE day='" + day + "' AND month='" + month + "' AND year='" + year + "';";
        Cursor cursor = AppBase.handler.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "Attendance does not exit.", Toast.LENGTH_LONG).show();
        } else {
            int ctr = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(3));
                registers.add(cursor.getInt(4));
                cursor.moveToNext();
                ctr++;
            }
            if (ctr == 0) {
                Toast.makeText(getBaseContext(), "No Students Found", Toast.LENGTH_LONG).show();
            }
        }
        adapter = new ListAdapter(thisActivity, names, registers, "edit");
        listView.setAdapter(adapter);
    }
}
