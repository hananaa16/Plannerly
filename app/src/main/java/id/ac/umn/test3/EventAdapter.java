package id.ac.umn.test3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class EventAdapter extends FirestoreRecyclerAdapter<SourcePlanner, EventAdapter.ItemVideoViewholder> {
    Context context2;
    DateFormat formatter;
    String formattedDate;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<SourcePlanner> options, Context context) {
        super(options);
        this.context2 = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventAdapter.ItemVideoViewholder holder, int position, @NonNull SourcePlanner model) {
        holder.libraryJudul.setText(model.getJudul());
        holder.libraryTime.setText(model.getTime());
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = formatter.format(model.getDate().toDate());
        holder.libraryDate.setText(formattedDate);

    }

    @NonNull
    @Override
    public EventAdapter.ItemVideoViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weeklytask_item_layout, parent, false);

        return new EventAdapter.ItemVideoViewholder(view);
    }

    public class ItemVideoViewholder extends RecyclerView.ViewHolder {
        private TextView libraryJudul;
        private TextView libraryTime;
        private TextView libraryDate;

        public ItemVideoViewholder(@NonNull View itemView) {
            super(itemView);
            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);

        }

    }
}


//public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ItemVideoViewHolder> {
//    private ArrayList<SourcePlanner> mDaftarPlan;
//    private LayoutInflater mInflater;
//    private Context mContext;
//
//    public EventAdapter(Context context, ArrayList<SourcePlanner> daftarPlan) {
//        this.mContext = context;
//        this.mDaftarPlan = daftarPlan;
//        this.mInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public ItemVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.weeklytask_item_layout,
//                parent, false);
//        return new ItemVideoViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemVideoViewHolder holder, int position) {
//        SourcePlanner mPlan = mDaftarPlan.get(position);
//        holder.libraryJudul.setText(mPlan.getJudul());
//        holder.libraryTime.setText(mPlan.getTime());
//        holder.libraryDate.setText(mPlan.getDate().toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDaftarPlan.size();
//    }
//
//    public static class ItemVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView libraryJudul;
//        private TextView libraryTime;
//        private TextView libraryDate;
//
//        public ItemVideoViewHolder(View itemView) {
//            super(itemView);
//            libraryJudul = (TextView) itemView.findViewById(R.id.tvJudulTask);
//            libraryTime = (TextView) itemView.findViewById(R.id.tvWaktuTask);
//            libraryDate = (TextView) itemView.findViewById(R.id.tvTanggalTask);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//        }
//    }
//}
