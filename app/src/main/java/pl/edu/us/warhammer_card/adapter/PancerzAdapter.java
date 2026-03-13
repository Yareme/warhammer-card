package pl.edu.us.warhammer_card.adapter;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
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
import java.util.stream.Collectors;

import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.LokalizacjaPancerza;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Talent;

public class PancerzAdapter extends RecyclerView.Adapter<PancerzAdapter.ViewHolder> {

    private List<Pancerz> pancerzList = new ArrayList<>();

    private OnInfoClickListener onInfoClickListener;
    private OnItemLongClickListener longClickListener;
    private OnClickListener onClickListener;
    private OnHelpClickListener onHelpClickListener;

    public <E> PancerzAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
                    if (onHelpClickListener != null) {
                        onHelpClickListener.onHelpClickListener(pancerz);
                    }
                }

            });

            czyZalozony.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (onCheckBoxClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Pancerz pancerz = pancerzList.get(position);
                            pancerz.setCzyZalozone(isChecked ? 1 : 0);
                            onCheckBoxClickListener.onCheckBoxClick(pancerz, isChecked);
                            updateList(pancerzList);
                        }, 500);
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


        Pancerz pancerz = pancerzList.get(position);
        holder.nazwa.setText(pancerzList.get(position).getNazwa());
        holder.dostepnosc.setText(pancerzList.get(position).getDastepnosc());


        String listaLokalizacja = pancerzList.get(position).getLokalizacjaPancerzaList().stream()
                .map(LokalizacjaPancerza::getNazwa)
                .collect(Collectors.joining(", "));

        SpannableString lokalizacjaText = new SpannableString("Lokalizacja: " + listaLokalizacja);
        lokalizacjaText.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.lokalizacja.setText(lokalizacjaText);

        SpannableString ppText = new SpannableString("PP: " + pancerzList.get(position).getPunktyPancerza());
        ppText.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.pp.setText(ppText);


        String listaNazwCech = pancerzList.get(position).getCechaPancerzaList().stream()
                .map(CechaPancerza::getNazwa)
                .collect(Collectors.joining(", "));

        SpannableString cechyText = new SpannableString("Cechy: " + listaNazwCech);
        cechyText.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.cechy.setText(cechyText);

        boolean isCheck = (pancerz.getCzyZalozone() == 1);

        holder.czyZalozony.setChecked(isCheck);


    }

    @Override
    public int getItemCount() {
        return pancerzList.size();
    }

    public void updateList(List<Pancerz> newList){
        pancerzList = newList;
        notifyDataSetChanged();
    }

    public interface OnInfoClickListener {
        void onInfoClickListener(Pancerz pancerz);
    }

    public void OnInfoClickListener(OnInfoClickListener listener) {
        this.onInfoClickListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(Pancerz pancerz);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(Pancerz pancerz);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(Pancerz pancerz, boolean isChecked);
    }

    private OnCheckBoxClickListener onCheckBoxClickListener;

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener listener) {
        this.onCheckBoxClickListener = listener;
    }

    public interface OnHelpClickListener{
        void onHelpClickListener(Pancerz pancerz);
    }
    public void setOnHelpClickListener(OnHelpClickListener listener){
        this.onHelpClickListener = listener;
    }

    public List<Pancerz> getPancerzList() {
        return pancerzList;
    }
}
