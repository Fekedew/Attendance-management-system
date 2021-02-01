package main.attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fekedew.R;

public class AllReportListAdapter extends BaseAdapter {
    ArrayList<String> nameList;
    ArrayList<Integer> tpresent, ta, tp;
    Activity activity;

    public AllReportListAdapter(Activity activity, ArrayList<String> nameList, ArrayList<Integer> tpresent, ArrayList<Integer> ta, ArrayList<Integer> tp) {
        this.nameList = nameList;
        this.activity = activity;
        this.tpresent = tpresent;
        this.ta = ta;
        this.tp = tp;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.all_report_list, null);
        }
        final int pos = position;
        TextView textView = v.findViewById(R.id.reportName);
        textView.setText(nameList.get(position));
        TextView totalAbsent = v.findViewById(R.id.totalAbsent);
        totalAbsent.setText(ta.get(position) + " Total absent");
        TextView totalPresent = v.findViewById(R.id.totalPresent);
        totalPresent.setText(tpresent.get(position) + " Total present");
        TextView totalPermission = v.findViewById(R.id.totalPermission);
        totalPermission.setText(tp.get(position) + " Total permission");
        return v;
    }

}
