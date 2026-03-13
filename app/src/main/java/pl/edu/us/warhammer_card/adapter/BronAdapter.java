package pl.edu.us.warhammer_card.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaBroni;

public class BronAdapter extends RecyclerView.Adapter<BronAdapter.ViewHolder> {
    private List<Bron> bronList = new ArrayList<>();

    private OnHelpClickListener onHelpClickListener;

    public <E> BronAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nazwa;
        TextView dostepnosc;
        TextView kategoria;
        TextView zasieg;
        TextView obrazenia;
        TextView cechy;
        ImageView info;

        public ViewHolder(View itemView) {
            super(itemView);

            nazwa = itemView.findViewById(R.id.nazwa);
            dostepnosc = itemView.findViewById(R.id.dostepnosc);
            kategoria = itemView.findViewById(R.id.kategoria);

            zasieg = itemView.findViewById(R.id.zasieg);
            cechy = itemView.findViewById(R.id.cechy);
            obrazenia = itemView.findViewById(R.id.obrazenia);


            info = itemView.findViewById(R.id.info);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(bronList.get(position));
                        return true;
                    }
                }
                return false;
            });

            info.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bron bron = bronList.get(position);
                    if (onHelpClickListener != null) {
                        onHelpClickListener.onHelpClickListener(bron);
                    }
                }

            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bron_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nazwa.setText(bronList.get(position).getNazwa());
        holder.dostepnosc.setText(bronList.get(position).getDostemnoscText());

        SpannableString kategoriaText = new SpannableString("Kategoria:" + bronList.get(position).getTypBroni().getNazwa());
        kategoriaText.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString zasiegText = new SpannableString("Zasięg: " + bronList.get(position).getZasieg());
        zasiegText.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString obrazeniaText = new SpannableString("Obrażenia: " + bronList.get(position).getObrazenia());
        obrazeniaText.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String listaNazwCech = bronList.get(position).getListaCech().stream()
                .map(CechaBroni::getNazwa)
                .collect(Collectors.joining(", "));

        SpannableString cechyTextn = new SpannableString("Cechy: " + listaNazwCech);
        cechyTextn.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.kategoria.setText(kategoriaText);
        holder.cechy.setText(cechyTextn);
        holder.zasieg.setText(zasiegText);
        holder.obrazenia.setText(obrazeniaText);
    }

    @Override
    public int getItemCount() {
        return bronList.size();
    }

    public void updateList(List<Bron> newList) {
        bronList = newList;
        notifyDataSetChanged();
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(Bron bron);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    public interface OnHelpClickListener {
        void onHelpClickListener(Bron bron);
    }
    public void setOnHelpClickListener(OnHelpClickListener listener) {
        this.onHelpClickListener = listener;
    }
    public List<Bron> getBronList() {
        return bronList;
    }
}
