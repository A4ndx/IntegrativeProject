/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
import Modelo.Email;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVControlTurno implements Initializable {

    String[][] pendientes;
    int cod_lugar;
    BaseDeDatos bd = new BaseDeDatos();
    @FXML
    private Label tTipoUsu;
    @FXML
    private Label tTurno;
    @FXML
    private Label tError;
    @FXML
    private ComboBox<String> cbModulo;
    @FXML
    private Spinner<Integer> sNumero;
    private ControlVPrincipal controlPrincipal;

    @FXML
    void getNextTurno(ActionEvent event) {
        tError.setText("");
        pendientes = bd.consultarRegistrosSinLlamar(cbModulo.getValue().toUpperCase(), sNumero.getValue());
        cod_lugar = bd.getCod_Lugar();
        if (pendientes != null) {
            int iPosicion = -1;
            int posicionCorreo = -1;
            if (sNumero.getValue() == 3) {
                boolean EP = false;
                for (int i = 0; i < pendientes.length; i++) {
                    if (pendientes[i][5].equals("EP")) {
                        EP = true;
                        iPosicion = i;
                        break;
                    }
                }
                if (EP) {
                    for (int i = 0; i < pendientes.length; i++) {
                        if (i == iPosicion) {
                            continue;
                        }
                        if (pendientes[i][5].equals("EP")) {
                            posicionCorreo = i;
                            break;
                        }
                    }
                    if (posicionCorreo == -1) {
                        for (int i = 0; i < pendientes.length; i++) {
                            if (i == iPosicion) {
                                continue;
                            }
                            if (pendientes[i][5].equals("EA")) {
                                posicionCorreo = i;
                                break;
                            }
                        }
                    }
                    if (posicionCorreo < pendientes.length && posicionCorreo >= 0) {
                        enviarCorreo(null, pendientes[posicionCorreo][0], 1);
                    }
                } else {
                    boolean EA = false;
                    for (int i = 0; i < pendientes.length; i++) {
                        if (pendientes[i][5].equals("EA")) {
                            iPosicion = i;
                            EA = true;
                            break;
                        }
                    }
                    if (EA) {
                        for (int i = 0; i < pendientes.length; i++) {
                            if (i == iPosicion) {
                                continue;
                            }
                            if (pendientes[i][5].equals("EP")) {
                                posicionCorreo = i;
                                break;
                            }
                        }
                        if (posicionCorreo == -1) {
                            for (int i = 0; i < pendientes.length; i++) {
                                if (i == iPosicion) {
                                    continue;
                                }
                                if (pendientes[i][5].equals("EA")) {
                                    posicionCorreo = i;
                                    break;
                                }
                            }
                        }
                        if (posicionCorreo < pendientes.length && posicionCorreo >= 0) {
                            enviarCorreo(null, pendientes[posicionCorreo][0], 1);
                        }
                    } else {
                        boolean AP = false;
                        for (int i = 0; i < pendientes.length; i++) {
                            if (pendientes[i][5].equals("AP")) {
                                iPosicion = i;
                                AP = true;
                                break;
                            }
                        }
                        if (!AP) {
                            tError.setText("No hay más personas por atender en este momento");
                        }
                    }
                }
            } else {
                boolean AP = false;
                for (int i = 0; i < pendientes.length; i++) {
                    if (pendientes[i][5].equals("AP")) {
                        iPosicion = i;
                        AP = true;
                        break;
                    }
                }
                if (!AP) {
                    boolean AA = false;
                    for (int i = 0; i < pendientes.length; i++) {
                        if (pendientes[i][5].equals("AA")) {
                            iPosicion = i;
                            AA = true;
                            break;
                        }
                    }
                    if (!AA) {
                        tError.setText("No hay más personas por atender en este momento");
                    }
                }
            }
            //ASIGNA EL TURNO
            if (iPosicion >= 0) {
                hacerAsignacion(pendientes, iPosicion);
                //ACTUALIZA LA BASE DE DATOS
                bd.actualizarRegistro(true, Integer.parseInt(pendientes[iPosicion][0]), cod_lugar);
                //MANDA CORREO AL ATENDIDO
                String lugar = cbModulo.getValue() + "-" + sNumero.getValue();
                enviarCorreo(lugar, pendientes[iPosicion][0], 2);
            }
        } else {
            System.out.println("ERROR");
        }
    }

    public void hacerAsignacion(String[][] pendientes, int iPosicion) {
        if (controlPrincipal != null) {
            String turno = pendientes[iPosicion][5] + "-" + pendientes[iPosicion][3];
            String modulo = cbModulo.getValue() + "-" + sNumero.getValue();
            controlPrincipal.addNewTurno(turno, modulo);
            tTurno.setText("-" + pendientes[iPosicion][3]);
            tTipoUsu.setText(pendientes[iPosicion][5]);
        } else {
            System.out.println("ERROR: TURNO NO ASIGNADO");
        }
    }

    private void enviarCorreo(String lugar, String cod_registro, int tipo) {
        String correo = "", asunto, cuerpo, cedula;
        cedula = bd.obtenerCedula(cod_registro);
        if (!cedula.isEmpty()) {
            correo = bd.consultarCorreo(cedula);
        }
        if (tipo == 1) {
            asunto = "ESTA A UN TURNO DE SER ATENDIDO";
            cuerpo = "<h1 style=\"color: #5e9ca0; text-align: center;\"><span style=\"color: #333333;\">Queda un turno</span></h1>\n"
                    + "<h2 style=\"color: #2e6c80;\">Se encuentra a un turno de ser atendido.</h2>\n"
                    + "<p>Por favor espere en la sala de espera hasta que su turno sea llamado.</p>\n"
                    + "<p><span style=\"color: #ff0000;\">IMPORTANTE: </span>Sera llamado en un turno siempre y cuando no llegue una persona con mayor prioridad antes de ser atendido.&nbsp;</p>\n"
                    + "<p><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://image.ibb.co/koeHL0/logo.png\" alt=\"logo\" border=\"0\" /></p>\n"
                    + "<h2 style=\"text-align: center;\"><span style=\"color: #000000;\">Protection Bank&nbsp; &nbsp;</span></h2>";
        } else {
            asunto = "HA SIDO ATENDIDO: " + lugar;
            cuerpo = "<h1 style=\"color: #5e9ca0; text-align: center;\"><span style=\"color: #333333;\">Ha sido atendido</span></h1>\n"
                    + "<h2 style=\"color: #2e6c80;\">Su turno ha sido atendido en el modulo:&nbsp;<span style=\"color: #ff0000;\">" + lugar + "</span></h2>\n"
                    + "<p><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://image.ibb.co/koeHL0/logo.png\" alt=\"logo\" border=\"0\" /></p>\n"
                    + "<h2 style=\"text-align: center;\"><span style=\"color: #000000;\">Protection Bank&nbsp; &nbsp;</span></h2>";
        }
        if (!correo.isEmpty()) {
            final String email, header, content;
            email = correo;
            header = asunto;
            content = cuerpo;
            Runnable proc = () -> Email.enviarConGMail(email, header, content);
            new Thread(proc).start();
        }
    }

    public void initData(ControlVPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> arrayModulo = FXCollections.observableArrayList("Caja", "Asesor");
        cbModulo.setItems(arrayModulo);
        cbModulo.setValue("Caja");
        SpinnerValueFactory<Integer> TurnoValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
        sNumero.setValueFactory(TurnoValueFactory);
        sNumero.setEditable(true);

    }

}
