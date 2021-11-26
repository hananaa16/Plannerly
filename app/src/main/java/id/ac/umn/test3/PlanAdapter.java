package id.ac.umn.test3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ItemVideoViewHolder> {
    private ArrayList<SourcePlanner> mDaftarPlan;
    private LayoutInflater mInflater;
    private Context mContext;

    public PlanAdapter(Context context, ArrayList<SourcePlanner> daftarPlan) {
        this.mContext = context;
        this.mDaftarPlan = daftarPlan;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dailytask_item_layout,
                parent, false);
        return new ItemVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoViewHolder holder, int position) {
        SourcePlanner mPlan = mDaftarPlan.get(position);
        holder.libraryJudul.setText(mPlan.getJudul());
        holder.libraryTime.setText(mPlan.getTime());
        holder.libraryDeskripsi.setText(mPlan.getDeskripsi());
        holder.libraryDate.setText(mPlan.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
        holder.btnDelete.setOnClickListener((view -> {
            deleteFile(position, view);
        }));
    }

    public void deleteFile(int position, View v) {
        DBHelper dbHelper = DBHelper.instanceOfDatabase(mContext);
        dbHelper.deletePlanInDB(mDaftarPlan.get(position));
        mDaftarPlan.remove(position);
        Toast.makeText(v.getContext(), "Plan telah dihapus! ", Toast.LENGTH_LONG).show();
        notifyItemChanged(position);
//        notifyItemRangeChanged(position, mDaftarPlan.size());
    }

    @Override
    public int getItemCount() {
        return mDaftarPlan.size();
    }

    public static class ItemVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView libraryJudul;
        private TextView libraryDeskripsi;
        private TextView libraryTime;
        private TextView libraryDate;
        private int mPosisi;
        private SourcePlanner mSumberPlan;
        private ImageButton btnDelete;

        public ItemVideoViewHolder(View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDeskripsi = (TextView) itemView.findViewById(R.id.tvKetTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteTask);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
