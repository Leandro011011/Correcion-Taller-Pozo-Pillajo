import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel ventanaPrincipal;
    private JTabbedPane tabbedPane1;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JComboBox cmbRutas;
    private SpinnerNumberModel interval = new SpinnerNumberModel(1, 1, 5, 1);
    private JSpinner spnCantidad;
    private JButton btnCalcularTotal;
    private JTextArea txtTotalAPagar;
    private JButton btnRegistrar;
    private JTextArea txtHistorial;
    private JButton btnRefrescarHistorial;
    private JButton btnLimpirarHistorial;
    private JComboBox cbmRutaResumen;
    private JTextArea txtResumen;
    private JButton btnVerResumenRuta;
    private JButton btnVerResumenGeneral;
    private JButton btnLimpiar;
    private JButton btnLimpiarR;
    private Ruta rutaViaje;
    private Compra compraViaje;
    private GestorVentas venta = new GestorVentas();
    public Ventana(){
        venta.agregarRuta(new Ruta("QUITO - GUAYAQUIL", 10.50, 0));
        venta.agregarRuta(new Ruta("QUITO - CUENCA", 12.75, 0));
        venta.agregarRuta(new Ruta("QUITO - LOJA", 15.00, 0));

        txtHistorial.setEditable(false);
        txtTotalAPagar.setEditable(false);
        txtResumen.setEditable(false);
        spnCantidad.setModel(interval);//pa ponerle intervalos al spinner

        //PRIMERA SECCION DE "REGISTRAR COMPRA Y PASAJERO"
        //PRIMER BOTON DE CALCULAR TOTAL
        btnCalcularTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nombre = txtNombre.getText().trim();
                String cedula = txtCedula.getText().trim();
                String ruta = cmbRutas.getSelectedItem().toString();
                int numBoletos = (int) spnCantidad.getValue();

                if (!nombre.isEmpty() && !cedula.isEmpty() && !ruta.isEmpty()){
                    if (cmbRutas.getSelectedIndex() == 1){
                        txtTotalAPagar.setText("Estimad@ "+nombre+"\nCon numero de cedula: "+cedula+
                                                "\nSu total a pagar es de: "+"$"+numBoletos*10.50);
                    }else if (cmbRutas.getSelectedIndex() == 2){
                        txtTotalAPagar.setText("Estimad@ "+nombre+"\nCon numero de cedula: "+cedula+
                                                "\nSu total a pagar es de: "+"$"+numBoletos*12.75);
                    }else if (cmbRutas.getSelectedIndex() == 3){
                        txtTotalAPagar.setText("Estimad@ "+nombre+"\nCon numero de cedula: "+cedula+
                                                "\nSu total a pagar es de: "+"$"+numBoletos*15.00);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error, no se ingreso los datos necesarios", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        //SEGUNDO BOTON DE REGISTRAR COMPRA
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nombre = txtNombre.getText().trim();
                String cedula = txtCedula.getText().trim();
                String nombreRuta = cmbRutas.getSelectedItem().toString().trim();
                int numBoletos = (int) spnCantidad.getValue();

                if (nombre.isEmpty() || cedula.isEmpty() || nombreRuta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error: faltan datos para registrar la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Ruta r = venta.buscarRutaPorNombre(nombreRuta);
                if (r == null) {
                    JOptionPane.showMessageDialog(null, "Ruta no encontrada en el gestor. Revise los nombres del ComboBox.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("No se encontró la ruta: " + nombreRuta);
                    return;
                }
                Pasajero p = new Pasajero(nombre, cedula, numBoletos);

                try {
                    if (!r.hayDisponibilidad(numBoletos)) {
                        voletosTerminados();
                        return;
                    }

                    double total = r.getCostoBoleto() * numBoletos;
                    compraViaje = new Compra(p, r, numBoletos, total);

                    r.agregarCompra(compraViaje);
                    compraViaje.procesarCompra();
                    venta.registrarCompra(compraViaje);
                    rutaViaje = r;
                } catch (CapacidadExcedidaException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //TERCER BOTON QUE LIMPIA LOS CAMPOS DE INGRESO
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                txtNombre.setText("");
                txtCedula.setText("");
                txtTotalAPagar.setText("");
                cmbRutas.setSelectedIndex(0);
                spnCantidad.setValue(1);

                JOptionPane.showMessageDialog(null, "Se limpio con exito ✅ ");
            }
        });

        //SEGUNDA SECCION DE "HISTORIAL DE COMPRAS"
        //PRIMER BOTON DE REFRESCAR
        btnRefrescarHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if ( rutaViaje == null || rutaViaje.estaVaciaLasCompras() == true){
                        noHayCompras();
                    }

                    //usamos append para no sobreescribir con setText lso datos anteriores
                    txtHistorial.append(rutaViaje.toString() + "\n");
                } catch (NoHayComprasException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //SEGUNDO BOTON DE LIMPIAR HISTORIAL
        btnLimpirarHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (rutaViaje == null || rutaViaje.estaVaciaLasCompras() == true){
                        noHayCompras();
                    }

                    rutaViaje.getColaCompras().clear();
                    txtHistorial.setText("");
                    JOptionPane.showMessageDialog(null, "Se limpio con exito ✅ ");
                } catch (NoHayComprasException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //TERCERA SECCION DE "RESUMEN DE VENTAS"
        //PRIMER BOTON DE VER RESUMEN POR RUTA
        btnVerResumenRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutaSeleccionada = cbmRutaResumen.getSelectedItem().toString();
                Ruta r = venta.buscarRutaPorNombre(rutaSeleccionada);
                if (r == null) {
                    JOptionPane.showMessageDialog(null, "No existen compras registradas para esa ruta.", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int totalViajes = r.getColaCompras().size();
                double dineroTotal = 0;
                for (Compra c : r.getColaCompras()) dineroTotal += c.getTotalPagar();

                String resumen = "=== RESUMEN DE RUTA ===\n"
                        + "Ruta: " + r.getNombreRuta() + "\n"
                        + "Total personas que compraron: " + totalViajes + "\n"
                        + "Dinero total recaudado: $" + dineroTotal + "\n"
                        + "=================================\n";
                txtResumen.setText(resumen);
            }
        });

        //SEGUNDO BOTON DE VER RESUMEN GENERAL(DE TODAS LAS RUTAS)
        btnVerResumenGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (rutaViaje == null || compraViaje == null || venta == null){
                        noHayCompras();
                    }
                    txtResumen.append(venta.resumenGeneral());
                } catch (NoHayComprasException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //TERCER BOTON DE LIMPIAR EL TEXT AREA

        btnLimpiarR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                txtResumen.setText("");
                JOptionPane.showMessageDialog(null, "Se Limpio con exito ✅");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().ventanaPrincipal);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //EXCEPCIONES
    public static void voletosTerminados() throws CapacidadExcedidaException{
        throw new CapacidadExcedidaException("Error, no hay suficientes boletos");
    }
    public static void noHayCompras() throws NoHayComprasException{
        throw new NoHayComprasException("Error, aun no hay compras de boletos");
    }

}
