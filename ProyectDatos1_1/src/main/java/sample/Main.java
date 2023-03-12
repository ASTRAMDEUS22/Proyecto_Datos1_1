package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Permite interactuar con JavaFX y realizar cambios a los elementos gráficos
 */
public class Main extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneBuilderInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        //----------------------------------------------------//







    }

    /**
     * Clase main que permite ejecutar el programa.
     * @param args Parámetro necesario en el método main
     */
    public static void main(String[] args) {
        launch();
    }
}