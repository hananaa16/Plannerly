package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.daysInWeekArray;
import static id.ac.umn.test3.CalendarUtils.monthYearFromDate;
import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.function.Predicate;

import id.ac.umn.test3.databinding.ActivityDailyCalendarBinding;
import id.ac.umn.test3.databinding.ActivityWeekViewBinding;

public class WeekViewActivity extends AppCompatActivity {
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    RecyclerView planListView;
    ArrayList<SourcePlanner> dailyEvents = SourcePlanner.sourcePlannerArrayList;
    private ActivityDailyCalendarBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private RecyclerView recyclerView;
    private String key = "";
    private String task;
    private String description;
    private Uri mImageUri;
    EventAdapter planAdapter;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private Button picker;

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
                startActivity(new Intent(WeekViewActivity.this, AddActivity.class));
            }
        });
        initWidgets();

    }

    private void initWidgets() {
/*        monthYearText = findViewById(R.id.monthYearTV);*/
        picker = findViewById(R.id.picker);
//        planListView = findViewById(R.id.weeklyRecyclerView);
        planListView = (RecyclerView) findViewById(R.id.planListView);
        planListView.setLayoutManager(new LinearLayoutManager(this));
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
        Date date3= convertToDateViaInstant(selectedDate.plusDays(8));
        Timestamp timestamp1 = new Timestamp(date2);
        Timestamp timestamp2 = new Timestamp(date3);
//        Timestamp timestamp = new Timestamp(new Date());

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
        planAdapter = new EventAdapter(options,context);
        planListView.setAdapter(planAdapter);

        //        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);
//        FirebaseRecyclerOptions<SourcePlanner> options = new FirebaseRecyclerOptions.Builder<SourcePlanner>()
//        .setQuery(reference.orderByChild("date").equalTo(selectedDate.toString()), SourcePlanner.class)
//        .build();

//        Predicate<SourcePlanner> condition = dailyEvent -> !LocalDate.parse(dailyEvent.getDate()).equals(selectedDate);
//        dailyEvents.removeIf(condition);
        //        FirebaseRecyclerOptions<SourcePlanner> options = new FirebaseRecyclerOptions.Builder<SourcePlanner>()
//                .setQuery(reference, SourcePlanner.class)
//                .build();

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
//        dailyEvents.clear();
////        loadFromDBToMemory();

    }

    private void setDayView() {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setPlanAdapter();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerOptions<SourcePlanner> options = new FirebaseRecyclerOptions.Builder<SourcePlanner>()
//                .setQuery(reference, SourcePlanner.class)
//                .build();
//        Context context;
//        context = this;
//        FirebaseRecyclerAdapter<SourcePlanner, MyViewHolder> adapter = new FirebaseRecyclerAdapter<SourcePlanner, MyViewHolder>(options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final SourcePlanner model) {
//                holder.setDate(model.getDate());
//                holder.setTask(model.getJudul());
//                holder.setDesc(model.getDeskripsi());
//
//                Picasso.with(context)
//                        .load(model.getImageURL())
//                        .fit()
//                        .centerCrop()
//                        .into(holder.imageView);
//
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        key = getRef(position).getKey();
//                        task = model.getJudul();
//                        description = model.getDate();
//
////                        updateTask();
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailytask_item_layout, parent, false);
//                return new MyViewHolder(view);
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        View mView;
//        ImageView imageView;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mView = itemView;
//            imageView = itemView.findViewById(R.id.ivGambarTask);
//        }
//
//        public void setTask(String task) {
//            TextView taskTectView = mView.findViewById(R.id.tvJudulTask);
//            taskTectView.setText(task);
//        }
//
//        public void setDesc(String desc) {
//            TextView descTectView = mView.findViewById(R.id.tvKetTask);
//            descTectView.setText(desc);
//        }
//
//        public void setDate(String date) {
//            TextView dateTextView = mView.findViewById(R.id.tvTanggalTask);
//        }
//
//    }


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
}
//public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
//    private TextView monthYearText;
//    private RecyclerView weeklyListView;
//    ArrayList<SourcePlanner> dailyEvents = SourcePlanner.sourcePlannerArrayList;
//    private Button picker;
//    private ActivityWeekViewBinding binding;
//    private FirebaseAuth mAuth;
//    private FirebaseUser mUser;
//    private String onlineUserID;
//    EventAdapter eventAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityWeekViewBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        setSupportActionBar(binding.toolbar);
//        initWidgets();
//    }
//
//    private void initWidgets() {
//        monthYearText = findViewById(R.id.monthYearTV);
//        weeklyListView = findViewById(R.id.weeklyRecyclerView);
//        picker = findViewById(R.id.picker);
//        weeklyListView.setLayoutManager(new LinearLayoutManager(this));
//        setEventAdapter();
//    }
//
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
////        dailyEvents.clear();
////        loadFromDBToMemory();
//        setEventAdapter();
//    }
//    public Date convertToDateViaInstant(LocalDate dateToConvert) {
//        return java.util.Date.from(dateToConvert.atStartOfDay()
//                .atZone(ZoneId.systemDefault())
//                .toInstant());
//    }
//
//
//    private void setEventAdapter() {
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        onlineUserID = mUser.getUid();
//
//        Date date2= convertToDateViaInstant(selectedDate);
//        Date date3= convertToDateViaInstant(selectedDate.plusDays(8));
//        Timestamp timestamp1 = new Timestamp(date2);
//        Timestamp timestamp2 = new Timestamp(date3);
//
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        Query query = rootRef.collection("tasks")
//                .whereEqualTo("id",onlineUserID);
////                .whereGreaterThanOrEqualTo("date",timestamp1)
////                .whereLessThanOrEqualTo("date",timestamp2);
//
//        FirestoreRecyclerOptions<SourcePlanner> options = new FirestoreRecyclerOptions.Builder<SourcePlanner>()
//                .setQuery(query, SourcePlanner.class)
//                .build();
//
//        Context context;
//        context =this;
//        eventAdapter = new EventAdapter(options,context);
//        weeklyListView.setAdapter(eventAdapter);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        eventAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        eventAdapter.stopListening();
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
//}