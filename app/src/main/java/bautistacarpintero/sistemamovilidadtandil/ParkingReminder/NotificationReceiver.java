package bautistacarpintero.sistemamovilidadtandil.ParkingReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ParkingReminderService.class);
        context.stopService(i);
    }
}
