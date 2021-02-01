package main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import fekedew.R;
import main.components.About;
import main.components.GridAdapter;
import main.components.SettingsActivity;
import main.database.DatabaseHandler;

public class AppBase extends AppCompatActivity {

    public static DatabaseHandler handler;
    public static Activity activity;
    ArrayList<String> basicFields;
    GridAdapter adapter;
    GridView gridView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mai_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        basicFields = new ArrayList<>();
        handler = new DatabaseHandler(this);
        activity = this;

        getSupportActionBar().show();

        gridView = findViewById(R.id.grid);
        basicFields.add("Add Student");
        basicFields.add("Student List");
        basicFields.add("Mark Attendance");
        basicFields.add("Attendance Report");
        basicFields.add("My class mark");
        basicFields.add("Book Loan");
        adapter = new GridAdapter(this, basicFields);
        gridView.setAdapter(adapter);
    }

    public void loadSettings(MenuItem item) {
        Intent launchIntent = new Intent(this, SettingsActivity.class);
        startActivity(launchIntent);
    }

    public void loadAbout(MenuItem item) {
        Intent launchIntent = new Intent(this, About.class);
        startActivity(launchIntent);
    }

    public void changeUser(MenuItem item) {
        Intent launchIntent = new Intent(this, ChangeUserName.class);
        startActivity(launchIntent);
    }
}
