package pl.edu.us.warhammer_card.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.table.Kampania;

public class KampaniaAdapter extends RecyclerView.Adapter<KampaniaAdapter.ViewHolder> {

    private List<Kampania> kampaniaList = new ArrayList<>();

    public <E> KampaniaAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nazwaKampanii;
        TextView dataKampanii;

        public ViewHolder(View itemView) {
            super(itemView);
            nazwaKampanii = itemView.findViewById(R.id.nazwa_kampanii);
            dataKampanii = itemView.findViewById(R.id.data_kampanii);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(kampaniaList.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(kampaniaList.get(position));
                        return true;
                    }
                }
                return false;
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kampania_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nazwaKampanii.setText(kampaniaList.get(position).getNazwa());

        long timestamp = kampaniaList.get(position).getDate();
        Date date = new Date(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(date);

        holder.dataKampanii.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return kampaniaList.size();
    }

    public void updateList(List<Kampania> newList) {
        kampaniaList = newList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Kampania kampania);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Kampania kampania);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    public List<Kampania> getKampaniaList() {
        return kampaniaList;
    }
}
