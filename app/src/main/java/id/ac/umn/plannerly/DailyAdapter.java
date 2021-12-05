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
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DailyAdapter extends FirestoreRecyclerAdapter<SourcePlanner, DailyAdapter.ItemVideoViewholder> {
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
    String date2;
    TextView title;
    DateFormat formatter;
    String formattedDate;
    String docname;
    ArrayList<SourcePlanner> sp;

    public DailyAdapter(@NonNull FirestoreRecyclerOptions<SourcePlanner> options, Context context) {
        super(options);
        this.context2 = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ItemVideoViewholder holder, int position, @NonNull SourcePlanner model) {
        Picasso.with(context2)
                .load(model.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.libraryImage);
        holder.libraryJudul.setText(model.getJudul());
        holder.libraryTime.setText(model.getTime());
        holder.libraryDeskripsi.setText(model.getDeskripsi());
        holder.libraryAddress.setText(model.getAddress());

        formatter = new SimpleDateFormat("d MMMM yyyy");
        formattedDate = formatter.format(model.getDate().toDate());
        holder.libraryDate.setText(formattedDate);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference = FirebaseFirestore.getInstance().collection("tasks");

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile(holder.getAdapterPosition());
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = model.getId();
                String judul = model.getJudul();
                String deskripsi = model.getDeskripsi();
                String address = model.getAddress();
                Timestamp date = model.getDate();
                String date2 = date.toDate().toString();
                String waktu = model.getTime();
                String imageUrl = model.getImageURL();
                updateFile(holder.getAdapterPosition(),key,judul,deskripsi,address,date2,waktu,imageUrl);
            }
        });
    }
    public void deleteFile(int position){
        getSnapshots().getSnapshot(position).getReference().delete()
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

    public void updateFile(int position,String key,String judul,String deskripsi, String address, String date2, String waktu,String imageUrl){
        String docname=getSnapshots().getSnapshot(position).getId();
        Intent edit = new Intent(context2, AddEditActivity.class);
        edit.putExtra("keyupdate",key);
        edit.putExtra("docname",docname);
        edit.putExtra("judulupdate",judul);
        edit.putExtra("addressupdate",address);
        edit.putExtra("dateupdate",date2);
        edit.putExtra("waktuupdate",waktu);
        edit.putExtra("imageUrlupdate",imageUrl);
        edit.putExtra("deskripsiupdate",deskripsi);
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
        private TextView libraryAddress;
        private ImageView libraryImage;
        private ImageButton btnDelete;
        private ImageButton btnEdit;
        public ItemVideoViewholder(@NonNull View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDeskripsi = (TextView) itemView.findViewById(R.id.tvKetTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
            libraryAddress = (TextView) itemView.findViewById(R.id.tvLocationTask);
            libraryImage= itemView.findViewById(R.id.ivGambarTask);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteTask);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEditTask);
        }
    }
}
