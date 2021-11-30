package id.ac.umn.test3;

import static id.ac.umn.test3.CalendarUtils.selectedDate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlanAdapter extends FirestoreRecyclerAdapter<SourcePlanner, PlanAdapter.ItemVideoViewholder> {
    Context context2;
    private String key = "";
    private String judul, deskripsi, waktu, address;
    private Timestamp date;
    private String imageUrl;
    private Uri imageUri;
    private CollectionReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    TextView title;
    DateFormat formatter;
    String formattedDate;

    public PlanAdapter(@NonNull FirestoreRecyclerOptions<SourcePlanner> options, Context context) {
        super(options);
        this.context2 = context;

    }


    @Override
    protected void onBindViewHolder(@NonNull ItemVideoViewholder holder, int position, @NonNull SourcePlanner model) {
        holder.libraryJudul.setText(model.getJudul());
        holder.libraryTime.setText(model.getTime());
        holder.libraryDeskripsi.setText(model.getDeskripsi());

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = formatter.format(model.getDate().toDate());

        holder.libraryDate.setText(formattedDate);

        Picasso.with(context2)
                .load(model.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.libraryImage);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseFirestore.getInstance().collection("tasks");

        key = getSnapshots().getSnapshot(position).getId();
        judul = model.getJudul();
        deskripsi = model.getDeskripsi();
        address = model.getAddress();
        date = model.getDate();
        waktu = model.getTime();
        imageUrl = model.getImageURL();

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteFile(key);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updateFile(key,reference);
            }
        });

    }

    public void deleteFile(String key){
        reference.document(key)
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context2, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        String err = task.getException().toString();
                        Toast.makeText(context2, "Failed to delete task "+ err, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void updateFile(String key, String judul, String deskripsi, String address, String waktu, Timestamp date, String imageUrl){
        Intent edit = new Intent(context2, AddActivity.class);
        context2.startActivity(edit);
    }

    @NonNull
    @Override
    public ItemVideoViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailytask_item_layout, parent, false);
        return new ItemVideoViewholder(view);
    }

    public class ItemVideoViewholder extends RecyclerView.ViewHolder {
        private TextView libraryJudul;
        private TextView libraryDeskripsi;
        private TextView libraryTime;
        private TextView libraryDate;
        private ImageView libraryImage;
        private ImageButton btnDelete;
        private ImageButton btnEdit;
        public ItemVideoViewholder(@NonNull View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDeskripsi = (TextView) itemView.findViewById(R.id.tvKetTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
            libraryImage= itemView.findViewById(R.id.ivGambarTask);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteTask);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEditTask);


        }
    }
}

//public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ItemVideoViewHolder> {
//    private ArrayList<SourcePlanner> mDaftarPlan;
//    private LayoutInflater mInflater;
//    private Context mContext;
//
//    public PlanAdapter(Context context, ArrayList<SourcePlanner> daftarPlan) {
//        this.mContext = context;
//        this.mDaftarPlan = daftarPlan;
//        this.mInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public ItemVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.dailytask_item_layout,
//                parent, false);
//        return new ItemVideoViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemVideoViewHolder holder, int position) {
//        SourcePlanner mPlan = mDaftarPlan.get(position);
//        holder.libraryJudul.setText(mPlan.getJudul());
//        holder.libraryTime.setText(mPlan.getTime());
//        holder.libraryDeskripsi.setText(mPlan.getDeskripsi());
//        holder.libraryDate.setText(mPlan.getDate());
//        holder.btnDelete.setOnClickListener((view -> {
//            deleteFile(position, view);
//        }));
//        holder.btnEdit.setOnClickListener((view -> {
//            editPlan(position);
//        }));
//    }
//
//    private void editPlan(int position) {
////        SourcePlanner selectedPlan = mDaftarPlan.get(position);
////        Intent edit = new Intent(mContext, AddActivity.class);
////        edit.putExtra(SourcePlanner.NOTE_EDIT_EXTRA, selectedPlan.getId());
////        mContext.startActivity(edit);
//    }
//
//    public void deleteFile(int position, View v) {
////        DBHelper dbHelper = DBHelper.instanceOfDatabase(mContext);
////        dbHelper.deletePlanInDB(mDaftarPlan.get(position));
////        mDaftarPlan.remove(position);
////        Toast.makeText(v.getContext(), "Plan has successfully been deleted!", Toast.LENGTH_LONG).show();
////        notifyItemChanged(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDaftarPlan.size();
//    }
//
//    public static class ItemVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView libraryJudul;
//        private TextView libraryDeskripsi;
//        private TextView libraryTime;
//        private TextView libraryDate;
//        private ImageButton btnDelete;
//        private ImageButton btnEdit;
//
//        public ItemVideoViewHolder(View itemView) {
//            super(itemView);
//            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
//            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
//            libraryDeskripsi = (TextView) itemView.findViewById(R.id.tvKetTask);
//            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
//            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteTask);
//            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEditTask);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
//}
