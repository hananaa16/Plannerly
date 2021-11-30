package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import id.ac.umn.test3.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =1;
//    private DatabaseReference reference;
    DocumentReference referenceUser;
    CollectionReference referenceTask;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private ProgressDialog loader;
    TextView title;
    EditText etJudul;
    EditText etDeskripsi;
    EditText etWaktu;
    ImageView pick;
    EditText etAddress;
    Uri resultUri;
    Button btnAdd;
    ArrayList<SourcePlanner> daftarPlan= new ArrayList<>();
    private SourcePlanner selectedPlan;
    private ActivityAddBinding binding;
    AwesomeValidation awesomeValidation;
    public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    String cameraPermission[];
    String storagePermission[];
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private CollectionReference mDatabaseRef;
  Timestamp date;

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

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
//        FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);
        referenceUser = FirebaseFirestore.getInstance().collection("users").document(onlineUserID);
        referenceTask = FirebaseFirestore.getInstance().collection("tasks");

        title = (TextView) findViewById(R.id.titleAdd);
        etJudul = findViewById(R.id.judulAdd);
        etDeskripsi = findViewById(R.id.deskripsiAdd);
        etWaktu = findViewById(R.id.waktuAdd);
        etAddress = findViewById(R.id.addressDesc);
        btnAdd = findViewById(R.id.add);
        Date date2= convertToDateViaInstant(selectedDate);
        date = new Timestamp(date2);
//        date= selectedDate.toString();
//        date= new Timestamp(new Date());
//        Date date2 = new Date();

        mStorageRef = FirebaseStorage.getInstance().getReference("image_uploads");
        mDatabaseRef = FirebaseFirestore.getInstance().collection("image_uploads");

//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("image_uploads");

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
                addPlanner();

            }
        });
    }
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private void addPlanner(){
        title.setText("Add Your New Planner!");
        String key = referenceUser.getId();
        String judul = etJudul.getText().toString();
        String waktu = etWaktu.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String address = etAddress.getText().toString().trim();

        if (awesomeValidation.validate()) {
            loader.setMessage("Adding your data");
            loader.setCanceledOnTouchOutside(false);
            loader.show();
            if (resultUri != null) {
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(resultUri));
                mUploadTask = fileReference.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        SourcePlanner sp;
                                        if(address.equals("")){
                                            sp = new SourcePlanner(key, judul, deskripsi, waktu, date, String.valueOf(task.getResult()));
                                        }else {
                                            sp = new SourcePlanner(key, judul, deskripsi, waktu, date, address, String.valueOf(task.getResult()));
                                        }
                                        referenceTask.add(sp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
                                                    loader.dismiss();
                                                } else {
                                                    String error = task.getException().toString();
                                                    Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
                                                    loader.dismiss();
                                                }

                                            }
                                        });
//                                        referenceTask.set(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
//                                                    loader.dismiss();
//                                                } else {
//                                                    String error = task.getException().toString();
//                                                    Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
//                                                    loader.dismiss();
//                                                }
//                                            }
//                                        });

                                    }
                                });

                            }
                        });
            }
            else {
                SourcePlanner sp;
                if (address.equals("")) {
                    sp = new SourcePlanner(key, judul, deskripsi, waktu, date);
                }else{
                    sp = new SourcePlanner(key, judul, deskripsi, waktu, date, address);
                }
                referenceTask.add(sp).addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        } else {
                            String error = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        }
                    }
                });
            }
        }
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
                resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(pick);
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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

//    private void checkForEditPlan() {
//        Intent previousIntent = getIntent();
//        passedPlanID = previousIntent.getIntExtra(SourcePlanner.NOTE_EDIT_EXTRA, -1);
//        selectedPlan = SourcePlanner.getPlanForID(passedPlanID);
//        if (selectedPlan != null) {
//            title.setText("Edit Your Planner!");
//            etJudul.setText(selectedPlan.getJudul());
//            etDeskripsi.setText(selectedPlan.getDeskripsi());
//            etWaktu.setText(selectedPlan.getTime());
//            etAddress.setText(selectedPlan.getAddress());
//            btnAdd.setText("SAVE");
//        }
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
                startActivity(new Intent(AddActivity.this, LoginUser.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//     btnAdd.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            title.setText("Add Your New Planner!");
//            String judul = etJudul.getText().toString();
//            String waktu = etWaktu.getText().toString();
//            String deskripsi = etDeskripsi.getText().toString();
//            String address = etAddress.getText().toString().trim();
//            if (selectedPlan == null) {
//                if (awesomeValidation.validate()) {
//                    SourcePlanner sp = new SourcePlanner();
//
//                    Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                if (awesomeValidation.validate()) {
//                    selectedPlan.setJudul(judul);
//                    selectedPlan.setTime(waktu);
//                    selectedPlan.setDeskripsi(deskripsi);
//                    selectedPlan.setAddress(address);
////                        dbHelper.updatePlanInDB(selectedPlan);
//                    Toast.makeText(getApplicationContext(), "Your plan has been updated", Toast.LENGTH_LONG).show();
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Failed to update your plan", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    });
}