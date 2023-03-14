package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Permite interactuar con JavaFX y realizar cambios a los elementos gráficos
 */
public class Main extends Application {


    /**
     * clase que carga la información en el archivo fxml dandole el diseño a la interfaz
     * @param stage es el escenario en el cuál serán añadidos todos los elementos
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneBuilderInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 378, 509);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();



    }





    /**
     * Clase main que permite ejecutar el programa.
     * @param args Parámetro necesario en el método main
     */
    public static void main(String[] args) {
        launch();
    }
}