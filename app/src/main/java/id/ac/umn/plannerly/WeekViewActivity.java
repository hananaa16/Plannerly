package id.ac.umn.plannerly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import id.ac.umn.plannerly.databinding.ActivityWeekViewBinding;

public class WeekViewActivity extends AppCompatActivity {
    private TextView monthYearText;
    RecyclerView weeklyListView;
    ArrayList<SourcePlanner> dailyEvents = SourcePlanner.sourcePlannerArrayList;
    private Button picker;
    private ActivityWeekViewBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    WeeklyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeekViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        initWidgets();
    }

    private void initWidgets() {
        monthYearText = findViewById(R.id.monthYearTV);
        picker = findViewById(R.id.picker);
        weeklyListView = (RecyclerView) findViewById(R.id.weeklyRecyclerView);
        weeklyListView.setLayoutManager(new LinearLayoutManager(this));
        setWeeklyAdapter();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private void setWeeklyAdapter() {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        Date date2= convertToDateViaInstant(LocalDate.now());
        Date date3= convertToDateViaInstant(LocalDate.now().plusDays(6));
        Timestamp timestamp1 = new Timestamp(date2);
        Timestamp timestamp2 = new Timestamp(date3);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("tasks")
                .whereEqualTo("id",onlineUserID)
                .whereGreaterThanOrEqualTo("date",timestamp1)
                .whereLessThanOrEqualTo("date",timestamp2);

        FirestoreRecyclerOptions<SourcePlanner> options = new FirestoreRecyclerOptions.Builder<SourcePlanner>()
                .setQuery(query, SourcePlanner.class)
                .build();

        Context context;
        context =this;
        adapter = new WeeklyAdapter(options,context);
        weeklyListView.setAdapter(adapter);

    }

    public void dropdownAction(View view) {
        PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), picker);
        dropDownMenu.getMenuInflater().inflate(R.menu.dropdown_menu, dropDownMenu.getMenu());
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dropdown_menu1:
                        startActivity(new Intent(WeekViewActivity.this, MonthlyView.class));
                        return true;
                    default:
                        return WeekViewActivity.super.onOptionsItemSelected(menuItem);
                }
            }
        });
        dropDownMenu.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WeekViewActivity.this, LoginUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
        System.exit(0);
    }
}