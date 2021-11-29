package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.LinkedList;

import id.ac.umn.test3.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    TextView title;
    EditText etJudul;
    EditText etDeskripsi;
    EditText etWaktu;
    ImageView pick;
    EditText etAddress;
    Button btnAdd;
    ArrayList<SourcePlanner> daftarPlan= new ArrayList<>();
    private SourcePlanner selectedPlan;
    private ActivityAddBinding binding;
    AwesomeValidation awesomeValidation;
    public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    String cameraPermission[];
    String storagePermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(CalendarUtils.monthDayFromDate(selectedDate));
        cameraPermission= new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        title = (TextView) findViewById(R.id.titleAdd);
        etJudul = findViewById(R.id.judulAdd);
        etDeskripsi = findViewById(R.id.deskripsiAdd);
        etWaktu = findViewById(R.id.waktuAdd);
        etAddress = findViewById(R.id.addressDesc);
        btnAdd = findViewById(R.id.add);
        DBHelper dbHelper = DBHelper.instanceOfDatabase(this);
        checkForEditPlan();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.judulAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_judul);
        awesomeValidation.addValidation(this, R.id.deskripsiAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_deskripsi);
        awesomeValidation.addValidation(this, R.id.waktuAdd, "[0-9]{2}:[0-9]{2}$", R.string.invalid_waktu);

        pick=(ImageView)findViewById(R.id.pickImage);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int picd=0;
                if(picd == 0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromGallery();
                    }
                }else if(picd == 1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("Add Your New Planner!");
                int id = daftarPlan.size();
                String judul = etJudul.getText().toString();
                String waktu = etWaktu.getText().toString();
                String deskripsi = etDeskripsi.getText().toString();
                String address = etAddress.getText().toString().trim();
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
                        selectedPlan.setAddress(address);
                        dbHelper.updatePlanInDB(selectedPlan);
                        Toast.makeText(getApplicationContext(), "Your plan has been updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to update your plan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void requestStoragePermission() {
        requestPermissions(storagePermission,STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission,CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(pick);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case CAMERA_REQUEST:{
                if(grantResults.length>0){
                    boolean camera_accepted=grantResults[0]==(PackageManager.PERMISSION_GRANTED);
                    boolean storage_accepted=grantResults[1]==(PackageManager.PERMISSION_GRANTED);
                    if(camera_accepted && storage_accepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Camera & Storage Access Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if(grantResults.length>0){
                    boolean storage_accepted = grantResults[0]==(PackageManager.PERMISSION_GRANTED);
                    if(storage_accepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Storage Access Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void checkForEditPlan() {
        Intent previousIntent = getIntent();
        int passedPlanID = previousIntent.getIntExtra(SourcePlanner.NOTE_EDIT_EXTRA, -1);
        selectedPlan = SourcePlanner.getPlanForID(passedPlanID);
        if (selectedPlan != null) {
            title.setText("Edit Your Planner!");
            etJudul.setText(selectedPlan.getJudul());
            etDeskripsi.setText(selectedPlan.getDeskripsi());
            etWaktu.setText(selectedPlan.getTime());
            etAddress.setText(selectedPlan.getAddress());
            btnAdd.setText("SAVE");
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
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AddActivity.this, LoginUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}