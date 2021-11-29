package id.ac.umn.test3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ItemVideoViewHolder> {
    private ArrayList<SourcePlanner> mDaftarPlan;
    private LayoutInflater mInflater;
    private Context mContext;

    public EventAdapter(Context context, ArrayList<SourcePlanner> daftarPlan) {
        this.mContext = context;
        this.mDaftarPlan = daftarPlan;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.weeklytask_item_layout,
                parent, false);
        return new ItemVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoViewHolder holder, int position) {
        SourcePlanner mPlan = mDaftarPlan.get(position);
        holder.libraryJudul.setText(mPlan.getJudul());
        holder.libraryTime.setText(mPlan.getTime());
        holder.libraryDate.setText(mPlan.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
    }

    @Override
    public int getItemCount() {
        return mDaftarPlan.size();
    }

    public static class ItemVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView libraryJudul;
        private TextView libraryTime;
        private TextView libraryDate;

        public ItemVideoViewHolder(View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
