/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Turno;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class ControlVPrincipal implements Initializable {

    int indx = 0;
    @FXML
    private ImageView imageAd;
    @FXML
    private Label tFecha;
    @FXML
    private Label tHora;
    @FXML
    private TableView<Turno> tableTurnos;
    @FXML
    private TableColumn<Turno, String> turno;
    @FXML
    private TableColumn<Turno, String> modulo;

    public void addNewTurno(String turno, String modulo) {
        Turno turn = new Turno(turno, modulo);
        ObservableList<Turno> aux;
        aux = tableTurnos.getItems();
        if (indx < 6) {
            aux.remove(indx);
            aux.add(indx, turn);
            indx++;
        } else {
            indx = 0;
            aux.remove(indx);
            aux.add(indx, turn);
            indx++;
        }
        tableTurnos.setItems(aux);
    }

    public ObservableList<Turno> getTurno() {
        ObservableList<Turno> turnos = FXCollections.observableArrayList();
        turnos.add(new Turno("", ""));
        turnos.add(new Turno("", ""));
        turnos.add(new Turno("", ""));
        turnos.add(new Turno("", ""));
        turnos.add(new Turno("", ""));
        turnos.add(new Turno("", ""));
        return turnos;
    }
    public void setTHora(String hora){
        tHora.setText(hora);
    }

    public void getHour() {
        Calendar calendario = Calendar.getInstance();
        int hora, minuto, segundo;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                tHora.setText(hora + ":" + minuto + ":" + segundo);
            }
        });
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> getHour();
        service.schedule(task, 1, TimeUnit.SECONDS);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        turno.setCellValueFactory(new PropertyValueFactory<Turno, String>("turno"));
        modulo.setCellValueFactory(new PropertyValueFactory<Turno, String>("modulo"));
        tableTurnos.setItems(getTurno());
        Calendar calendario = Calendar.getInstance();
        tFecha.setText(calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR));
        getHour();
    }

}
