package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import id.ac.umn.test3.databinding.ActivityDailyCalendarBinding;

public class DailyCalendarActivity extends AppCompatActivity {
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    RecyclerView planListView;
    ArrayList<SourcePlanner> dailyEvents = SourcePlanner.sourcePlannerArrayList;
    private ActivityDailyCalendarBinding binding;

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
    }

    private void initWidgets() {
        monthDayText = findViewById(R.id.monthDayTV);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        planListView = (RecyclerView) findViewById(R.id.planListView);
        setPlanAdapter();
    }

    private void setPlanAdapter() {
        Predicate<SourcePlanner> condition = dailyEvent -> !dailyEvent.getDate().equals(selectedDate);
        dailyEvents.removeIf(condition);
        PlanAdapter planAdapter = new PlanAdapter(this, dailyEvents);
        planListView.setAdapter(planAdapter);
        planListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadFromDBToMemory() {
        DBHelper db = DBHelper.instanceOfDatabase(this);
        db.populatePlanListArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dailyEvents.clear();
        loadFromDBToMemory();
        setDayView();
    }

    private void setDayView() {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setPlanAdapter();
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
            case R.id.action_logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}