import java.util.LinkedList;
import java.util.Queue;

public class GestorVentas {
    private Queue<Ruta> rutas = new LinkedList<>();
    private Queue<Compra> historialCompras = new LinkedList<>();

    public void registrarCompra(Compra c){
        historialCompras.add(c);
    }

    public void agregarRuta(Ruta r){
        rutas.add(r);
    }
    public Queue<Ruta> getRutas() {
        return rutas;
    }

    public Ruta buscarRutaPorNombre(String nombre) {
        for (Ruta r : rutas) {
            if (r.getNombreRuta().trim().equalsIgnoreCase(nombre.trim())) {
                return r;
            }
        }
        return null;
    }


    public boolean validarCedula(String cedula){
        for (Compra c : historialCompras){
            if (cedula.equals(c.getPasajero().getCedula())){
                return true;
            }
        }
        return false;
    }

    public int boletosVendidosPorRuta(String nombreRuta) {
        for (Ruta ruta : rutas) {
            if (ruta.getNombreRuta().equals(nombreRuta)) {
                return ruta.getBoletosVendidos();
            }
        }
        return 0;
    }

    public int boletosRestantesPorRuta(String nombreRuta) {
        for (Ruta ruta : rutas) {
            if (ruta.getNombreRuta().equals(nombreRuta)) {
                return ruta.getCapacidadMaxima();
            }
        }
        return 0;
    }

    public double calcularTotalRecaudado(String nombreRuta) {
        for (Ruta ruta : rutas) {
            if (ruta.getNombreRuta().equals(nombreRuta)) {
                double total = 0;
                for (Compra c : ruta.getColaCompras()) {
                    total += c.getTotalPagar();
                }
                return total;
            }
        }
        return 0.0;
    }

    public double calcularTotalRecaudadoGeneral() {
        double total = 0;
        for (Ruta ruta : rutas) {
            total += calcularTotalRecaudado(ruta.getNombreRuta());
        }
        return total;
    }

    public String resumenGeneral() {
        StringBuilder sc = new StringBuilder();
        sc.append("========== RESUMEN GENERAL DE COMPRAS ==========\n");
        for (Compra c : historialCompras) {
            sc.append(c.toString()).append("\n");
        }
        sc.append("Total recaudado general: $")
                .append(calcularTotalRecaudadoGeneral()).append("\n");
        return sc.toString();
    }
}
