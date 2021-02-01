package main.bookloan;

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
import android.widget.RadioButton;
import android.widget.Toast;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;
import main.profile.EditStudentActivity;

public class BookEdit extends AppCompatActivity {

    DatabaseHandler handler = AppBase.handler;
    Activity profileActivity = this;
    ListView listView;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        FloatingActionButton fab = findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(profileActivity, BorrowNew.class);
                startActivity(launchIntent);
            }
        });
        loadListView(2);
        RadioButton showOnlyA = findViewById(R.id.showAll);
        showOnlyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView(2);
            }
        });
        RadioButton showOnlyR = findViewById(R.id.showOnlyReturned);
        showOnlyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView(1);
            }
        });
        RadioButton showOnlyUR = findViewById(R.id.showOnlyUnReturned);
        showOnlyUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView(0);
            }
        });


    }

    private void loadListView(int type) {
        String qu;
        if (type == 0) {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE returned=0";
        } else if (type == 1) {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE returned=1";
        } else {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE 1";
        }
        Cursor cursorx = handler.execQuery(qu);
        if (cursorx == null || cursorx.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Borrowed Book Info Available: " + type, Toast.LENGTH_LONG).show();
        } else {
            cursorx.moveToFirst();
            while (!cursorx.isAfterLast()) {
//                tname.add(cursorx.getString(0));
//                tcontact.add(cursorx.getInt(1));
//                troll.add(cursorx.getInt(2));

                cursorx.moveToNext();
            }
//            adapter = new ProfileAdapter(tname, tcontact, profileActivity, troll);
//            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.book_menu, menu);
        return true;
    }

    public void editBook(MenuItem item) {
        Intent launchIntent = new Intent(this, EditStudentActivity.class);
        launchIntent.putExtra("TYPE", "EDIT");
        startActivity(launchIntent);
    }

    public void deleteBook(MenuItem item) {
        Intent launchIntent = new Intent(this, EditStudentActivity.class);
        launchIntent.putExtra("TYPE", "DELETE");
        startActivity(launchIntent);
    }

    public void addBook(MenuItem item) {
        Intent launchIntent = new Intent(this, EditStudentActivity.class);
        launchIntent.putExtra("TYPE", "DELETE");
        startActivity(launchIntent);
    }
}
