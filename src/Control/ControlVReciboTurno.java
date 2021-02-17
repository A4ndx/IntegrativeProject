/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
import Modelo.Email;
import Modelo.IO;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVReciboTurno implements Initializable {

    @FXML
    private Label tTipoUsu;
    @FXML
    private Label tTurno;
    @FXML
    private Label tFecha;
    @FXML
    private Label tHora;
    BaseDeDatos bd = new BaseDeDatos();
    private String horaDia;
    private String tipoUsu;
    private String accionBanco;
    static int contAA = 0;
    static int contAP = 0;
    static int contEA = 0;
    static int contEP = 0;
    private String cedulaTitular;

    @FXML
    void loadVBienvenida(ActionEvent event) {
        loadNewScene(event);
    }

    void loadNewScene(ActionEvent event) {
        try {
            String mens = "/Vista/SacarTurno/VBienvenida.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(mens));
            Parent VTipoUsuParent = loader.load();
            JMetro metro = new JMetro(JMetro.Style.LIGHT);
            metro.applyTheme(VTipoUsuParent);
            Scene vTipoUsuScene = new Scene(VTipoUsuParent);

            //Accede al controlador y llama un metodo
//            ControlVReciboTurno controller = loader.getController();
//            controller.initData(tipoUsu, accionBanco);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vTipoUsuScene);
            window.show();
        } catch (IOException e) {
            IO.mostrar(e.getMessage());
        }
    }

    static String integerFormat(int i) {
        DecimalFormat df = new DecimalFormat("00");
        String s = df.format(i);

        return s;
    }

    public void hacerTurno() {
        //AQUI SE HACE EL TURNO 
        String correo, mens;
        Calendar calendario = Calendar.getInstance();
        horaDia = "" + integerFormat(calendario.get(Calendar.HOUR_OF_DAY)) + integerFormat(calendario.get(Calendar.MINUTE));
        Integer.parseInt(horaDia);
        tFecha.setText(calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR));
        if (calendario.get(Calendar.AM_PM) == 0) {
            tHora.setText(calendario.get(Calendar.HOUR_OF_DAY) + ":" + integerFormat(calendario.get(Calendar.MINUTE)) + " AM");
        } else {
            tHora.setText(calendario.get(Calendar.HOUR_OF_DAY) + ":" + integerFormat(calendario.get(Calendar.MINUTE)) + " PM");
        }
        tTipoUsu.setText(this.tipoUsu);
        switch (this.tipoUsu) {
            case "AA":
                contAA++;
                tTurno.setText("-" + contAA);
                bd.insertarRegistro(0, Integer.parseInt(horaDia), false, contAA, accionBanco, tipoUsu);
                break;
            case "AP":
                contAP++;
                tTurno.setText("-" + contAP);
                bd.insertarRegistro(0, Integer.parseInt(horaDia), false, contAP, accionBanco, tipoUsu);
                break;
            case "EA":
                contEA++;
                tTurno.setText("-" + contEA);
                bd.insertarRegistro(0, Integer.parseInt(horaDia), false, contEA, accionBanco, tipoUsu);
                bd.insertarRegistroTitular(bd.consultarCedulas(cedulaTitular));
                correo = bd.consultarCorreo(cedulaTitular);
                if (!correo.isEmpty()) {
                    Runnable proc = () -> mandarCorreo(correo, "EA", contEA);
                    new Thread(proc).start();
                }
                break;
            case "EP":
                contEP++;
                tTurno.setText("-" + contEP);
                bd.insertarRegistro(0, Integer.parseInt(horaDia), false, contEP, accionBanco, tipoUsu);
                bd.insertarRegistroTitular(bd.consultarCedulas(cedulaTitular));
                correo = bd.consultarCorreo(cedulaTitular);
                if (!correo.isEmpty()) {
                    Runnable proc = () -> mandarCorreo(correo, "EP", contEP);
                    new Thread(proc).start();
                }
                break;
        }
    }

    public void mandarCorreo(String correo, String tipoUsu, int num) {
        String mens = "NULL";
        if (tipoUsu.equals("EA")) {
            mens = "<h1 style=\"color: #5e9ca0; text-align: center;\"><span style=\"color: #333333;\">Turno Asignado</span></h1>\n"
                    + "<h2 style=\"color: #2e6c80;\">Su turno asignado es:&nbsp;<span style=\"color: #ff0000;\">EA-" + num + "</span></h2>\n"
                    + "<p>Por favor espere en la sala de espera hasta que su turno sea llamado, tambien ser&aacute; informado cuando este cerca su turno.&nbsp;</p>\n"
                    + "<p><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://image.ibb.co/koeHL0/logo.png\" alt=\"logo\" border=\"0\" /></p>\n"
                    + "<h2 style=\"text-align: center;\"><span style=\"color: #000000;\">Protection Bank&nbsp; &nbsp;</span></h2>";
        } else {
            mens = "<h1 style=\"color: #5e9ca0; text-align: center;\"><span style=\"color: #333333;\">Turno Asignado</span></h1>\n"
                    + "<h2 style=\"color: #2e6c80;\">Su turno asignado es:&nbsp;<span style=\"color: #ff0000;\">EP-" + num + "</span></h2>\n"
                    + "<p>Por favor espere en la sala de espera hasta que su turno sea llamado, tambien ser&aacute; informado cuando este cerca su turno.&nbsp;</p>\n"
                    + "<p><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://image.ibb.co/koeHL0/logo.png\" alt=\"logo\" border=\"0\" /></p>\n"
                    + "<h2 style=\"text-align: center;\"><span style=\"color: #000000;\">Protection Bank&nbsp; &nbsp;</span></h2>";
        }
        Email.enviarConGMail(correo, "TURNO ASIGNADO", mens);
    }

    public void initData(String tipoUsu, String accionBanco, String cedulaTitular) {
        this.tipoUsu = tipoUsu;
        this.accionBanco = accionBanco;
        this.cedulaTitular = cedulaTitular;
        hacerTurno();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
