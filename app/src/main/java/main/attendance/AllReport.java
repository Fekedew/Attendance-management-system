package main.attendance;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;

public class AllReport extends AppCompatActivity {

    public static int month;
    public static int year;
    public int day;
    public static String week;

    ListView listView;
    AllReportListAdapter adapter;
    ArrayList<String> nameList;
    ArrayList<Integer> tpresent, ta, tp;
    Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_report);


        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        day = getIntent().getIntExtra("DATE", 0);
        week = getIntent().getStringExtra("WEEK");

        listView = findViewById(R.id.allReportListView);
        nameList = new ArrayList<>();
        tpresent = new ArrayList<>();
        ta = new ArrayList<>();
        tp = new ArrayList<>();
        if (week.equals("YES")) {
            loadListViewWeek();
        } else {
            loadListView();
        }
    }

    public void loadListView() {
        nameList.clear();
        tpresent.clear();
        ta.clear();
        tp.clear();

        int allStudentPresent = 0, allStudentAbsent = 0, allStudentPermission = 0;
        int mallStudentPresent = 0, mallStudentAbsent = 0, mallStudentPermission = 0;
        int fallStudentPresent = 0, fallStudentAbsent = 0, fallStudentPermission = 0;
        String q = "SELECT * FROM STUDENT ORDER BY name";
        Cursor c = AppBase.handler.execQuery(q);
        if (c == null || c.getCount() == 0) {
            Toast.makeText(this, "Student does not exit.", Toast.LENGTH_LONG).show();
        } else {
            String rolls;
            String gen;
            c.moveToFirst();
            while (!c.isAfterLast()) {

                gen = c.getString(3);
                rolls = c.getString(0) + " (" + c.getInt(2) + ')';
                nameList.add(rolls);
                String qu;
                int taa = 0;
                int tpp = 0;
                int tpres = 0;
                int ftaa = 0;
                int ftpp = 0;
                int ftpres = 0;
                int mtaa = 0;
                int mtpp = 0;
                int mtpres = 0;

                if (month != 25)
                    qu = "SELECT * FROM ATTENDANCE WHERE month='" + month + "' AND year='" + year + "' AND register='" + rolls + "';";
                else
                    qu = "SELECT * FROM ATTENDANCE WHERE year='" + year + "' AND register='" + rolls + "';";
                Cursor cursor = AppBase.handler.execQuery(qu);
                if (cursor == null || cursor.getCount() == 0) {
//                    Toast.makeText(this, "Attendance does not exit.", Toast.LENGTH_LONG).show();
                } else {
                    int ctr = 0;
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        int isPresent = cursor.getInt(4);
                        if (isPresent == 0) {
                            taa++;
                            if (gen.equals("M")) {
                                mtaa++;
                            } else {
                                ftaa++;
                            }
                        } else if (isPresent == 1) {
                            tpres++;

                            if (gen.equals("M")) {
                                mtpres++;
                            } else {
                                ftpres++;
                            }
                        } else if (isPresent == 2) {
                            tpp++;

                            if (gen.equals("M")) {
                                mtpp++;
                            } else {
                                ftpp++;
                            }
                        }
                        cursor.moveToNext();
                        ctr++;
                    }
                    if (ctr == 0) {
                        Toast.makeText(getBaseContext(), "No Attendance Found", Toast.LENGTH_LONG).show();
                    }
                }
                allStudentAbsent += taa;
                allStudentPresent += tpres;
                allStudentPermission += tpp;
                mallStudentAbsent += mtaa;
                mallStudentPresent += mtpres;
                mallStudentPermission += mtpp;
                fallStudentAbsent += ftaa;
                fallStudentPresent += ftpres;
                fallStudentPermission += ftpp;
                ta.add(taa);
                tp.add(tpp);
                tpresent.add(tpres);
                c.moveToNext();
            }

        }
        String attendanceReport = allStudentPresent + "(" + mallStudentPresent + " M, " + fallStudentPresent + " F)->Present, " +
                "" + allStudentAbsent + "(" + mallStudentAbsent + " M, " + fallStudentAbsent + " F)->Absent, " +
                "" + allStudentPermission + "(" + mallStudentPermission + " M, " + fallStudentPermission + " F)->Permission.";
        TextView attendanceReportTv = findViewById(R.id.allAttendanceReport);
        attendanceReportTv.setText(attendanceReport);
        adapter = new AllReportListAdapter(activity, nameList, tpresent, ta, tp);
        listView.setAdapter(adapter);
    }
    public void loadListViewWeek() {
        nameList.clear();
        tpresent.clear();
        ta.clear();
        tp.clear();

        int allStudentPresent = 0, allStudentAbsent = 0, allStudentPermission = 0;
        int mallStudentPresent = 0, mallStudentAbsent = 0, mallStudentPermission = 0;
        int fallStudentPresent = 0, fallStudentAbsent = 0, fallStudentPermission = 0;
        String q = "SELECT * FROM STUDENT ORDER BY name";
        Cursor c = AppBase.handler.execQuery(q);
        if (c == null || c.getCount() == 0) {
            Toast.makeText(this, "Student does not exit.", Toast.LENGTH_LONG).show();
        } else {
            String rolls;
            String gen;
            c.moveToFirst();
            while (!c.isAfterLast()) {

                gen = c.getString(3);
                rolls = c.getString(0) + " (" + c.getInt(2) + ')';
                nameList.add(rolls);
                String qu;
                int taa = 0;
                int tpp = 0;
                int tpres = 0;
                int ftaa = 0;
                int ftpp = 0;
                int ftpres = 0;
                int mtaa = 0;
                int mtpp = 0;
                int mtpres = 0;
                int day1, day2, day3, day4;
                switch (day){
                    case 26:
                        day1 = day;
                        day2=day+4;
                        day3=1;
                        day4=2;
                        break;
                    case 27:
                        day1 = day;
                        day2=day+4;
                        day3=1;
                        day4=2;
                        break;
                    case 28:
                        day1 = day;
                        day2=day+4;
                        day3=1;
                        day4=2;
                        break;
                    case 29:
                        day1 = day;
                        day2=day+4;
                        day3=1;
                        day4=2;
                        break;
                    case 30:
                        day1 = day;
                        day2=day;
                        day3=1;
                        day4=2;
                        break;
                }

                if (month != 25)
                    qu = "SELECT * FROM ATTENDANCE WHERE day BETWEEN '"+day+"' AND '"+day+5+"' AND month='" + month + "' AND year='" + year + "' AND register='" + rolls + "';";
                else
                    qu = "SELECT * FROM ATTENDANCE WHERE year='" + year + "' AND register='" + rolls + "';";
                Cursor cursor = AppBase.handler.execQuery(qu);
                if (cursor == null || cursor.getCount() == 0) {
//                    Toast.makeText(this, "Attendance does not exit.", Toast.LENGTH_LONG).show();
                } else {
                    int ctr = 0;
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        int isPresent = cursor.getInt(4);
                        if (isPresent == 0) {
                            taa++;
                            if (gen.equals("M")) {
                                mtaa++;
                            } else {
                                ftaa++;
                            }
                        } else if (isPresent == 1) {
                            tpres++;

                            if (gen.equals("M")) {
                                mtpres++;
                            } else {
                                ftpres++;
                            }
                        } else if (isPresent == 2) {
                            tpp++;

                            if (gen.equals("M")) {
                                mtpp++;
                            } else {
                                ftpp++;
                            }
                        }
                        cursor.moveToNext();
                        ctr++;
                    }
                    if (ctr == 0) {
                        Toast.makeText(getBaseContext(), "No Attendance Found", Toast.LENGTH_LONG).show();
                    }
                }
                allStudentAbsent += taa;
                allStudentPresent += tpres;
                allStudentPermission += tpp;
                mallStudentAbsent += mtaa;
                mallStudentPresent += mtpres;
                mallStudentPermission += mtpp;
                fallStudentAbsent += ftaa;
                fallStudentPresent += ftpres;
                fallStudentPermission += ftpp;
                ta.add(taa);
                tp.add(tpp);
                tpresent.add(tpres);
                c.moveToNext();
            }

        }
        String attendanceReport = allStudentPresent + "(" + mallStudentPresent + " M, " + fallStudentPresent + " F)->Present, " +
                "" + allStudentAbsent + "(" + mallStudentAbsent + " M, " + fallStudentAbsent + " F)->Absent, " +
                "" + allStudentPermission + "(" + mallStudentPermission + " M, " + fallStudentPermission + " F)->Permission.";
        TextView attendanceReportTv = findViewById(R.id.allAttendanceReport);
        attendanceReportTv.setText(attendanceReport);
        adapter = new AllReportListAdapter(activity, nameList, tpresent, ta, tp);
        listView.setAdapter(adapter);
    }


}
