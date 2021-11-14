package id.ac.umn.plannery1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class AddPlannery extends AppCompatActivity {
    EditText etJudul, etDeskripsi, etWaktu, etFoto, etMaps;
    Button btAdd;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_planner);

        etJudul = findViewById(R.id.judulAdd);
        etDeskripsi = findViewById(R.id.deskripsiAdd);
        etWaktu = findViewById(R.id.waktuAdd);
        etFoto = findViewById(R.id.fotoAdd);
        etMaps = findViewById(R.id.gambarMapsAdd);
        btAdd = findViewById(R.id.add);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.judulAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_judul);
        awesomeValidation.addValidation(this, R.id.deskripsiAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_deskripsi);
        awesomeValidation.addValidation(this, R.id.waktuAdd, "[5-9]{1}[0-9]{9}$", R.string.invalid_waktu);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(), "Your plan is successfully added.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed to add your plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}