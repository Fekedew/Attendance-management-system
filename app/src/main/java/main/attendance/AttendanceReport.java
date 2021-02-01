package main.attendance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;

public class AttendanceReport extends AppCompatActivity {

    ListView listView;
    AttendanceReportAdapter adapter;
    ArrayList<String> days, months, years;
    ArrayList<String> roll;
    ArrayList<Integer> attend;
    Activity activity = this;

    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_report);

        FloatingActionButton fab = findViewById(R.id.fab_sch);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = getIntent().getIntExtra("DATE", 0);
                month = getIntent().getIntExtra("MONTH", 0);
                year = getIntent().getIntExtra("YEAR", 0);
                Intent launchinIntent = new Intent(getBaseContext(), AttendanceActivity.class);
                launchinIntent.putExtra("DATE", day);
                launchinIntent.putExtra("MONTH", month);
                launchinIntent.putExtra("YEAR", year);
                launchinIntent.putExtra("EDIT", "YES");
                startActivity(launchinIntent);
            }
        });

        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();

        roll = new ArrayList<String>();
        attend = new ArrayList<Integer>();
        listView = findViewById(R.id.reportList);
        loadReports();
    }

    private void loadReports() {
        days.clear();
        months.clear();
        years.clear();
        int totalAbsent = 0, totalPresent = 0, totalPermission = 0, getAttend;
        day = getIntent().getIntExtra("DATE", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        String qu = "SELECT * FROM ATTENDANCE WHERE day='" + day + "' AND month='" + month + "' AND year='" + year + "';";
        Cursor cursor = AppBase.handler.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Attendance Available", Toast.LENGTH_LONG).show();
        } else {
            totalAbsent = 0;
            totalPresent = 0;
            totalPermission = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                roll.add(cursor.getString(3));
                days.add(cursor.getInt(0) + "-" + cursor.getInt(1) + "-" + cursor.getInt(2));
                getAttend = cursor.getInt(4);
                if (getAttend == 0) totalAbsent++;
                else if (getAttend == 1) totalPresent++;
                else if (getAttend == 2) totalPermission++;
                attend.add(cursor.getInt(4));
                cursor.moveToNext();
            }
        }
        String attendanceReport = totalPresent + "->Present, " + totalAbsent + "->Absent, " + totalPermission + "->Permission.";
        TextView attendanceReportTv = findViewById(R.id.attendanceReport);
        attendanceReportTv.setText(attendanceReport);
        adapter = new AttendanceReportAdapter(activity, days, roll, attend);
        listView.setAdapter(adapter);
    }

    public void refresh(MenuItem item) {
        loadReports();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scheduler_menu, menu);
        return true;
    }
}
