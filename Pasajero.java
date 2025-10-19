public class Pasajero extends Persona{
    //ATRIBUTOS
    private int numBoletosComprados;
    //METODOS
    public Pasajero(String nombre, String cedula, int numBoletosComprados) {
        super(nombre, cedula);
        this.numBoletosComprados = numBoletosComprados;
    }
    public int getNumBoletosComprados() {
        return numBoletosComprados;
    }

    public void agregarCompra(int cantidad){

    }
    @Override
    public String getIdentificacion() {
        return "";
    }

    @Override
    public String toString() {
        return "Persona: \n" + " nombre: " + getNombre() + ", cedula: " + getCedula()+", numero de boletos comprados: "+getNumBoletosComprados()+"\n";
    }
}
