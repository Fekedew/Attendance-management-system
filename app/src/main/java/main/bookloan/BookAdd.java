package main.bookloan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fekedew.R;
import main.AppBase;
import main.database.DatabaseHandler;

public class BookAdd extends AppCompatActivity {

    DatabaseHandler handler = AppBase.handler;
    Activity bookActivity = this;

    Button addBook;
    EditText bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        bookName = findViewById(R.id.txtBookName);
        addBook = findViewById(R.id.addBook);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookn = bookName.getText().toString();
                if (bookn.isEmpty()) {
                    bookName.setError("Book name can\'t be empty");
                } else {
                    String q = "SELECT * FROM SUBJECT WHERE sname='" + bookn + "';";
                    Cursor c = handler.execQuery(q);
                    if (c.getCount() != 0) {
                        Toast.makeText(bookActivity, "Book already added", Toast.LENGTH_LONG).show();
                    } else {
                        String qu = "INSERT INTO SUBJECT VALUES('" + bookn + "');";
                        if (handler.execAction(qu)) {
                            Toast.makeText(bookActivity, "Book added successful", Toast.LENGTH_SHORT).show();
                            Toast.makeText(BookAdd.this, "Successfully added.", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(BookAdd.this, BookActivity.class);
                            startActivity(in);
                        } else {
                            Toast.makeText(bookActivity, "Failed to add book", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


    }
}