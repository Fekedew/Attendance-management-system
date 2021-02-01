package main.bookloan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;

public class BookActivity extends AppCompatActivity {

    DatabaseHandler handler = AppBase.handler;
    Activity bookActivity = this;
    BookAdapter adapter;
    ListView listView;

    public static ArrayList<Integer> returned;
    public static ArrayList<String> bookName, studName;
    public static ArrayList<Integer> bId;

    public static boolean isActionMode = false;
    public static ActionMode actionMode = null;
    public static List<Integer> selectedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        FloatingActionButton fab = findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(bookActivity, BorrowNew.class);
                startActivity(launchIntent);
            }
        });

        listView = findViewById(R.id.bookListView);
        returned = new ArrayList<>();
        bookName = new ArrayList<>();
        studName = new ArrayList<>();
        bId = new ArrayList<>();

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
        listView = findViewById(R.id.bookListView);
        String qu;

        returned.clear();
        bookName.clear();
        studName.clear();
        listView.invalidateViews();
        if (type == 0) {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE returned=0";
            listView.invalidateViews();
        } else if (type == 1) {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE returned=1";
        } else {
            qu = "SELECT * FROM BORROWEDSUBJECT WHERE 1";
        }
        Cursor cursorx = handler.execQuery(qu);

        if (cursorx == null || cursorx.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Borrowed Book Info Available: ", Toast.LENGTH_LONG).show();
        } else {
            cursorx.moveToFirst();
            while (!cursorx.isAfterLast()) {
                returned.add(cursorx.getInt(3));
                bookName.add(cursorx.getString(2));
                studName.add(cursorx.getString(1));
                bId.add(cursorx.getInt(0));

                cursorx.moveToNext();
            }
            adapter = new BookAdapter(bookActivity, bookName, studName, returned);
            listView.setAdapter(adapter);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(modeListener);


        }
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.mark_menu, menu);

            isActionMode = true;
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.markMenu:
                    adapter.removeItems(selectedList);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;
            selectedList.clear();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.book_menu, menu);
        return true;
    }

    public void editBook(MenuItem item) {
//        Intent launchIntent = new Intent(this, BookAdd.class);
//        launchIntent.putExtra("TYPE", "EDIT");
//        startActivity(launchIntent);
    }

    public void deleteBook(MenuItem item) {
//        Intent launchIntent = new Intent(this, EditStudentActivity.class);
//        launchIntent.putExtra("TYPE", "DELETE");
//        startActivity(launchIntent);
    }

    public void addBook(MenuItem item) {
        Intent launchIntent = new Intent(this, BookAdd.class);
        launchIntent.putExtra("TYPE", "DELETE");
        startActivity(launchIntent);
    }
}
