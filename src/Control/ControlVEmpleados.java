/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVEmpleados implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane apLogin;
    @FXML
    private TextField tUserName;
    @FXML
    private PasswordField tPassword;
    @FXML
    private Label tError;
    @FXML
    private Button bRegistro;
    BaseDeDatos bd = new BaseDeDatos();
    int selected = -1;
    private ControlVPrincipal controlPrincipal;

    @FXML
    void loadVControl(ActionEvent event) throws IOException {
        if (selected != 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Empleados/VControlTurno.fxml"));
            AnchorPane pane = loader.load();
            ControlVControlTurno controller = loader.getController();
            controller.initData(controlPrincipal);
            rootPane.getChildren().setAll(pane);
            selected = 0;
        }
    }

    @FXML
    void loadVRegistro(ActionEvent event) throws IOException {
        if (selected != 1) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Vista/Empleados/VRegistro.fxml"));
            rootPane.getChildren().setAll(pane);
            selected = 1;
        }
    }

    @FXML
    void loadVReporte(ActionEvent event) throws IOException {
        if (selected != 2) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Vista/Empleados/VReporte.fxml"));
            rootPane.getChildren().setAll(pane);
            selected = 2;
        }
    }

    @FXML
    void login(ActionEvent event) {
        //Confirmar contraseña
        String username, password;
        username = tUserName.getText();
        password = tPassword.getText();
        if (bd.consultarLoginEmpleados(username, password)) {
            if (bd.getCargo().equals("CAJERO")) {
                bRegistro.setDisable(true);
            } else {
                bRegistro.setDisable(false);
            }
            apLogin.setDisable(true);
            apLogin.setVisible(false);
        } else {
            tError.setText("Error: Contraseña o usuario invalido");
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
        try {
            loadVReporte(null);
        } catch (IOException ex) {
            Logger.getLogger(ControlVEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
