package bautistacarpintero.sistemamovilidadtandil.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "card")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "saldo")
    private String saldo;

    @ColumnInfo(name = "viajes")
    private String viajes;

    @ColumnInfo(name = "lastUpdate")
    private String lastUpdate;

    public Card() {
    }

    public Card(String nombre, String number) {
        this.name = nombre;
        this.number = number;
        saldo = "";
        viajes = "";
        lastUpdate = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getViajes() {
        return viajes;
    }

    public void setViajes(String viajes) {
        this.viajes = viajes;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Nombre: "+ name +" - Nro: "+number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                Objects.equals(name, card.name) &&
                Objects.equals(number, card.number) &&
                Objects.equals(saldo, card.saldo) &&
                Objects.equals(viajes, card.viajes) &&
                Objects.equals(lastUpdate, card.lastUpdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, number, saldo, viajes, lastUpdate);
    }
}
