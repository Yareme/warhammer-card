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
import pl.edu.us.warhammer_card.table.Zaklecia;


public class ZakleciAdapter extends RecyclerView.Adapter<ZakleciAdapter.ViewHolder> {

    private List<Zaklecia> zakleciaList = new ArrayList<>();

    public <E> ZakleciAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nazwa;
        TextView pz;

        TextView zasieg;
        TextView cel;
        TextView czas;
        TextView opis;


        public ViewHolder(View itemView) {
            super(itemView);

            nazwa = itemView.findViewById(R.id.nazwa);
            pz = itemView.findViewById(R.id.pz);
            zasieg = itemView.findViewById(R.id.zasieg);
            cel = itemView.findViewById(R.id.cel);
            czas = itemView.findViewById(R.id.czas);
            opis = itemView.findViewById(R.id.opis);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(zakleciaList.get(position));
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.czar_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nazwa.setText(zakleciaList.get(position).getNazwa());

        SpannableString pzText = new SpannableString("PZ: " + zakleciaList.get(position).getPoziomZaklecie());
        pzText.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString zasiegText = new SpannableString("Zasięg: " + zakleciaList.get(position).getZacieg());
        zasiegText.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString celText = new SpannableString("Cel: " + zakleciaList.get(position).getCel());
        celText.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString czasText = new SpannableString("Czas: " + zakleciaList.get(position).getCzas());
        czasText.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



        holder.pz.setText(pzText);
        holder.zasieg.setText(zasiegText);
        holder.cel.setText(celText);
        holder.czas.setText(czasText);
        holder.opis.setText(String.valueOf(zakleciaList.get(position).getOpis()));


    }

    @Override
    public int getItemCount() {
        return zakleciaList.size();
    }

    public void updateList(List<Zaklecia> newList) {
        zakleciaList = newList;
        notifyDataSetChanged();
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(Zaklecia zaklecia);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<Zaklecia> getBronList() {
        return zakleciaList;
    }
}
