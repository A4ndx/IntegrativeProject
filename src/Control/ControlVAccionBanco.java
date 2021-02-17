/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.IO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVAccionBanco implements Initializable {

    String tipoUsu;
    String accionBanco;
    private String cedulaTitular;

    @FXML
    void loadAsesorApert(ActionEvent event) {
        accionBanco = "Consulta Asesor, Apertura de cuentas";
        loadNewScene(event, 1);

    }

    @FXML
    void loadAsesorCDT(ActionEvent event) {
        accionBanco = "Consulta Asesor, CDT";
        loadNewScene(event, 1);
    }

    @FXML
    void loadAsesorCartera(ActionEvent event) {
        accionBanco = "Consulta Asesor, Compra de cartera";
        loadNewScene(event, 1);
    }

    @FXML
    void loadAsesorCredito(ActionEvent event) {
        accionBanco = "Consulta Asesor, Solicitud de crédito";
        loadNewScene(event, 1);
    }

    @FXML
    void loadCaja(ActionEvent event) {
        accionBanco = "Retiros y consignaciones";
        loadNewScene(event, 1);
    }

    @FXML
    void loadVBienvenida(ActionEvent event) {
        loadNewScene(event, 0);
    }

    void loadNewScene(ActionEvent event, int ele) {
        try {
            String mens;
            if (ele == 1) {
                mens = "/Vista/SacarTurno/VReciboTurno.fxml";
            } else {
                mens = "/Vista/SacarTurno/VBienvenida.fxml";
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(mens));
            Parent VTipoUsuParent = loader.load();
            JMetro metro = new JMetro(JMetro.Style.LIGHT);
            metro.applyTheme(VTipoUsuParent);
            Scene vTipoUsuScene = new Scene(VTipoUsuParent);

            if (ele == 1) {
                //Accede al controlador y llama un metodo
                ControlVReciboTurno controller = loader.getController();
                controller.initData(tipoUsu, accionBanco, cedulaTitular);
            }

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vTipoUsuScene);
            window.show();
        } catch (IOException e) {
            IO.mostrar(e.getMessage());
        }
    }

    public void initData(String tipoUsu, String cedulaTitular) {
        this.tipoUsu = tipoUsu;
        this.cedulaTitular = cedulaTitular;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
