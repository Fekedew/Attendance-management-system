package main.profile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fekedew.R;

public class ProfileAdapter extends BaseAdapter {
    ArrayList<String> tname, gender;
    Activity activity;
    ArrayList<Integer> tcontact;
    ArrayList<Integer> troll, age;

    public ProfileAdapter(ArrayList<String> tname, ArrayList<Integer> tcontact, Activity profileActivity, ArrayList<Integer> troll, ArrayList<String> gender, ArrayList<Integer> age) {
        this.tname = tname;
        this.tcontact = tcontact;
        this.activity = profileActivity;
        this.troll = troll;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public int getCount() {
        return tname.size();
    }

    @Override
    public Object getItem(int position) {
        return tname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.student_row, null);
        }
        final int pos = position;
        ImageView imageView = v.findViewById(R.id.imageViewFlag);
        imageView.setImageResource(R.drawable.profileimage);
        TextView textView = v.findViewById(R.id.textName);
        textView.setText(tname.get(position) + " (" + gender.get(position) + ")");
        TextView textView1 = v.findViewById(R.id.textContact);
        textView1.setText("Contact: " + tcontact.get(position));
        TextView textView2 = v.findViewById(R.id.texRoll);
        textView2.setText("Roll number: " + troll.get(position));
        TextView textView3 = v.findViewById(R.id.txtAge);
        textView3.setText("Age: " + age.get(position));
        return v;
    }
}
