package id.ac.umn.test;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.ac.umn.test.databinding.ActivityMonthlyViewBinding;

public class MonthlyView extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMonthlyViewBinding binding;
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    public static final int ADD_NOTE = 44;
    public Button bView;
    CalendarView calendarView;
    List<EventDay> mEventDays = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonthlyViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        bView = (Button) findViewById(R.id.picker);
        setTitle("Monthly View");

        bView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), bView);
                dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_menu, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if (menuItem.getTitle() == "Daily Calendar View") {
//                            Intent intent = new Intent(this, )
//                        }
                        return true;
                    }
                });
                dropDownMenu.show();
            }
        });

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MonthlyView.this, AddActivity.class);
//                startActivityForResult(intent, ADD_NOTE);
//            }
//        });

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clicked = eventDay.getCalendar();
                Intent intent = new Intent(MonthlyView.this, DetailActivity.class);
                if (eventDay instanceof MyEventDay) {
                    intent.putExtra(EVENT, (MyEventDay) eventDay);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_monthly_view);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            try {
                calendarView.setDate(myEventDay.getCalendar());
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }
            mEventDays.add(new EventDay(calendar, R.drawable.icon));
            calendarView.setEvents(mEventDays);
        }
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
        System.exit(0);
    }
}