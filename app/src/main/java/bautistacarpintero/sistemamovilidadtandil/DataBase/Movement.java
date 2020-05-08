package bautistacarpintero.sistemamovilidadtandil.DataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movimiento")
public class Movement {

    public Movement() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "transaccion")
    private String transaccion;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "hora")
    private String hora;

    @ColumnInfo(name = "linea")
    private String linea;

    @ColumnInfo(name = "recorrido")
    private String recorrido;

    @ColumnInfo(name = "unidad")
    private String unidad;

    @ColumnInfo(name = "boleto")
    private String boleto;

    @ColumnInfo(name = "cantPasajes")
    private String cant_pasajes;

    @ColumnInfo(name = "importe")
    private String importe;

    @ColumnInfo(name = "saldo")
    private String saldo;

    @ColumnInfo(name = "saldoViajes")
    private String saldo_viajes;

    @ColumnInfo(name = "number")
    private String number;

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

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        this.boleto = boleto;
    }

    public String getCant_pasajes() {
        return cant_pasajes;
    }

    public void setCant_pasajes(String cant_pasajes) {
        this.cant_pasajes = cant_pasajes;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldo_viajes() {
        return saldo_viajes;
    }

    public void setSaldo_viajes(String saldo_viajes) {
        this.saldo_viajes = saldo_viajes;
    }

    @Override
    public String toString() {
        return "Movement nro "+number+" Transaccion: "+transaccion;
    }
}
