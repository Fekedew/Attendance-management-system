package main.bookloan;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fekedew.R;
import main.MainActivity;

public class BookAdapter extends BaseAdapter {


    ArrayList<Integer> returned;
    ArrayList<String> bookName, studName;

    Activity activity;

    public BookAdapter(Activity activity, ArrayList<String> bookName, ArrayList<String> studName, ArrayList<Integer> returned) {
        this.bookName = bookName;
        this.activity = activity;
        this.studName = studName;
        this.returned = returned;
    }

    @Override
    public int getCount() {
        return bookName.size();
    }

    @Override
    public Object getItem(int position) {
        return bookName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.book_list, null);
        }
        final int pos = position;
        TextView textView = v.findViewById(R.id.bookName);
        textView.setText(bookName.get(position));
        TextView s = v.findViewById(R.id.studName);
        s.setText(studName.get(position));
        String rr;
        CheckBox markAsReturn = v.findViewById(R.id.markAsReturned);
        TextView r = v.findViewById(R.id.returned);
        if (returned.get(position) == 1) {
            rr = "Book is returned";
            r.setBackgroundColor(Color.GREEN);
        } else {
            rr = "Book is not returned";
            r.setBackgroundColor(Color.RED);
        }
        r.setText(rr);



        markAsReturn.setTag(position);
        if (BookActivity.isActionMode){
            markAsReturn.setVisibility(View.VISIBLE);
        }else{
            markAsReturn.setVisibility(View.GONE);
        }
        markAsReturn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();

                if (BookActivity.selectedList.contains(BookActivity.bId.get(position))){
                    BookActivity.selectedList.remove(BookActivity.bId.get(position));
                }else{
                    BookActivity.selectedList.add(BookActivity.bId.get(position));
                }
                BookActivity.actionMode.setTitle(BookActivity.selectedList.size()+" item is selected.");
            }
        });
        return v;
    }

    public void removeItems(List<Integer> items){
        for (int id : items){
            MainActivity.handler.execAction("UPDATE BORROWEDSUBJECT SET returned=1 WHERE bid="+id);
        }
        notifyDataSetChanged();
    }
}
