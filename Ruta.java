import java.util.LinkedList;
import java.util.Queue;

public class Ruta {
    // ATRIBUTOS
    private String nombreRuta;
    private double costoBoleto;
    private int capacidadMaxima = 20;
    private int boletosVendidos = 0;
    private Queue<Compra> colaCompras = new LinkedList<>();

    // CONSTRUCTOR
    public Ruta(String nombreRuta, double costoBoleto, int boletosVendidos) {
        this.nombreRuta = nombreRuta;
        this.costoBoleto = costoBoleto;
        this.boletosVendidos = boletosVendidos;
    }

    // GETTERS
    public String getNombreRuta() {
        return nombreRuta;
    }

    public double getCostoBoleto() {
        return costoBoleto;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public Queue<Compra> getColaCompras() {
        return colaCompras;
    }


    // Verificar disponibilidad y actualizar capacidad
    public boolean hayDisponibilidad(int cantidadBoletos) {
        if (capacidadMaxima - cantidadBoletos < 0) {
            return false;
        }
        capacidadMaxima -= cantidadBoletos;
        boletosVendidos += cantidadBoletos;
        return true;
    }

    // Agregar una nueva compra a la cola
    public void agregarCompra(Compra c) {
        colaCompras.add(c);
    }

    // Verificar si no hay compras registradas
    public boolean estaVaciaLasCompras() {
        return colaCompras.isEmpty();
    }

    // Calcular total vendido en esta ruta
    public double calcularTotalVenta() {
        double total = 0;
        for (Compra c : colaCompras) {
            total += c.getTotalPagar();
        }
        return total;
    }

    // Mostrar detalle de todas las compras de la ruta
    @Override
    public String toString() {
        StringBuilder sc = new StringBuilder();
        sc.append("=== HISTORIAL DE COMPRAS EN RUTA: ").append(nombreRuta).append(" ===\n");
        for (Compra c : colaCompras) {
            sc.append(c.toString()).append("\n");
        }
        sc.append("Boletos vendidos: ").append(boletosVendidos).append("\n");
        sc.append("Boletos restantes: ").append(capacidadMaxima).append("\n");
        sc.append("Total recaudado: $").append(calcularTotalVenta()).append("\n");
        sc.append("============================================\n");
        return sc.toString();
    }
}
