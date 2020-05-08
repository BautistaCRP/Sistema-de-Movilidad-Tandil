package bautistacarpintero.sistemamovilidadtandil.Tabs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.ActionMode.EditAndDeleteCallback;
import bautistacarpintero.sistemamovilidadtandil.ActionMode.EditAndDeleteUser;
import bautistacarpintero.sistemamovilidadtandil.Adapters.CardAdapter;
import bautistacarpintero.sistemamovilidadtandil.Adapters.CardViewerUser;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiUser;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards.DeleteCardTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards.GetAllCardsTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards.GetAllCardsTaskListener;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards.InsertCardTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards.UpdateCardTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.DeleteAllMovementsTask;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements.InsertMovementTask;
import bautistacarpintero.sistemamovilidadtandil.MainActivity;
import bautistacarpintero.sistemamovilidadtandil.MovementActivity;
import bautistacarpintero.sistemamovilidadtandil.ParkingReminder.Coordenates.LocationPolygon;
import bautistacarpintero.sistemamovilidadtandil.ParkingReminder.ParkingReminderService;
import bautistacarpintero.sistemamovilidadtandil.R;

@SuppressLint("ValidFragment")
public class TabMain extends Fragment implements SumoApiUser,EditAndDeleteUser,CardViewerUser,GetAllCardsTaskListener {

    private View view;
    private MainActivity parentActivity;

    private String TAG = "TabMain";
    private final int MOVS_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private FloatingActionButton floatingActionButton;
    private List<Card> cards;
    private SumoApiHandler sumoApiHandler;
    private LocationPolygon locationPolygon = getLocationPolygon();

    public static String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    public static LocationPolygon getLocationPolygon(){
        LocationPolygon locationPolygon = new LocationPolygon();
        Location españaSantamarina = new Location("");
        españaSantamarina.setLatitude(-37.320384);
        españaSantamarina.setLongitude(-59.132920);
        locationPolygon.addLocation(españaSantamarina);

        Location españa14deJulio = new Location("");
        españa14deJulio.setLatitude(-37.324258);
        españa14deJulio.setLongitude(-59.142809);
        locationPolygon.addLocation(españa14deJulio);

        Location maipu14deJulio = new Location("");
        maipu14deJulio.setLatitude(-37.331664);
        maipu14deJulio.setLongitude(-59.138474);
        locationPolygon.addLocation(maipu14deJulio);

        Location maipuSantamarina = new Location("");
        maipuSantamarina.setLatitude(-37.327881);
        maipuSantamarina.setLongitude(-59.128558);
        locationPolygon.addLocation(maipuSantamarina);

        return locationPolygon;
    }

    public TabMain() {}

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sumoApiHandler = new SumoApiHandler(this);
        cards = new ArrayList<>();
        GetAllCardsTask getAllCardsTask = new GetAllCardsTask(getContext(),this);
        getAllCardsTask.execute();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_main,container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(this);
        cardAdapter.addAllCards(cards);
        recyclerView.setAdapter(cardAdapter);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                startNewCardDialog();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void startNewCardDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(dialogView)
                .setTitle("Nueva Tarjeta")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editName = dialogView.findViewById(R.id.edit_name);
                        EditText editCardNumber = dialogView.findViewById(R.id.edit_cardNumber);
                        String name = editName.getText().toString();
                        String number = editCardNumber.getText().toString();
                        newCard(name,number);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void newCard(String name, String number){
        Card card = new Card(name,number);
        InsertCardTask insertCardTask = new InsertCardTask(getContext(),card);
        insertCardTask.execute();
        cards.add(card);
        cardAdapter.addCard(card);
    }

    @Override
    public void startMovementActivity(Card card) {
        Intent intent = new Intent(getContext(),MovementActivity.class);
        intent.putExtra("number",card.getNumber());
        intent.putExtra("lastUpdate",card.getLastUpdate());
        intent.putExtra("id",card.getId());
        startActivityForResult(intent, MOVS_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void startActionMode(int position, Card card) {
        EditAndDeleteCallback editAndDeleteCallback = new EditAndDeleteCallback(card,position,this,getFragmentManager());
        parentActivity.startActionMode(editAndDeleteCallback);
    }

    @Override
    public void notifyStartActionMode() {
        floatingActionButton.setVisibility(View.GONE);
        TabLayout tabLayout = parentActivity.findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.GONE);
        parentActivity.hideRechargeTab();
        cardAdapter.startActionMode();
    }

    @Override
    public void notifyEndActionMode() {
        floatingActionButton.setVisibility(View.VISIBLE);
        TabLayout tabLayout = parentActivity.findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.VISIBLE);
        parentActivity.showRechargeTab();
        cardAdapter.endActionMode();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == MainActivity.RESULT_OK && requestCode == MOVS_ACTIVITY_REQUEST_CODE){
            int id = data.getExtras().getInt("id");
            String saldo = data.getExtras().getString("saldo");
            String viajes = data.getExtras().getString("viajes");
            String number = data.getExtras().getString("number");
            String lastUpdate = data.getExtras().getString("lastUpdate");
            if(!saldo.equals("")) {
                updateCard(id,number, saldo, viajes, lastUpdate);
            }
        }
    }

    public void updateButtonTriggered(int position){
        Card card = cards.get(position);
        sumoApiHandler.getMovements(card.getNumber(),card.getId());
    }

    public boolean isPermissionsOk(){
        String[] REQUIRED_SDK_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            int result = ContextCompat.checkSelfPermission(parentActivity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean isGpsEnable() {
        final LocationManager manager = (LocationManager) parentActivity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoPermissions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("La ubicación es necesaria para realizar recordatorios")
                .setMessage("Se necesitan permisos de ubicación para iniciar un recordatorio")
                .setCancelable(false)
                .setNegativeButton("Cancelar",null)
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", parentActivity.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.GRAY);
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("La ubicación es necesaria para realizar recordatorios")
                .setMessage("Active la ubicación en su dispositivo para iniciar un recordatorio")
                .setCancelable(false)
                .setNegativeButton("Cancelar",null)
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.GRAY);
    }

    @Override
    public void parkingCarButtomTriggered(int position) {
        if(!isMyServiceRunning(ParkingReminderService.class)) {
            if(isGpsEnable()) {
                if(isPermissionsOk()) {
                    Intent intent = new Intent(parentActivity, ParkingReminderService.class);
                    intent.putExtra("cardNumber", cards.get(position).getNumber());
                    intent.putParcelableArrayListExtra("locationPolygon", locationPolygon.getLocations());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        parentActivity.startForegroundService(intent);
                    } else
                        parentActivity.startService(intent);

                } else
                    buildAlertMessageNoPermissions();
            } else
                buildAlertMessageNoGps();
        } else {
            Toast.makeText(parentActivity,"Ya hay un recordatorio en ejecucion",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "parkingCarButtomTriggered: Ya hay un recordatorio en ejecucion");
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void updateCard(int id, String number, String saldo, String viajes, String lastUpdate){
        for(Card card : cards) {
            if (card.getNumber().equals(number) && (card.getId() == id)) {
                card.setLastUpdate(lastUpdate);
                card.setSaldo(saldo);
                card.setViajes(viajes);
                Log.d(TAG, "updateCard: Nro: "+ card.getNumber());
                UpdateCardTask updateCardTask = new UpdateCardTask(getContext(),card);
                updateCardTask.execute();
                cardAdapter.setCards(cards);
            }
        }
    }

    @Override
    public void setMovements(ArrayList<Movement> movs, String number, int cardId) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d MMMM '-' HH:mm 'hs'");
        String lastUpdate = dateFormatter.format(new Date());
        lastUpdate = Character.toUpperCase(lastUpdate.charAt(0)) + lastUpdate.substring(1);

        this.updateCard(cardId,number,movs.get(0).getSaldo(),movs.get(0).getSaldo_viajes(), lastUpdate);
        Toast.makeText(getContext(), "Saldo: "+movs.get(0).getSaldo(), Toast.LENGTH_SHORT).show();
        new DeleteAllMovementsTask(getContext(),number).execute();
        for (Movement m : movs)
            new InsertMovementTask(getContext(),m).execute();
    }

    @Override
    public void setRechargePoint(ArrayList<RechargePoint> puestos) {
        for (RechargePoint p : puestos)
            Log.d(TAG, "setRechargePoint: "+p.getDireccion());

    }

    @Override
    public void updateEditedCard(String name, String number, int position) {
        Log.d(TAG, "updateEditedCard: ");
        Card oldCard = cardAdapter.getCardByPosition(position);
        if (oldCard.getNumber().equals(number)){
            oldCard.setName(name);
            UpdateCardTask updateCardTask = new UpdateCardTask(getContext(),oldCard);
            updateCardTask.execute();


            cardAdapter.updateEditedCard(name,number,position);
        } else {
            String oldNumber = oldCard.getNumber();
            int id = oldCard.getId();
            oldCard = new Card(name,number);
            oldCard.setId(id);
            UpdateCardTask updateCardTask = new UpdateCardTask(getContext(),oldCard);
            updateCardTask.execute();
            new DeleteAllMovementsTask(getContext(),oldNumber).execute();
            cardAdapter.updateEditedCard(name,number,position);
        }
    }

    @Override
    public void deleteCard(int position) {
        Card oldCard = cardAdapter.getCardByPosition(position);
        int index = cards.indexOf(oldCard);
        DeleteCardTask deleteCardTask = new DeleteCardTask(getContext(),cards.get(index));
        deleteCardTask.execute();
        boolean repetedNumber = false;
        for(int i=0; i<cards.size();i++){
            if((i!=index) && (cards.get(index).getNumber().equals(cards.get(i).getNumber())) )
                repetedNumber = true;
        }
        if (!repetedNumber)
            new DeleteAllMovementsTask(getContext(),cards.get(index).getNumber()).execute();
        cards.remove(index);
        cardAdapter.deleteCard(position);
    }

    @Override
    public void setCardsFromDB(List<Card> cards) {
        this.cards.addAll(cards);
        cardAdapter.addAllCards(cards);
    }
}
