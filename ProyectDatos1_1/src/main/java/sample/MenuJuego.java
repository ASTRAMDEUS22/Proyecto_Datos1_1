package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuJuego extends Application {

    //Definir objetos de la interfaz

    //Botones
    Button empezarJuego;

    //Combobox
    ComboBox<String> seleccionDificultad;

    //Labels
    Label labelTitulo;

    //Caja de texto
    TextField numeroDeMinas;



    @Override
    public void start(Stage primaryStage) {

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setOnAction(e ->
            ejecutarPanelJuego(seleccionDificultad.getValue()));

        //Labels
        labelTitulo = new Label();
        labelTitulo.setText("MINESWEEPER");
        labelTitulo.setTranslateX(0);
        labelTitulo.setTranslateY(-200);

        //Combobox
        seleccionDificultad = new ComboBox<>();
        seleccionDificultad.getItems().addAll(
                "Dummy",
                "Avanced"
        );
        seleccionDificultad.setTranslateX(70);
        seleccionDificultad.setTranslateY(0);

        //TextField
        //CUADROS DE TEXTO
        numeroDeMinas = new TextField();  //Instancia de la caja de texto
        numeroDeMinas.setTranslateX(0);  //Coords en X
        numeroDeMinas.setTranslateY(-100.0);  //Coords en Y
        numeroDeMinas.setMaxWidth(100);  //Tamaño del cuadro
        numeroDeMinas.setPromptText("Cantidad de minas");

        //Agregar elementos a la interfaz
        canva.getChildren().addAll(
                empezarJuego,
                labelTitulo,
                seleccionDificultad,
                numeroDeMinas);


        Scene menuJuego = new Scene(canva,378,509);
        primaryStage.setTitle("MINESWEEPER");
        primaryStage.setScene(menuJuego);
        primaryStage.show();



    }



    public void ejecutarPanelJuego(String dificultad){
        Juego juego = new Juego();
        juego.elementosInterfaz(dificultad);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
