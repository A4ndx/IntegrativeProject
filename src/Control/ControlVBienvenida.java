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
public class ControlVBienvenida implements Initializable {

    private boolean preferencial = false;

    public boolean isPreferencial() {
        return preferencial;
    }

    @FXML
    void loadNoPref(ActionEvent event) {
        preferencial = false;
        loadNewScene(event);
    }

    @FXML
    void loadPref(ActionEvent event) throws IOException {
        preferencial = true;
        loadNewScene(event);
    }

    void loadNewScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/SacarTurno/VTipoUsu.fxml"));
            Parent VTipoUsuParent = loader.load();
            JMetro metro = new JMetro(JMetro.Style.LIGHT);
            metro.applyTheme(VTipoUsuParent);
            Scene vTipoUsuScene = new Scene(VTipoUsuParent);

            //Accede al controlador y llama un metodo
            ControlVTipoUsu controller = loader.getController();
            controller.initData(preferencial);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vTipoUsuScene);
            window.show();
        } catch (IOException e) {
            IO.mostrar(e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
