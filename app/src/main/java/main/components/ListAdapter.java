package main.components;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import fekedew.R;
import main.AppBase;
import main.attendance.AttendanceActivity;

public class ListAdapter extends BaseAdapter {
    ArrayList<String> nameList;
    ArrayList<Integer> isPresent;
    Activity activity;
    String edit = "";

    ArrayList<Integer> attendanceList;

    public ListAdapter(Activity activity, ArrayList<String> nameList, ArrayList<Integer> reg, String edit) {
        this.nameList = nameList;
        this.activity = activity;
        this.isPresent = reg;
        this.edit = edit;
        attendanceList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            attendanceList.add(new Integer(1));
        }
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
            v = vi.inflate(R.layout.list_ele, null);
        }
        final int pos = position;
        if (edit.equals("edit")) {
            TextView textView = v.findViewById(R.id.attendanceName);
            textView.setText(nameList.get(position));
            final RadioButton attAbsent = v.findViewById(R.id.attAbsent);
            final RadioButton attPresent = v.findViewById(R.id.attPresent);
            final RadioButton attPermission = v.findViewById(R.id.attPermission);
            switch (isPresent.get(position)) {
                case 0:
                    attAbsent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 0);
                        }
                    });
                    attAbsent.setChecked(true);
                    attPresent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 1);
                        }
                    });
                    attPermission.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 2);
                        }
                    });
                    break;
                case 1:
                    attAbsent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 0);
                        }
                    });
                    attPresent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 1);
                        }
                    });
                    attPresent.setChecked(true);
                    attPermission.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 2);
                        }
                    });
                    break;
                case 2:
                    attAbsent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 0);
                        }
                    });
                    attPresent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 1);
                        }
                    });
                    attPermission.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceList.set(pos, 2);
                        }
                    });
                    attPermission.setChecked(true);
                    break;
            }

        } else {
            TextView textView = v.findViewById(R.id.attendanceName);
            textView.setText(nameList.get(position));
            final RadioButton attAbsent = v.findViewById(R.id.attAbsent);
            attAbsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attendanceList.set(pos, 0);
                }
            });
            final RadioButton attPresent = v.findViewById(R.id.attPresent);
            attPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attendanceList.set(pos, 1);
                }
            });
            final RadioButton attPermission = v.findViewById(R.id.attPermission);
            attPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attendanceList.set(pos, 2);
                }
            });
        }
        return v;
    }

    public void saveAll() {
        for (int i = 0; i < nameList.size(); i++) {
            String qu;
            if (edit.equals("edit")) {
                qu = "UPDATE ATTENDANCE SET  isPresent='" + attendanceList.get(i) + "' WHERE register='" + nameList.get(i) + "';";
            } else {
                qu = "INSERT INTO ATTENDANCE VALUES('" + AttendanceActivity.day + "', '" + AttendanceActivity.month + "', " +
                        "'" + AttendanceActivity.year + "', '" + nameList.get(i) + "'," +
                        "'" + attendanceList.get(i) + "');";
            }

//            Toast.makeText(activity, "Insert attend: " + AttendanceActivity.year, Toast.LENGTH_LONG).show();
            AppBase.handler.execAction(qu);
            activity.finish();
        }
    }
}
