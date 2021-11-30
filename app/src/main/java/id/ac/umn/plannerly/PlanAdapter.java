package id.ac.umn.plannerly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    ArrayList<SourcePlanner> sp;

    public PlanAdapter(@NonNull FirestoreRecyclerOptions<SourcePlanner> options, Context context) {
        super(options);
        this.context2 = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ItemVideoViewholder holder, int position, @NonNull SourcePlanner model) {
        holder.libraryJudul.setText(model.getJudul());
        holder.libraryTime.setText(model.getTime());
        holder.libraryDeskripsi.setText(model.getDeskripsi());

        formatter = new SimpleDateFormat("d MMMM yyyy");
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
                updateFile(key);
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
                        Toast.makeText(context2, "Plan deleted successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        String err = task.getException().toString();
                        Toast.makeText(context2, "Failed to delete plan"+ err, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void updateFile(String key){
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
