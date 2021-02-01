package main.attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fekedew.R;

public class AttendanceReportAdapter extends BaseAdapter {
    ArrayList<String> times;
    ArrayList<String> registers;
    ArrayList<Integer> attend;
    Activity activity;

    public AttendanceReportAdapter(Activity activity, ArrayList<String> times, ArrayList<String> roll, ArrayList<Integer> attend) {
        this.times = times;
        this.activity = activity;
        this.registers = roll;
        this.attend = attend;

    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.list_report, null);
        }
        final int pos = position;
        TextView textView = v.findViewById(R.id.attendanceNameR);
        textView.setText(registers.get(position));
        TextView textView1 = v.findViewById(R.id.attendanceDateR);
        textView1.setText(times.get(position));
        TextView attMarkeer = v.findViewById(R.id.attMarkerR);
        String s;
        if (attend.get(position) == 1) s = "Present";
        else if (attend.get(position) == 0) s = "Absent";
        else s = "Permission";
        attMarkeer.setText(s);

        return v;
    }

}
