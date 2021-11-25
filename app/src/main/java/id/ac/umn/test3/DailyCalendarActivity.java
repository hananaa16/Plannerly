package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

import id.ac.umn.test3.databinding.ActivityDailyCalendarBinding;

public class DailyCalendarActivity extends AppCompatActivity {
    TextView monthDayText;
    TextView dayOfWeekTV;
    RecyclerView hourListView;
    LinkedList<SourcePlanner> daftarMusik= new LinkedList<>();
    HourAdapter mAdapter;
    private ActivityDailyCalendarBinding binding;
    private static final int REQUEST_TAMBAH = 1;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isiDaftarMusik();
        binding = ActivityDailyCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DailyCalendarActivity.this, AddActivity.class), REQUEST_TAMBAH);
            }
        });

        hourListView = (RecyclerView) findViewById(R.id.hourListView);
        mAdapter = new HourAdapter(this, daftarMusik);
        hourListView.setAdapter(mAdapter);
        hourListView.setLayoutManager(new LinearLayoutManager(this));
//        initWidgets();
//        setHourAdapter();
    }

//    private void initWidgets() {
//        monthDayText = findViewById(R.id.monthDayTV);
//        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
//        hourListView = findViewById(R.id.hourListView);
//        db = new DBHelper(this);
//        ArrayList arrayList = db.getAllPlans();
//        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dailytask_item_layout, arrayList);
//        setHourAdapter();
//        isiDaftarMusik();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setDayView();
//    }
//
//    private void setDayView() {
//        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
//        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
//        dayOfWeekTV.setText(dayOfWeek);
//        setHourAdapter();
//    }
//
    private void isiDaftarMusik() {
        daftarMusik.add(new SourcePlanner("Rapat BPH Koor", "Rapat", "PPT"));
        daftarMusik.add(new SourcePlanner("Tugas Akhir", "Tugas", "Makalah"));
    }
//
//    private void setHourAdapter() {
//        HourAdapter hourAdapter = new HourAdapter(this, daftarMusik);
//        hourListView.setAdapter(hourAdapter);
//        hourListView.setLayoutManager(new LinearLayoutManager(this));
//    }
//
//    private ArrayList<HourEvent> hourEventList() {
//        ArrayList<HourEvent> list = new ArrayList<>();
//        for (int hour = 0; hour < 24; hour++) {
//            LocalTime time = LocalTime.of(hour, 0);
//            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
//            HourEvent hourEvent = new HourEvent(time, events);
//            list.add(hourEvent);
//        }
//        return list;
//    }
//
//    public void previousDayAction(View view) {
//        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
//        setDayView();
//    }
//
//    public void nextDayAction(View view) {
//        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
//        setDayView();
//    }
//
//    public void newEventAction(View view) {
//        startActivity(new Intent(this, EventEditActivity.class));
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}