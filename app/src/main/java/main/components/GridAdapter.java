package main.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import fekedew.R;
import main.AppBase;
import main.attendance.AllReport;
import main.attendance.AttendanceActivity;
import main.attendance.AttendanceReport;
import main.bookloan.BookActivity;
import main.myClass.MyClassActivity;
import main.profile.ProfileActivity;
import main.profile.StudentRegistration;

public class GridAdapter extends BaseAdapter {
    public static Activity activity;
    ArrayList names;

    public GridAdapter(Activity activity, ArrayList names) {
        GridAdapter.activity = activity;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.grid_layout, null);
        }
        TextView textView = v.findViewById(R.id.namePlacer);
        ImageView imageView = v.findViewById(R.id.imageHolder);
        if (names.get(position).toString().equals("Mark Attendance")) {
            imageView.setImageResource(R.drawable.dash_mark_attendance);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = activity.getFragmentManager();
                    createRequest request = new createRequest();
                    request.show(fm, "Select");
                }
            });

        } else if (names.get(position).toString().equals("Attendance Report")) {
            imageView.setImageResource(R.drawable.ic_schedule);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectReportFormat();
                }
            });
        } else if (names.get(position).toString().equals("My class mark")) {
            imageView.setImageResource(R.drawable.ic_cgpa);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, MyClassActivity.class);
                    activity.startActivity(launchinIntent);
                }
            });

        } else if (names.get(position).toString().equals("Add Student")) {
            imageView.setImageResource(R.drawable.dash_add_student);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, StudentRegistration.class);
                    activity.startActivity(launchinIntent);
                }
            });
        } else if (names.get(position).toString().equals("Student List")) {
            imageView.setImageResource(R.drawable.dash_student_list);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, ProfileActivity.class);
                    activity.startActivity(launchinIntent);
                }
            });
        } else if (names.get(position).toString().equals("Book Loan")) {
            imageView.setImageResource(R.drawable.ic_notes);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(activity, BookActivity.class);
                    activity.startActivity(in);
                }
            });
        }

        textView.setText(names.get(position).toString());
        return v;
    }

    private void selectReportFormat() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Please select report format");
        String[] items = {"Daily", "Weekly", "Monthly", "Yearly"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        FragmentManager fm = activity.getFragmentManager();
                        createRequest2 request = new createRequest2();
                        request.show(fm, "Select");
                        break;
                    case 1:
                        FragmentManager fmw = activity.getFragmentManager();
                        createRequest3 requestw = new createRequest3();
                        requestw.show(fmw, "Select");
                        break;
                    case 2:
                        final Calendar today = Calendar.getInstance();
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(activity,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        Intent launchinIntent = new Intent(AppBase.activity, AllReport.class);
                                        launchinIntent.putExtra("WEEK", "NO");
                                        launchinIntent.putExtra("DATE", -1);
                                        launchinIntent.putExtra("MONTH", selectedMonth + 1);
                                        launchinIntent.putExtra("YEAR", selectedYear);
                                        AppBase.activity.startActivity(launchinIntent);
                                    }
                                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                        builder.setActivatedMonth(Calendar.MONTH)
                                .setMinYear(1990)
                                .setActivatedYear(today.get(Calendar.YEAR))
                                .setTitle("Select report month")
                                .setYearRange(2012, 2030)
                                .build()
                                .show();

                        break;
                    case 3:
                        final Calendar todays = Calendar.getInstance();
                        MonthPickerDialog.Builder builders = new MonthPickerDialog.Builder(activity,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        Intent launchinIntent = new Intent(AppBase.activity, AllReport.class);
                                        launchinIntent.putExtra("WEEK", "NO");
                                        launchinIntent.putExtra("DATE", -1);
                                        launchinIntent.putExtra("MONTH", 25);
                                        launchinIntent.putExtra("YEAR", selectedYear);
                                        AppBase.activity.startActivity(launchinIntent);
                                    }
                                }, todays.get(Calendar.YEAR), todays.get(Calendar.MONTH));
                        builders.setActivatedYear(todays.get(Calendar.YEAR))
                                .setTitle("Select report year")
                                .setYearRange(2012, 2030)
                                .showYearOnly()
                                .build()
                                .show();
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public static class createRequest extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = v.findViewById(R.id.datePicker);


            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            final int day = datePicker.getDayOfMonth();
                            final int month = datePicker.getMonth() + 1;
                            final int year = datePicker.getYear();

                            Cursor cursor = AppBase.handler.execQuery("SELECT * FROM ATTENDANCE WHERE day = '" +
                                    day + "' AND month='" + month + "' AND year='" + year + "';");
                            if (cursor == null || cursor.getCount() == 0) {
                                Intent launchinIntent = new Intent(AppBase.activity, AttendanceActivity.class);
                                launchinIntent.putExtra("DATE", day);
                                launchinIntent.putExtra("MONTH", month);
                                launchinIntent.putExtra("YEAR", year);
                                launchinIntent.putExtra("EDIT", "NO");
                                AppBase.activity.startActivity(launchinIntent);
                            } else {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                                alert.setTitle("Attendance Already Added!");
                                alert.setMessage("Please see today's attendance report and you can edit there if you want");
                                alert.setPositiveButton("See report", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int day = datePicker.getDayOfMonth();
                                        int month = datePicker.getMonth() + 1;
                                        int year = datePicker.getYear();

                                        Intent launchinIntent = new Intent(AppBase.activity, AttendanceReport.class);
                                        launchinIntent.putExtra("DATE", day);
                                        launchinIntent.putExtra("MONTH", month);
                                        launchinIntent.putExtra("YEAR", year);
                                        AppBase.activity.startActivity(launchinIntent);
                                    }
                                });
                                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }

    public static class createRequest2 extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = v.findViewById(R.id.datePicker);


            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();

                            Intent launchinIntent = new Intent(AppBase.activity, AttendanceReport.class);
                            launchinIntent.putExtra("WEEK", "NO");
                            launchinIntent.putExtra("DATE", day);
                            launchinIntent.putExtra("MONTH", month);
                            launchinIntent.putExtra("YEAR", year);
                            AppBase.activity.startActivity(launchinIntent);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
    public static class createRequest3 extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = v.findViewById(R.id.datePicker);


            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();

                            Intent launchinIntent = new Intent(AppBase.activity, AllReport.class);
                            launchinIntent.putExtra("WEEK", "YES");
                            launchinIntent.putExtra("DATE", day);
                            launchinIntent.putExtra("MONTH", month);
                            launchinIntent.putExtra("YEAR", year);
                            AppBase.activity.startActivity(launchinIntent);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
