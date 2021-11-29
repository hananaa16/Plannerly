package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.daysInWeekArray;
import static id.ac.umn.test3.CalendarUtils.monthYearFromDate;
import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import id.ac.umn.test3.databinding.ActivityWeekViewBinding;


public class WeekViewActivity extends AppCompatActivity {
//        implements CalendarAdapter.OnItemListener {
//    private TextView monthYearText;
//    private RecyclerView weeklyListView;
//    ArrayList<SourcePlanner> dailyEvents = SourcePlanner.sourcePlannerArrayList;
//    private Button picker;
//    private ActivityWeekViewBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityWeekViewBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        setSupportActionBar(binding.toolbar);
//        initWidgets();
//        loadFromDBToMemory();
//    }
//
//    private void initWidgets() {
//        monthYearText = findViewById(R.id.monthYearTV);
//        weeklyListView = findViewById(R.id.weeklyRecyclerView);
//        picker = findViewById(R.id.picker);
//        setEventAdapter();
//    }
//
//    private void loadFromDBToMemory() {
////        DBHelper db = DBHelper.instanceOfDatabase(this);
////        db.populatePlanListArray();
//    }
//
//    @Override
//    public void onItemClick(int position, LocalDate date) {
//        CalendarUtils.selectedDate = date;
//        startActivity(new Intent(this, DailyCalendarActivity.class));
//    }
//
//    public void dropdownAction(View view) {
//        PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), picker);
//        dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_menu, dropDownMenu.getMenu());
//        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.dropdown_menu1:
//                        startActivity(new Intent(WeekViewActivity.this, MonthlyView.class));
//                        return true;
//                    default:
//                        return WeekViewActivity.super.onOptionsItemSelected(menuItem);
//                }
//            }
//        });
//        dropDownMenu.show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        dailyEvents.clear();
//        loadFromDBToMemory();
//        setEventAdapter();
//    }
//
//    private void setEventAdapter() {
//        Predicate<SourcePlanner> condition = dailyEvent -> !dailyEvent.getDate().equals(LocalDate.now()) && !dailyEvent.getDate().equals(LocalDate.now().plusDays(1))
//                && !dailyEvent.getDate().equals(LocalDate.now().plusDays(2)) && !dailyEvent.getDate().equals(LocalDate.now().plusDays(3)) && !dailyEvent.getDate().equals(LocalDate.now().plusDays(4))
//                && !dailyEvent.getDate().equals(LocalDate.now().plusDays(5)) && !dailyEvent.getDate().equals(LocalDate.now().plusDays(6));
//        dailyEvents.removeIf(condition);
//        Collections.sort(dailyEvents, new Comparator<SourcePlanner>() {
//            @Override
//            public int compare(SourcePlanner sourcePlanner, SourcePlanner t1) {
//                return sourcePlanner.getDate().compareTo(t1.getDate());
//            }
//        });
//        EventAdapter eventAdapter = new EventAdapter(this, dailyEvents);
//        weeklyListView.setAdapter(eventAdapter);
//        weeklyListView.setLayoutManager(new LinearLayoutManager(this));
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
//            case R.id.action_logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(WeekViewActivity.this, LoginUser.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onBackPressed(){
//        finishAffinity();
//        System.exit(0);
//    }
}