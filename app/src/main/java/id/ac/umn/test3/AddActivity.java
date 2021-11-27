package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.LinkedList;

import id.ac.umn.test3.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    EditText etJudul;
    EditText etDeskripsi;
    EditText etWaktu;
    EditText etMaps;
    Button btnAdd;
    private ImageButton etFoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    PlanAdapter adapter;
    LinkedList<SourcePlanner> daftarMusik= new LinkedList<>();
    private SourcePlanner selectedPlan;
    private ActivityAddBinding binding;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(CalendarUtils.monthDayFromDate(selectedDate));

        etJudul = findViewById(R.id.judulAdd);
        etDeskripsi = findViewById(R.id.deskripsiAdd);
        etWaktu = findViewById(R.id.waktuAdd);
        etFoto = findViewById(R.id.fotoAdd);
        etMaps = findViewById(R.id.gambarMapsAdd);
        btnAdd = findViewById(R.id.add);
        DBHelper dbHelper = DBHelper.instanceOfDatabase(this);
        checkForEditPlan();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.judulAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_judul);
        awesomeValidation.addValidation(this, R.id.deskripsiAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_deskripsi);
        awesomeValidation.addValidation(this, R.id.waktuAdd, "[0-9]{2}:[0-9]{2}$", R.string.invalid_waktu);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = daftarMusik.size();
                String judul = etJudul.getText().toString();
                String waktu = etWaktu.getText().toString();
                String deskripsi = etDeskripsi.getText().toString();
                if (selectedPlan == null) {
                    if (awesomeValidation.validate()) {
                        SourcePlanner sp = new SourcePlanner(id, judul, deskripsi, waktu, selectedDate);
                        SourcePlanner.sourcePlannerArrayList.add(sp);
                        dbHelper.insertPlan(sp);
                        Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (awesomeValidation.validate()) {
                        selectedPlan.setJudul(judul);
                        selectedPlan.setTime(waktu);
                        selectedPlan.setDeskripsi(deskripsi);
                        dbHelper.updatePlanInDB(selectedPlan);
                        Toast.makeText(getApplicationContext(), "Your plan has been updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to update your plan", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent takePictureIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    private void checkForEditPlan() {
        Intent previousIntent = getIntent();
        int passedPlanID = previousIntent.getIntExtra(SourcePlanner.NOTE_EDIT_EXTRA, -1);
        selectedPlan = SourcePlanner.getPlanForID(passedPlanID);
        if (selectedPlan != null) {
            etJudul.setText(selectedPlan.getJudul());
            etDeskripsi.setText(selectedPlan.getDeskripsi());
            etWaktu.setText(selectedPlan.getTime());
        }
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
    @Override
    protected void onActivityResult (int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            etFoto.setImageBitmap(imageBitmap);
        }
    }
}