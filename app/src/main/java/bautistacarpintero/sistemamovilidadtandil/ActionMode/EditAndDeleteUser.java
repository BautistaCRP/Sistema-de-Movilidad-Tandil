package bautistacarpintero.sistemamovilidadtandil.ActionMode;

public interface EditAndDeleteUser {

    void notifyStartActionMode();
    void notifyEndActionMode();
    void updateEditedCard(String name, String number, int position);
    void deleteCard(int position);
}
