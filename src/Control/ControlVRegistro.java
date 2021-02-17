/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVRegistro implements Initializable {

    @FXML
    private TextField tNombre;
    @FXML
    private TextField tApellidos;
    @FXML
    private TextField tCorreo;
    @FXML
    private TextField tPin;
    @FXML
    private TextField tId;
    @FXML
    private DatePicker dpFechaNac;
    @FXML
    private Label tMensaje;
    private static int cont = 0;
    BaseDeDatos bd = new BaseDeDatos();

    @FXML
    void registrar(ActionEvent event) {
        cont++;
        String nombre, apellidos, correo, id, fecha_nac, numero, pin;
        numero = Integer.toString(2015 + cont);
        nombre = tNombre.getText();
        apellidos = tApellidos.getText();
        correo = tCorreo.getText();
        id = tId.getText();
        fecha_nac = dpFechaNac.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        pin = tPin.getText();
        int inserto;
        if (!(nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || id.isEmpty()
                || fecha_nac.isEmpty() || numero.isEmpty() || pin.isEmpty())) {
            inserto = bd.registroT(nombre, apellidos, correo, id, fecha_nac, numero, pin);
            if (inserto == 1) {
                cont--;
                tMensaje.setTextFill(Color.web("#a42e2e"));
                tMensaje.setText("Error, usuario o correo ya se encuentran registrados");
            } else if (inserto == 0) {
                tMensaje.setTextFill(Color.web("#3ea42e"));
                tMensaje.setText("Usuario registrado correctamente");
                tNombre.setText("");
                tApellidos.setText("");
                tCorreo.setText("");
                tId.setText("");
                dpFechaNac.setValue(LocalDate.now());
                tPin.setText("");
            }
        } else {
            cont--;
            tMensaje.setTextFill(Color.web("#a42e2e"));
            tMensaje.setText("Error, Ingrese valores a ingresar");
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dpFechaNac.setValue(LocalDate.now());
    }

}
