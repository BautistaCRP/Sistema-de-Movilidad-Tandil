package bautistacarpintero.sistemamovilidadtandil.ActionMode;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;
import bautistacarpintero.sistemamovilidadtandil.R;

@SuppressLint("ValidFragment")
public class EditDialog extends AppCompatDialogFragment {

    private EditText editName;
    private EditText editCardNumber;
    private ActionMode mode;
    private Card card;
    private int position;
    private EditAndDeleteUser user;

    public EditDialog(ActionMode mode, Card card, int position, EditAndDeleteUser user) {
        this.mode = mode;
        this.card = card;
        this.position = position;
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);

        builder.setView(view)
                .setTitle("Editar")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mode.finish();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editName.getText().toString();
                        String number = editCardNumber.getText().toString();
                        user.updateEditedCard(name,number,position);
                        mode.finish();
                    }
                });

        editName = view.findViewById(R.id.edit_name);
        editCardNumber = view.findViewById(R.id.edit_cardNumber);
        editName.setText(card.getName());
        editCardNumber.setText(card.getNumber());
        return builder.create();
    }
}
