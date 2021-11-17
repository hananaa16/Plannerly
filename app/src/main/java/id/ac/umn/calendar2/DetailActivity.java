package id.ac.umn.calendar2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.umn.calendar2.databinding.ActivityDetailBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class DetailActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetailBinding binding;
    RecyclerView rvDaftarMusik;
    DailyPlannerAdapter mAdapter;
    LinkedList<SourcePlanner> daftarMusik= new LinkedList<>();
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        isiDaftarMusik();

        rvDaftarMusik = (RecyclerView) findViewById(R.id.rvDailyTask);
        mAdapter = new DailyPlannerAdapter(this, daftarMusik);
        rvDaftarMusik.setAdapter(mAdapter);
        rvDaftarMusik.setLayoutManager(new LinearLayoutManager(this));
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

    public void isiDaftarMusik(){

        daftarMusik.add(new SourcePlanner("Rapat BPH Koor", "Rapat", "PPT","android.resource://" +getPackageName() + "/"+ R.raw.wowsfx));
        daftarMusik.add(new SourcePlanner("Tugas Akhir", "Tugas", "Makalah","android.resource://" +getPackageName() + "/"+ R.raw.kleeboom1));



    }



}