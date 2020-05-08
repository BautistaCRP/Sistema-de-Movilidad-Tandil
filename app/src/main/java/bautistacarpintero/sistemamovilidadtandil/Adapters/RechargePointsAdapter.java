package bautistacarpintero.sistemamovilidadtandil.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.R;

public class RechargePointsAdapter extends RecyclerView.Adapter<RechargePointsAdapter.ViewHolder>{

    private String TAG = "RechargePointsAdapter";
    private ArrayList<RechargePoint> dataset;
    private RechargePointsViewerUser user;

    public RechargePointsAdapter(RechargePointsViewerUser user) {
        this.user = user;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recharge_point, parent, false );
        return new RechargePointsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = dataset.get(position).getName();
        holder.nameTextView.setText(name);
        String address = dataset.get(position).getDireccion();
        holder.addressTextView.setText(address);
        Float distance = dataset.get(position).getDistancia();
        if(distance <= 1000f)
            holder.distanceTextView.setText("a "+String.format("%.2f", distance)+" mtrs");
        else {
            distance = distance/1000f;
            holder.distanceTextView.setText("a " + String.format("%.2f", distance) + " kms");
        }

        holder.buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = dataset.get(position).getLat();
                String lon = dataset.get(position).getLon();
                String address = dataset.get(position).getDireccion();
                user.startGoogleMapsActivity(lat,lon,address);
            }
        });
    }

    public void setRechargePoints(List<RechargePoint> points){
        dataset = new ArrayList<>();
        dataset.addAll(points);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView addressTextView;
        public TextView distanceTextView;
        public ImageButton buttonMap;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            addressTextView = itemView.findViewById(R.id.address);
            distanceTextView = itemView.findViewById(R.id.distance);
            buttonMap = itemView.findViewById(R.id.buttonMap);

        }
    }
}
