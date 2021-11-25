package id.ac.umn.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ItemVideoViewHolder> {
    private LinkedList<SourcePlanner> mDaftarMusik;
    private LayoutInflater mInflater;
    private Context mContext;

    public WeeklyAdapter(Context context, LinkedList<SourcePlanner> daftarMusik) {
        this.mContext = context;
        this.mDaftarMusik = daftarMusik;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.weeklyview_layout,
                parent, false);
        return new ItemVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoViewHolder holder, int position) {
        SourcePlanner mSumberMusik = mDaftarMusik.get(position);
        holder.libraryJudul.setText(mSumberMusik.getJudul());
        holder.libraryKategori.setText(mSumberMusik.getKategori());
        holder.libraryTanggal.setText(mSumberMusik.getTanggal());
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
        private TextView libraryTanggal;
        private int mPosisi;
        private SourcePlanner mSumberMusik;
        private ImageButton btnDelete;

        public ItemVideoViewHolder(View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryKategori = (TextView) itemView.findViewById(R.id.tvKetTask);
            libraryTanggal = (TextView) itemView.findViewById(R.id.tvTanggalTask);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
