package id.ac.umn.test3;

import static android.view.View.VISIBLE;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

import id.ac.umn.test3.databinding.ActivityDailyCalendarBinding;

public class DailyCalendarActivity extends AppCompatActivity {
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private static RecyclerView hourListView;
    private ActivityDailyCalendarBinding binding;
    private static final int REQUEST_TAMBAH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyCalendarActivity.this, AddActivity.class));
            }
        });
        initWidgets();
        loadFromDBToMemory();
        ArrayList<SourcePlanner> dailyEvents = SourcePlanner.eventsForDate(selectedDate);
        HourAdapter hourAdapter = new HourAdapter(this, dailyEvents);
        hourListView.setLayoutManager(new LinearLayoutManager(this));
        hourListView.setAdapter(hourAdapter);
    }

    private void initWidgets() {
        monthDayText = findViewById(R.id.monthDayTV);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
        isiDaftarMusik();
    }

    private void loadFromDBToMemory() {
        DBHelper db = DBHelper.instanceOfDatabase(this);
        db.populatePlanListArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
    }

    private void isiDaftarMusik() {
        SourcePlanner.sourcePlannerArrayList.add(new SourcePlanner(1, "Rapat BPH Koor", "PPT", "12:00", LocalDate.now()));
        SourcePlanner.sourcePlannerArrayList.add(new SourcePlanner(2,"Tugas Akhir", "Makalah", "14:00", LocalDate.now()));
    }

    public void previousDayAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}