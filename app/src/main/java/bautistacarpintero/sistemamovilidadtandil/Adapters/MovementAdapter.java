package bautistacarpintero.sistemamovilidadtandil.Adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.R;

public class MovementAdapter extends android.support.v7.widget.RecyclerView.Adapter<MovementAdapter.ViewHolder>  {

    private String TAG = "MovementAdapter";

    private ArrayList<Movement> dataset;

    public MovementAdapter() {
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movement, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.ViewHolder holder, int position) {
        String transaccion = "Transacción: "+dataset.get(position).getTransaccion();
        holder.transaccion.setText(transaccion);
        String tipo = "Tipo: "+dataset.get(position).getTipo();
        holder.tipo.setText(tipo);
        String fecha = "Fecha: "+dataset.get(position).getFecha()+" "+dataset.get(position).getHora();
        holder.fecha.setText(fecha);
        String linea = "Línea: "+dataset.get(position).getLinea();
        holder.linea.setText(linea);
        String recorrido = "Recorrido: "+dataset.get(position).getRecorrido();
        holder.recorrido.setText(recorrido);
        String cantPasajes = "Cantidad de pasajes: "+dataset.get(position).getCant_pasajes();
        holder.cantPasajes.setText(cantPasajes);
        String importe = "Importe: "+dataset.get(position).getImporte();
        holder.importe.setText(importe);
        String saldo = "Saldo: "+dataset.get(position).getSaldo();
        holder.saldo.setText(saldo);
        String viajes = "Cantidad de viajes: "+dataset.get(position).getSaldo_viajes();
        holder.viajes.setText(viajes);
    }


    public void addAllMovements(List<Movement> movements){
        dataset = new ArrayList<>();
        dataset.addAll(movements);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rowLayout;
        public FloatingActionButton floatingActionButton;
        public TextView transaccion;
        public TextView tipo;
        public TextView fecha;
        public TextView linea;
        public TextView recorrido;
        public TextView cantPasajes;
        public TextView importe;
        public TextView saldo;
        public TextView viajes;


        public ViewHolder(View itemView) {
            super(itemView);

            rowLayout = itemView.findViewById(R.id.row_layout);
            transaccion = itemView.findViewById(R.id.transaccion);
            tipo = itemView.findViewById(R.id.tipo);
            fecha = itemView.findViewById(R.id.fecha);
            linea = itemView.findViewById(R.id.linea);
            recorrido = itemView.findViewById(R.id.recorrido);
            cantPasajes = itemView.findViewById(R.id.cant_pasajes);
            importe = itemView.findViewById(R.id.importe);
            saldo = itemView.findViewById(R.id.saldo);
            viajes = itemView.findViewById(R.id.saldo_viajes);
        }
    }
}
