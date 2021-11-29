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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private RecyclerView recyclerView;
    private String key = "";
    private String task;
    private String description;
    private Uri mImageUri;

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
//        initWidgets();
//        loadFromDBToMemory();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
//        dailyEvents.clear();
////        loadFromDBToMemory();
//        setDayView();
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
                startActivity(new Intent(DailyCalendarActivity.this, LoginUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}