package Principal;

import Control.ControlVEmpleados;
import Control.ControlVPrincipal;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

class NewStage {
    
    NewStage(ControlVPrincipal controlPrincipal) throws IOException
    {
        Stage subStage = new Stage();
        Stage subStageUsuario = new Stage();
        subStageUsuario.setTitle("Protection Queue Manager System");
        subStage.setTitle("Protection Queue Manager System");
        subStage.getIcons().add(new Image("/Vista/Images/Icon_2.png"));
        subStageUsuario.getIcons().add(new Image("/Vista/Images/Icon_2.png"));
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/Vista/Empleados/vEmpleados.fxml"));
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/Vista/SacarTurno/VBienvenida.fxml"));
        Parent root2 = loader2.load();
        Parent root = loader1.load();
        ControlVEmpleados controller = loader1.getController();
        controller.initData(controlPrincipal);
        JMetro metro = new JMetro(JMetro.Style.LIGHT);
        Scene scene2 = new Scene(root2);
        Scene scene = new Scene(root);
        metro.applyTheme(scene);
        metro.applyTheme(scene2);
        subStage.setScene(scene);
        subStageUsuario.setScene(scene2);
        subStageUsuario.show();
        subStage.show();
    }
}

public class Principal extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Vista/vPrincipal.fxml"));
        Parent root = loader.load();
        ControlVPrincipal controller = loader.getController();
        stage.setTitle("Protection Queue Manager System");
        stage.getIcons().add(new Image("/Vista/Images/Icon_2.png"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        Scene scene = new Scene(root);
        new NewStage(controller);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));
        }catch(Exception e){
            System.out.println("Error al cargar el FXML: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        //Instanciamos la tabla BaseDeDatos y las vistas.
        launch(args);
    }
    
}
