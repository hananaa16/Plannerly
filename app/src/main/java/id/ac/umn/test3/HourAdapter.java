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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Source;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ItemVideoViewHolder> {
    private LinkedList<SourcePlanner> mDaftarMusik;
    private LayoutInflater mInflater;
    private Context mContext;

    public HourAdapter(Context context, LinkedList<SourcePlanner> daftarMusik) {
        this.mContext = context;
        this.mDaftarMusik = daftarMusik;
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
        SourcePlanner mSumberMusik = mDaftarMusik.get(position);
        holder.libraryJudul.setText(mSumberMusik.getJudul());
        holder.libraryKategori.setText(mSumberMusik.getKategori());
        holder.libraryDeskripsi.setText(mSumberMusik.getDeskripsi());
//        holder.btnDelete.setOnClickListener((view -> {
//            deleteFile(position, view);
//        }));
    }

    public void deleteFile(int position, View v) {
        mDaftarMusik.remove(position);
        Toast.makeText(v.getContext(), "Lagu telah dihapus! ", Toast.LENGTH_LONG).show();
        notifyItemChanged(position);
        notifyItemRangeChanged(position, mDaftarMusik.size());
    }

    @Override
    public int getItemCount() {
        if (mDaftarMusik != null) {
            return mDaftarMusik.size();
        } else {
            return 0;
        }
    }

    public static class ItemVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView libraryJudul;
        private TextView libraryKategori;
        private TextView libraryDeskripsi;
        private int mPosisi;
        private SourcePlanner mSumberMusik;
        private ImageButton btnDelete;

        public ItemVideoViewHolder(View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryKategori = (TextView) itemView.findViewById(R.id.tvLokasiTask);
            libraryDeskripsi = (TextView) itemView.findViewById(R.id.tvKetTask);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteTask);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
