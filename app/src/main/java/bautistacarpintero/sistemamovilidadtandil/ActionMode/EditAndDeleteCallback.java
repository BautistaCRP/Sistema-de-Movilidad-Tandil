package bautistacarpintero.sistemamovilidadtandil.ActionMode;

import android.support.v4.app.FragmentManager;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;
import bautistacarpintero.sistemamovilidadtandil.R;

public class EditAndDeleteCallback implements ActionMode.Callback {

    private static final String TAG = "EditAndDeleteCallback";

    Card card;
    int position;
    EditAndDeleteUser user;
    FragmentManager fragmentManager;

    public EditAndDeleteCallback(Card card, int position, EditAndDeleteUser user, FragmentManager fragmentManager) {
        this.card = card;
        this.position = position;
        this.user = user;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.edit_and_delete_menu, menu);
        mode.setTitle(card.getName());
        user.notifyStartActionMode();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                openEditDialog(mode);
                return true;

            case R.id.delete:
                openDeleteDialog(mode);
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        user.notifyEndActionMode();
    }

    private void openEditDialog(ActionMode mode) {
        EditDialog editDialog = new EditDialog(mode,card,position,user);
        editDialog.show(fragmentManager,"EditDialog");
    }

    private void openDeleteDialog(ActionMode mode){
        DeleteDialog deleteDialog = new DeleteDialog(mode,position,card.getName(),user);
        deleteDialog.show(fragmentManager,"DeleteDialog");
    }
}