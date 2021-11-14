package id.ac.umn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditCalendar extends AppCompatActivity {
    private EditText judul, deskripsi, waktu;
    private TextView tanggal;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar);
        judul = (EditText) findViewById(R.id.judulEdit);
        deskripsi = (EditText) findViewById(R.id.deskripsiEdit);
        waktu = (EditText) findViewById(R.id.waktuEdit);
        tanggal = (TextView) findViewById(R.id.tanggalEdit);
        Date c = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        date = dateFormat.format(c);
        tanggal.setText(date);
        Intent intent = getIntent();
//        if (intent.hasExtra("Task")) {
//            judul.setText();
//            deskripsi.setText();
//            waktu.setText();
//        }
    }

    public void editData(View view) {
        String judul2 = judul.getText().toString();
        String deskripsi2 = deskripsi.getText().toString();
        String waktu2 = waktu.getText().toString();
        if (judul2.length() <= 0 || deskripsi2.length() <= 0) {
            Toast.makeText(this, "Judul dan deskripsi wajib diisi", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}