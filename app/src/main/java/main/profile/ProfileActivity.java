package main.profile;

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
import android.widget.Toast;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHandler handler = AppBase.handler;
    Activity profileActivity = this;
    ListView listView;
    ProfileAdapter adapter;
    Activity activity = this;
    ArrayList<String> tname, gender;
    ArrayList<Integer> tcontact, troll, age;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_profile);

        tname = new ArrayList<>();
        troll = new ArrayList<>();
        tcontact = new ArrayList<>();
        gender = new ArrayList<>();
        age = new ArrayList<>();

        listView = findViewById(R.id.attendProfileView_list);
        FloatingActionButton fab = findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(profileActivity, StudentRegistration.class);
                startActivity(launchIntent);
            }
        });
        String qu = "SELECT * FROM STUDENT WHERE 1 ORDER BY name";
        Cursor cursorx = handler.execQuery(qu);
        if (cursorx == null || cursorx.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Student Info Available", Toast.LENGTH_LONG).show();
        } else {
            cursorx.moveToFirst();
            while (!cursorx.isAfterLast()) {
                tname.add(cursorx.getString(0));
                tcontact.add(cursorx.getInt(1));
                troll.add(cursorx.getInt(2));
                gender.add(cursorx.getString(3));
                age.add(cursorx.getInt(4));

                cursorx.moveToNext();
            }

            adapter = new ProfileAdapter(tname, tcontact, profileActivity, troll, gender, age);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }


    public void editStudent(MenuItem item) {
        Intent launchIntent = new Intent(this, EditStudentActivity.class);
        launchIntent.putExtra("TYPE", "EDIT");
        startActivity(launchIntent);
    }

    public void deleteStudent(MenuItem item) {
        Intent launchIntent = new Intent(this, EditStudentActivity.class);
        launchIntent.putExtra("TYPE", "DELETE");
        startActivity(launchIntent);
    }
}
