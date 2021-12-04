package id.ac.umn.plannerly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WeeklyAdapter extends FirestoreRecyclerAdapter<SourcePlanner, WeeklyAdapter.ItemVideoViewholder> {
    Context context2;
    DateFormat formatter;
    String formattedDate;

    public WeeklyAdapter(@NonNull FirestoreRecyclerOptions<SourcePlanner> options, Context context) {
        super(options);
        this.context2 = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull WeeklyAdapter.ItemVideoViewholder holder, int position, @NonNull SourcePlanner model) {
        holder.libraryJudul.setText(model.getJudul());
        holder.libraryTime.setText(model.getTime());
        formatter = new SimpleDateFormat("d MMMM yyyy");
        formattedDate = formatter.format(model.getDate().toDate());
        holder.libraryDate.setText(formattedDate);
        Picasso.with(context2)
                .load(model.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.libraryImage);
    }

    @NonNull
    @Override
    public WeeklyAdapter.ItemVideoViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weeklytask_item_layout, parent, false);
        return new WeeklyAdapter.ItemVideoViewholder(view);
    }

    public class ItemVideoViewholder extends RecyclerView.ViewHolder {
        private TextView libraryJudul;
        private TextView libraryTime;
        private TextView libraryDate;
        private ImageView libraryImage;

        public ItemVideoViewholder(@NonNull View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
            libraryImage= itemView.findViewById(R.id.ivGambarTask);
        }
    }


}
