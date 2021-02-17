/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVTipoUsu implements Initializable {

    BaseDeDatos bd = new BaseDeDatos();
    String tipoUsu;
    boolean preferencial = false;
    @FXML
    private TextField tIdentificacion;
    @FXML
    private Label tError;

    @FXML
    void loadAtras(ActionEvent event) {
        loadNewScene(event, 0);
    }

    @FXML
    void loadParticular(ActionEvent event) {
        tipoUsu = "A";
        if (preferencial) {
            tipoUsu += "P";
        } else {
            tipoUsu += "A";
        }
        loadNewScene(event, 1);
    }

    @FXML
    void loadTitular(ActionEvent event) {
        bd.consultarCedulas(tIdentificacion.getText());
        if (bd.getCedulaRegistrada()) {
            tipoUsu = "E";
            if (preferencial) {
                tipoUsu += "P";
            } else {
                tipoUsu += "A";
            }
            loadNewScene(event, 1);
        } else {
            tError.setText("Error, identificacion de usuario no registrada");
        }
    }

    void loadNewScene(ActionEvent event, int ele) {
        try {
            String mens;
            if (ele == 1) {
                mens = "/Vista/SacarTurno/VAccionBanco.fxml";
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
                ControlVAccionBanco controller = loader.getController();
                controller.initData(tipoUsu, tIdentificacion.getText());
            }
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vTipoUsuScene);
            window.show();
        } catch (IOException e) {
            IO.mostrar(e.getMessage());
        }
    }

    //Recibe informacion de otros controles
    public void initData(boolean preferencial) {
        this.preferencial = preferencial;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
