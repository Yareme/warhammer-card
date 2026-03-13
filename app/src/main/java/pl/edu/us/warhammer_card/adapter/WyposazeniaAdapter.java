package pl.edu.us.warhammer_card.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.us.warhammer_card.R;


import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Wyposarzenia;

public class WyposazeniaAdapter  extends RecyclerView.Adapter<WyposazeniaAdapter.ViewHolder> {

    private List<Wyposarzenia> wyposarzeniaList = new ArrayList<>();
    private OnItemLongClickListener longClickListener;
    private  OnMinusClickListener onMinusClickListener;
    private  OnPlusClickListener onPlusClickListener;
    private OnItemClickListener onItemClickListener;
    private TextChangedListener textChangedListener;

    public <E> WyposazeniaAdapter(ArrayList<E> es){

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nazwa;
        ImageButton buttonMinus;
        EditText ilosc;
        ImageButton buttonPlus;

        public ViewHolder(View itemView) {
            super(itemView);

            nazwa = itemView.findViewById(R.id.nazwa);

            buttonMinus = itemView.findViewById(R.id.buttonMinus);

            ilosc = itemView.findViewById(R.id.ilosc);

            buttonPlus = itemView.findViewById(R.id.buttonPlus);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(wyposarzeniaList.get(position));
                        return true;
                    }
                }
                return false;
            });

            buttonMinus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Wyposarzenia wyposarzenia = wyposarzeniaList.get(position);
                    if (onMinusClickListener != null) {
                        onMinusClickListener.onMinusClick(wyposarzenia);
                    }
                }
            });

            buttonPlus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Wyposarzenia wyposarzenia = wyposarzeniaList.get(position);
                    if (onPlusClickListener != null) {
                        onPlusClickListener.onPlusClick(wyposarzenia);
                    }
                }
            });

            ilosc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Wyposarzenia wyposarzenia = wyposarzeniaList.get(position);

                        try {
                            int newValue = Integer.parseInt(s.toString());
                            if (wyposarzenia.getSztuk() != newValue) {
                                wyposarzenia.setSztuk(newValue);

                                if (textChangedListener != null) {
                                    textChangedListener.textChanged(wyposarzenia);
                                }
                            }
                        } catch (NumberFormatException e) {
                            ilosc.setText(String.valueOf(wyposarzenia.getSztuk()));
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wyposarzenie_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wyposarzenia wyposarzenia = wyposarzeniaList.get(position);
        holder.nazwa.setText(wyposarzeniaList.get(position).getNazwa());
        holder.ilosc.setText(String.valueOf(wyposarzeniaList.get(position).getSztuk()));


        holder.buttonMinus.setOnClickListener(v -> {
            if (onMinusClickListener != null) {
                onMinusClickListener.onMinusClick(wyposarzenia);
            }
        });

        holder.buttonPlus.setOnClickListener(v -> {
            if (onPlusClickListener != null) {
                onPlusClickListener.onPlusClick(wyposarzenia);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wyposarzeniaList.size();
    }

    public void updateList(List<Wyposarzenia> newList) {
        wyposarzeniaList = newList;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(Wyposarzenia wyposarzenia);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(Wyposarzenia wyposarzenia);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<Wyposarzenia> getWyposarzeniaList(){
        return wyposarzeniaList;
    }

    public interface OnMinusClickListener{
        void onMinusClick(Wyposarzenia wyposarzenia);
    }
    public interface OnPlusClickListener{
        void onPlusClick(Wyposarzenia wyposarzenia);
    }
    public void setOnMinusClickListener(OnMinusClickListener listener){
        this.onMinusClickListener = listener;
    }

    public void setOnPlusClickListener(OnPlusClickListener listener){
        this.onPlusClickListener = listener;
    }

    public interface TextChangedListener{
        void textChanged(Wyposarzenia wyposarzenia);
    }

    public void setTextChangedListener(TextChangedListener listener){
        this.textChangedListener = listener;
    }
}
