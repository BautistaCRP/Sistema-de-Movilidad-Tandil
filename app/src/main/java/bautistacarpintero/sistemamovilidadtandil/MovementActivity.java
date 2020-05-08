package bautistacarpintero.sistemamovilidadtandil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.Adapters.MovementAdapter;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiUser;
import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.DeleteAllMovementsTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.GetAllMovementsTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.GetAllMovementsTaskListener;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.InsertMovementTask;

public class MovementActivity extends AppCompatActivity implements SumoApiUser, GetAllMovementsTaskListener {

    private String TAG = "MovementActivity";

    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MovementAdapter adapter;
    private List<Movement> movements;
    private SumoApiHandler sumoApiHandler;

    private int id;
    private String number;
    private String lastUpdateIn;
    private String saldo = "";
    private String viajes = "";
    private String lastUpdateOut = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        Bundle b = getIntent().getExtras();
        if(b != null){
            number = b.getString("number");
            lastUpdateIn = b.getString("lastUpdate");
            id = b.getInt("id");
        }

        sumoApiHandler = new SumoApiHandler(this);
        movements = new ArrayList<>();
        GetAllMovementsTask getAllMovementsTask = new GetAllMovementsTask(this,number,this);
        getAllMovementsTask.execute();

        floatingActionButton = findViewById(R.id.floatingActionButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(true);
        adapter = new MovementAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addAllMovements(movements);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),"Ultima consulta: "+lastUpdateIn, BaseTransientBottomBar.LENGTH_LONG);
        View mySbView = snackbar.getView();
        TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        snackbar.show();

        try {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    sumoApiHandler.getMovements(number,id);
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setMovements(ArrayList<Movement> movs, String number, int id) {
        movements = movs;
        saldo = movs.get(0).getSaldo();
        viajes = movs.get(0).getSaldo_viajes();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d MMMM '-' HH:mm 'hs'");
        lastUpdateOut = dateFormatter.format(new Date());
        lastUpdateOut = Character.toUpperCase(lastUpdateOut.charAt(0)) + lastUpdateOut.substring(1);
        DeleteAllMovementsTask deleteAllMovementsTask = new DeleteAllMovementsTask(this,number);
        deleteAllMovementsTask.execute();
        for (Movement m : movements)
            new InsertMovementTask(this,m).execute();
        adapter.addAllMovements(movements);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        Toast.makeText(this.getBaseContext(), "Saldo: "+saldo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRechargePoint(ArrayList<RechargePoint> puestos) {

    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("saldo", saldo);
        data.putExtra("viajes", viajes);
        data.putExtra("number", number);
        data.putExtra("lastUpdate", lastUpdateOut);
        data.putExtra("id",id);
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public void setMovementsFromDB(List<Movement> movements) {
        this.movements.addAll(movements);
        adapter.addAllMovements(movements);
    }
}
