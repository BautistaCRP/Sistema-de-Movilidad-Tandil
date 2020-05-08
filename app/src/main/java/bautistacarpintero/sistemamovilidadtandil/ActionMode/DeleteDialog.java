package bautistacarpintero.sistemamovilidadtandil.ActionMode;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.ActionMode;

@SuppressLint("ValidFragment")
public class DeleteDialog extends AppCompatDialogFragment {

    private ActionMode mode;
    private int position;
    private String name;
    private EditAndDeleteUser user;

    public DeleteDialog(ActionMode mode, int position, String name, EditAndDeleteUser user) {
        this.mode = mode;
        this.position = position;
        this.name = name;
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar")
                .setMessage("Desea eliminar la tarjeta "+name+"?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.deleteCard(position);
                        mode.finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mode.finish();
                    }
                });
        return builder.create();
    }
}
