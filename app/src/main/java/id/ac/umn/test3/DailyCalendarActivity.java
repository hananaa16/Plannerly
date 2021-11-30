package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private String key = "";
    private String task;
    private String description;
    private Uri mImageUri;
    PlanAdapter planAdapter;
    private FirebaseFirestore db;
    private CollectionReference reference;

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
    }

    private void initWidgets() {
        monthDayText = findViewById(R.id.monthDayTV);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        planListView = (RecyclerView) findViewById(R.id.planListView);
        planListView.setLayoutManager(new LinearLayoutManager(this));
        setDayView();
        setPlanAdapter();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private void setPlanAdapter() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        Date date2= convertToDateViaInstant(selectedDate);
        Timestamp timestamp1 = new Timestamp(date2);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("tasks")
                .whereEqualTo("id",onlineUserID)
                .whereEqualTo("date",timestamp1);

        FirestoreRecyclerOptions<SourcePlanner> options = new FirestoreRecyclerOptions.Builder<SourcePlanner>()
        .setQuery(query, SourcePlanner.class)
        .build();

        Context context;
        context =this;
        planAdapter = new PlanAdapter(options,context);
        planListView.setAdapter(planAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        planAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        planAdapter.stopListening();
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
                startActivity(new Intent(DailyCalendarActivity.this, LoginUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}