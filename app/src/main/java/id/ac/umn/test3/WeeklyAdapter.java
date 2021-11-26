package id.ac.umn.test3;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Source;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ItemVideoViewHolder> {
    private ArrayList<SourcePlanner> mDaftarPlan;
    private LayoutInflater mInflater;
    private Context mContext;

    public WeeklyAdapter(Context context, ArrayList<SourcePlanner> daftarPlan) {
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
        holder.libraryDeskripsi.setText(mPlan.getDeskripsi());
        holder.libraryDate.setText(mPlan.getDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")));
        holder.btnDelete.setOnClickListener((view -> {
            deleteFile(position, view);
            DBHelper dbHelper = DBHelper.instanceOfDatabase(mContext);
            dbHelper.updatePlanInDB(mPlan);
        }));
    }

    public void deleteFile(int position, View v) {
        mDaftarPlan.remove(position);
        Toast.makeText(v.getContext(), "Plan telah dihapus! ", Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
//        notifyItemChanged(position);
//        notifyItemRangeChanged(position, mDaftarPlan.size()-1);
    }

    @Override
    public int getItemCount() {
        if (mDaftarPlan != null) {
            return mDaftarPlan.size();
        } else {
            return 0;
        }
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

