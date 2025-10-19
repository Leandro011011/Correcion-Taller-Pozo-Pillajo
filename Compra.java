import javax.swing.*;

public class Compra {
    //ATRIBUTOS
    private Pasajero pasajero;
    private Ruta ruta;
    private int cantidadBoletos;
    private double totalPagar;
    //METODOS
    public Compra(Pasajero pasajero, Ruta ruta, int cantidadBoletos, double totalPagar) {
        this.pasajero = pasajero;
        this.ruta = ruta;
        this.cantidadBoletos = cantidadBoletos;
        this.totalPagar = totalPagar;
    }
    public Pasajero getPasajero() {
        return pasajero;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void procesarCompra(){
        JOptionPane.showMessageDialog(null, "Compra hecha con exito ✅");
    }

    @Override
    public String toString() {
        return "Compra: " + "\npasajero: " + pasajero.getNombre() + "\nRuta: " + ruta.getNombreRuta() +"\nNumero de Boletos: " + cantidadBoletos + "\nTotal: " + totalPagar +"\n";
    }
}
