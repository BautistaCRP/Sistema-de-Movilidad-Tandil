package bautistacarpintero.sistemamovilidadtandil.Tabs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.Adapters.RechargePointsAdapter;
import bautistacarpintero.sistemamovilidadtandil.Adapters.RechargePointsViewerUser;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiUser;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.Distance.DistanceCalculator;
import bautistacarpintero.sistemamovilidadtandil.Distance.DistanceCalculatorUser;
import bautistacarpintero.sistemamovilidadtandil.Distance.DistanceCalculatorMatrixAPI;
import bautistacarpintero.sistemamovilidadtandil.R;

@SuppressLint("ValidFragment")
public class TabRechargePoints extends Fragment implements SumoApiUser, DistanceCalculatorUser, RechargePointsViewerUser, MyLocationListener {

    private String TAG = "TabRechargePoints";
    private View view;
    private SumoApiHandler sumoApiHandler;
    private RecyclerView recyclerView;
    private RechargePointsAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private ArrayList<RechargePoint> points;

    private DistanceCalculator distanceCalculator;
    private Location currentLocation;
    private boolean keepDoingRequest = false;

    public TabRechargePoints() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sumoApiHandler = new SumoApiHandler(this);
        points = new ArrayList<>();
        if(currentLocation != null)
            distanceCalculator = new DistanceCalculatorMatrixAPI(this,currentLocation);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_recharge_points, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RechargePointsAdapter(this);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLocation != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    sumoApiHandler.getRechargePoints();
                } else
                    buildAlertMessageNoGps();
            }
        });

        return view;
    }

    private void buildAlertMessageNoGps() {
        String message = "Para realizar esta consulta y recibir los resultados ordenados según cercanía " +
                "se necesitan permisos de para acceder a la ubicación y que la misma este activada.\n" +
                "Pulse continuar y se realizara la consulta sin recibir la información de distancia.";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No se puede acceder a la ubicacion")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",null)
                .setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        keepDoingRequest = true;
                        progressBar.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        sumoApiHandler.getRechargePoints();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void setMovements(ArrayList<Movement> movs, String number, int cardId) {}

    @Override
    public void setRechargePoint(ArrayList<RechargePoint> points) {
        if(!keepDoingRequest) {
            this.points = points;
            if (distanceCalculator != null)
                distanceCalculator.distanceTo(points);
            else {
                Collections.sort(points);
                adapter.setRechargePoints(points);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            adapter.setRechargePoints(points);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            keepDoingRequest = false;
        }
    }

    @Override
    public void updateDistances(List<Float> distances) {
        for (int i = 0; i < distances.size(); i++) {
            Float distancia = distances.get(i);
            points.get(i).setDistancia(distancia);
        }
        Collections.sort(points);
        adapter.setRechargePoints(points);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void startGoogleMapsActivity(String lat, String lon, String address) {
        Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"?q="+address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }


    @Override
    public void updateCurrentLocation(Location location) {
        if(currentLocation == null){
            currentLocation = location;
            distanceCalculator = new DistanceCalculatorMatrixAPI(this,currentLocation);
        } else {
            currentLocation = location;
            if(distanceCalculator != null)
                distanceCalculator.updateCurrentLocation(currentLocation);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("currentLocation",currentLocation);
        outState.putParcelableArrayList("points", points);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            currentLocation = (Location) savedInstanceState.get("currentLocation");
            distanceCalculator = new DistanceCalculatorMatrixAPI(this,currentLocation);
            points = savedInstanceState.getParcelableArrayList("points");
            adapter.setRechargePoints(points);
        }
        super.onActivityCreated(savedInstanceState);
    }

}
