/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDeDatos;
import Modelo.Turno;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.ToggleSwitch;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVReporte implements Initializable {

    BaseDeDatos bd = new BaseDeDatos();
    @FXML
    private ToggleSwitch tsCaja; //PENDIENTE
    @FXML
    private ToggleSwitch tsAsesor; //PENDIENTE
    @FXML
    private ToggleSwitch tsParti;
    @FXML
    private ToggleSwitch tsTitu;
    @FXML
    private ToggleSwitch tsPartiPref;
    @FXML
    private ToggleSwitch tsTituPref;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TableView<Turno> tableRegistros;
    @FXML
    private TableColumn<Turno, String> cTurno;
    @FXML
    private TableColumn<Turno, String> cMotivo;
    @FXML
    private TableColumn<Turno, String> cLugar;
    @FXML
    private TableColumn<Turno, String> cFecha;
    @FXML
    private TableColumn<Turno, String> cHora;
    @FXML
    private TableColumn<Turno, String> cId;
    @FXML
    private TableColumn<Turno, String> cCorreo;
    
    @FXML
    void searchTable(ActionEvent event) {
        tableRegistros.setItems(getTurno());
    }
    
    public ObservableList<Turno> getTurno(){
        boolean cja = false, assor = false;
        if(tsCaja.isSelected()){
            cja = true;
        }
        if(tsAsesor.isSelected()){
            assor = true;
        }
        ArrayList<Turno> turnosArrayList = new ArrayList<>();
        if(tsParti.isSelected()){
            bd.consultarRegistrosPorDia(dpFecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), turnosArrayList, "AA", cja, assor);
        }
        if(tsTitu.isSelected()){
            bd.consultarRegistrosPorDia(dpFecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), turnosArrayList, "EA", cja, assor);
        }
        if(tsPartiPref.isSelected()){
            bd.consultarRegistrosPorDia(dpFecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), turnosArrayList, "AP", cja, assor);
        }
        if(tsTituPref.isSelected()){
            bd.consultarRegistrosPorDia(dpFecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), turnosArrayList, "EP", cja, assor);
        }
        ObservableList<Turno> turnos = FXCollections.observableArrayList(turnosArrayList);
        return turnos;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dpFecha.setValue(LocalDate.now());
        cTurno.setCellValueFactory(new PropertyValueFactory<Turno, String>("turno"));
        cLugar.setCellValueFactory(new PropertyValueFactory<Turno, String>("modulo"));
        cMotivo.setCellValueFactory(new PropertyValueFactory<Turno, String>("accionBanco"));
        cFecha.setCellValueFactory(new PropertyValueFactory<Turno, String>("fecha"));
        cHora.setCellValueFactory(new PropertyValueFactory<Turno, String>("hora"));
        cId.setCellValueFactory(new PropertyValueFactory<Turno, String>("identificacion"));
        cCorreo.setCellValueFactory(new PropertyValueFactory<Turno, String>("correo"));
    }    
    
}
