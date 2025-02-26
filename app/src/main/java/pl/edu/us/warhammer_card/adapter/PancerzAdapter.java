package pl.edu.us.warhammer_card.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pl.edu.us.warhammer_card.R;
import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Pancerz;

public class PancerzAdapter extends RecyclerView.Adapter<PancerzAdapter.ViewHolder> {

    private List<Pancerz> pancerzList = new ArrayList<>();

    private OnInfoClickListener onInfoClickListener;


    public <E> PancerzAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nazwa;
        TextView dostepnosc;
        TextView lokalizacja;
        TextView pp;
        TextView cechy;
        CheckBox czyZalozony;
        ImageView info;
        public ViewHolder(View itemView){
            super(itemView);

            nazwa = itemView.findViewById(R.id.nazwa);
            dostepnosc = itemView.findViewById(R.id.dostepnosc);
            lokalizacja = itemView.findViewById(R.id.lokalizacja);
            pp = itemView.findViewById(R.id.pp);
            cechy = itemView.findViewById(R.id.cechy);
            czyZalozony = itemView.findViewById(R.id.czy_zalozony);
            info = itemView.findViewById(R.id.info);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(pancerzList.get(position));
                        return true;
                    }
                }
                return false;
            });

            info.setOnClickListener(v ->{
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Pancerz pancerz = pancerzList.get(position);
                    // Wywołanie metody w listenerze
                    if (onInfoClickListener != null) {
                        onInfoClickListener.onInfoClickListener(pancerz);
                    }
                }

            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pancerz_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nazwa.setText(pancerzList.get(position).getNazwa());
        holder.nazwa.setText(pancerzList.get(position).getNazwa());

        holder.dostepnosc.setText(pancerzList.get(position).getDastepnosc());

        holder.lokalizacja.setText("Dorobić lokalizacje");

        holder.pp.setText(String.valueOf(pancerzList.get(position).getPunktyPancerza()));

        holder.cechy.setText("Dorobić cechy");

        boolean chek = true;
        if (pancerzList.get(position).getCzyZalozone() == 0)
            chek = false;
        holder.czyZalozony.setChecked(chek);
    }

    @Override
    public int getItemCount() {
        return pancerzList.size();
    }

    public void updateList(List<Pancerz> newList){
        pancerzList = newList;
        notifyDataSetChanged();
    }

    public interface OnInfoClickListener{
        void onInfoClickListener(Pancerz pancerz);
    }
    public void OnInfoClickListener(OnInfoClickListener listener){
        this.onInfoClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Pancerz pancerz);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<Pancerz> getPancerzList(){return pancerzList;}
}
