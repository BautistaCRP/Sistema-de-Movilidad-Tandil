package bautistacarpintero.sistemamovilidadtandil.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;
import bautistacarpintero.sistemamovilidadtandil.R;

public class CardAdapter extends android.support.v7.widget.RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private String TAG = "CardAdapter";

    private CardViewerUser user;
    private ArrayList<Card> dataset;
    private boolean inActionMode;
    private int highlight;

    public CardAdapter(CardViewerUser user) {
        this.user = user;
        dataset = new ArrayList<>();
        highlight = -1;
        inActionMode = false;
    }

    public void startActionMode(){
        inActionMode = true;
        notifyDataSetChanged();
    }

    public void endActionMode(){
        inActionMode = false;
        notifyDataSetChanged();
    }

    public Card getCardByPosition(int position){
        return dataset.get(position);
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.ViewHolder holder, final int position) {
        holder.textViewName.setText(dataset.get(position).getName());
        holder.textViewCardNumber.setText(dataset.get(position).getNumber());
        holder.textViewSaldo.setText("Saldo: "+dataset.get(position).getSaldo());
        holder.stopProgress();
        String viajes = dataset.get(position).getViajes();
        if (!viajes.equals(" "))
            holder.textViewViajes.setText("Viajes: "+viajes);
        else
            holder.textViewViajes.setText("");
        holder.textViewLastUpdate.setText(dataset.get(position).getLastUpdate());

        if ((position == highlight) && (inActionMode)) {
                holder.itemView.setBackgroundColor(Color.parseColor("#e0e0e0"));
        }
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));


        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inActionMode) {
                    user.updateButtonTriggered(position);
                    holder.startProgress();
                }
            }
        });

        holder.parkingCarButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.parkingCarButtomTriggered(position);
            }
        });

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inActionMode) {
                    if (holder.progressBar.getVisibility() == View.VISIBLE)
                        holder.stopProgress();
                    else
                        user.startMovementActivity(dataset.get(position));
                }
            }
        });

        holder.rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inActionMode = true;
                highlight = position;
                user.startActionMode(position,dataset.get(position));
                notifyDataSetChanged();
                return true;
            }
        });

    }

    public void addCard(Card card){
        dataset.add(card);
        notifyDataSetChanged();
    }

    public void addAllCards(List<Card> cards){
        dataset.addAll(cards);
        notifyDataSetChanged();
    }

    public void setCards(List<Card> cards){
        dataset = new ArrayList<>(cards);
        notifyDataSetChanged();
    }

    public void deleteCard(int position) {
        dataset.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateEditedCard(String name, String number, int position){
        Card oldCard = dataset.get(position);
        if(!oldCard.getNumber().equals(number)){
            oldCard.setSaldo("");
            oldCard.setViajes("");
            oldCard.setLastUpdate("");
        }
        oldCard.setName(name);
        oldCard.setNumber(number);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rowLayout;
        public TextView textViewName;
        public TextView textViewCardNumber;
        public TextView textViewLastUpdate;
        public TextView textViewLastUpdateTitle;
        public TextView textViewSaldo;
        public TextView textViewViajes;
        public ImageButton updateButton;
        public ImageButton parkingCarButtom;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            rowLayout = itemView.findViewById(R.id.row_layout);
            textViewName = itemView.findViewById(R.id.name);
            textViewCardNumber = itemView.findViewById(R.id.cardNumber);
            textViewLastUpdate = itemView.findViewById(R.id.last_update);
            textViewLastUpdateTitle = itemView.findViewById(R.id.last_update_title);
            textViewSaldo = itemView.findViewById(R.id.saldo);
            textViewViajes = itemView.findViewById(R.id.viajes);
            updateButton = itemView.findViewById(R.id.update_button);
            parkingCarButtom = itemView.findViewById(R.id.parking_car_button);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }

        public void startProgress(){
            textViewLastUpdate.setVisibility(View.INVISIBLE);
            textViewLastUpdateTitle.setVisibility(View.INVISIBLE);
            textViewSaldo.setVisibility(View.INVISIBLE);
            textViewViajes.setVisibility(View.INVISIBLE);
            updateButton.setVisibility(View.INVISIBLE);
            parkingCarButtom.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void stopProgress(){
            textViewLastUpdate.setVisibility(View.VISIBLE);
            textViewLastUpdateTitle.setVisibility(View.VISIBLE);
            textViewSaldo.setVisibility(View.VISIBLE);
            textViewViajes.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.VISIBLE);
            parkingCarButtom.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
