package com.example.todoapplication.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.todoapplication.Database.Todo;
import com.example.todoapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTodo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTodo extends AppCompatDialogFragment {

    private EditText txt_title, txt_date, txt_time;
    RadioGroup priority_group;
    private TodoViewModel todoViewModel;
    private String title;
    private String mode;
    private Bundle extra_data;

    @SuppressWarnings("ValidFragment")
    AddTodo(String title, String mode, Bundle bundle){
        this.title = title;
        this.mode = mode;
        this.extra_data = bundle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_todo, null);
        builder.setView(view).setTitle(this.title).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = txt_title.getText().toString();
                String time = txt_time.getText().toString();
                String date = txt_date.getText().toString();

                String priority = "High";
                int checkedId = priority_group.getCheckedRadioButtonId();
                switch (checkedId){
                    case R.id.radButton1:
                        priority = "High";
                        break;
                    case R.id.radButton2:
                        priority = "Medium";
                        break;
                    case R.id.radButton3:
                        priority = "Low";
                }

                if(mode == "add"){
                    Todo todo = new Todo(title, date, time, priority);
                    todoViewModel.insert(todo);

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Saving")
                            .setContentText("New Todo added")
                            .show();

                } else {
                    Todo todo = new Todo(extra_data.getInt("id"), title, date, time, priority);
                    todoViewModel.update(todo);

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Updating")
                            .setContentText("Todo detail updated")
                            .show();
                }
            }
        });

        // todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        txt_title = view.findViewById(R.id.todo_title);
        txt_date = view.findViewById(R.id.todo_date);
        txt_time = view.findViewById(R.id.todo_time);
        priority_group = view.findViewById(R.id.radioGroup);

        if(this.mode == "edit"){
            txt_title.setText(this.extra_data.getString("title"));
            txt_date.setText(this.extra_data.getString("date"));
            txt_time.setText(this.extra_data.getString("time"));

            String priority = this.extra_data.getString("priority");
            switch(priority){
                case "High":
                    priority_group.check(R.id.radButton1);
                    break;
                case "Medium":
                    priority_group.check(R.id.radButton2);
                    break;
                case "Low":
                    priority_group.check(R.id.radButton3);
            }
        }


        final Calendar myCalendar = Calendar.getInstance();

        txt_time.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txt_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //Date Picker

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txt_date.setText(sdf.format(myCalendar.getTime()));
            }

        };
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return builder.create();
    }

}
