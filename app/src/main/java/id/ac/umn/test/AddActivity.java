package id.ac.umn.test;

import static java.util.Calendar.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private EditText judul, deskripsi, waktu;
    private TextView tanggal;
    private CalendarView calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        judul = (EditText) findViewById(R.id.judulAdd);
        deskripsi = (EditText) findViewById(R.id.deskripsiAdd);
        waktu = (EditText) findViewById(R.id.waktuAdd);
        tanggal = (TextView) findViewById(R.id.tanggalAdd);
        Button button = findViewById(R.id.add);
        calendar = findViewById(R.id.datePicker);
        Date c = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        date = dateFormat.format(c);
        tanggal.setText(date);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year, month, day);
                date = dateFormat.format(calendar1.getTime());
                tanggal.setText(date);
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent returnIntent = new Intent();
//
//                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
//                        R.drawable.ic_message_black_48dp, noteEditText.getText().toString());
//
//                returnIntent.putExtra(MonthlyView.RESULT, myEventDay);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();
//            }
//        });
    }

    public void simpanData(View view) {
        String judul2 = judul.getText().toString();
        String deskripsi2 = deskripsi.getText().toString();
        String waktu2 = waktu.getText().toString();
        if (judul2.length() <= 0 || deskripsi2.length() <= 0) {
            Toast.makeText(this, "Judul dan deskripsi wajib diisi", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}