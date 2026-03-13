package pl.edu.us.warhammer_card.adapter;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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

import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.table.Talent;

public class TalentAdapter extends RecyclerView.Adapter<TalentAdapter.ViewHolder> {

    private List<Talent> talentList = new ArrayList<>();

    private OnHelpClickListener onHelpClickListener;
    private OnMinusClickListener onMinusClickListener;
    private OnPlusClickListener onPlusClickListener;

    public <E> TalentAdapter(ArrayList<E> es) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taletLabel;
        TextView maksimum;

        ImageView helpIcon;
        TextView testy;
        TextView opis;
        ImageButton buttonMinus;
        EditText lvl;
        ImageButton buttonPlus;



        public ViewHolder(View itemView) {
            super(itemView);

            taletLabel = itemView.findViewById(R.id.talent_label);
            maksimum = itemView.findViewById(R.id.maksimum);
            helpIcon = itemView.findViewById(R.id.help_icon);
            testy = itemView.findViewById(R.id.testy);
            opis = itemView.findViewById(R.id.opis);

            buttonMinus = itemView.findViewById(R.id.buttonMinus);

            lvl = itemView.findViewById(R.id.lvl);

            buttonPlus = itemView.findViewById(R.id.buttonPlus);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(talentList.get(position));
                        return true;
                    }
                }
                return false;
            });

            helpIcon.setOnClickListener(v ->{
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Talent talent = talentList.get(position);
                    if (onHelpClickListener != null) {
                        onHelpClickListener.onHelpClickListener(talent);
                    }
                }

            });

            buttonMinus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Talent talent = talentList.get(position);
                    if (onMinusClickListener != null) {
                        onMinusClickListener.onMinusClick(talent);
                    }
                }
            });

            buttonPlus.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Talent talent = talentList.get(position);
                    if (onPlusClickListener != null) {
                        onPlusClickListener.onPlusClick(talent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talent_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.taletLabel.setText(talentList.get(position).getNazwa());

        SpannableString maksimumText = new SpannableString("Maksimum: " + talentList.get(position).getMaksimum());
        maksimumText.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.maksimum.setText(maksimumText);


        if (talentList.get(position).getTesty().isEmpty()){
            holder.testy.setText(talentList.get(position).getTesty());

        }else {
            SpannableString testyText = new SpannableString("Testy: " + talentList.get(position).getTesty());
            testyText.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.testy.setText(testyText);
        }

            holder.opis.setText(talentList.get(position).getOpis());

        holder.lvl.setText(String.valueOf(talentList.get(position).getPoziom()));

    }

    @Override
    public int getItemCount() {
        return talentList.size();
    }

    public void updateList(List<Talent> newList){
        talentList = newList;
        notifyDataSetChanged();
    }



    public interface OnHelpClickListener{
        void onHelpClickListener(Talent talent);
    }
    public void setOnHelpClickListener(OnHelpClickListener listener){
        this.onHelpClickListener = listener;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(Talent talent);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<Talent> getTalentList(){
        return talentList;
    }

    public interface OnMinusClickListener{
        void onMinusClick(Talent talent);
    }
    public interface OnPlusClickListener{
        void onPlusClick(Talent talent);
    }
    public void setOnMinusClickListener(OnMinusClickListener listener){
        this.onMinusClickListener = listener;
    }

    public void setOnPlusClickListener(OnPlusClickListener listener){
        this.onPlusClickListener = listener;
    }
}
