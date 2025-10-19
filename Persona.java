public abstract class Persona {
    //ATRIBUTOS
    private String nombre;
    private String cedula;
    //METODOS
    public Persona(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public abstract String getIdentificacion();


}
