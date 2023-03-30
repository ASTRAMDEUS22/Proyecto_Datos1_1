package sample;

import javafx.application.Application;
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
    TextField textoNumeroDeMinas;



    @Override
    public void start(Stage primaryStage) {

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setText("Empezar");
        empezarJuego.setOnAction(e ->
            ejecutarPanelJuego());


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
        seleccionDificultad.setTranslateX(0);
        seleccionDificultad.setTranslateY(-50);

        //TextField
        textoNumeroDeMinas = new TextField();  //Instancia de la caja de texto
        textoNumeroDeMinas.setPromptText("Cantidad de minas");
        textoNumeroDeMinas.setMaxWidth(100);  //Tamaño del cuadro
        textoNumeroDeMinas.setTranslateX(0);  //Coords en X
        textoNumeroDeMinas.setTranslateY(-100.0);  //Coords en Y


        //Agregar elementos a la interfaz
        canva.getChildren().addAll(
                empezarJuego,
                labelTitulo,
                seleccionDificultad,
                textoNumeroDeMinas);


        Scene menuJuego = new Scene(canva,378,509);
        primaryStage.setTitle("MINESWEEPER");
        primaryStage.setScene(menuJuego);
        primaryStage.show();



    }



    public void ejecutarPanelJuego(){
        Juego juego = new Juego();
        String cantidadMinas,dificultad;

        dificultad = seleccionDificultad.getValue();
        cantidadMinas = textoNumeroDeMinas.getText();


        try{
            if (dificultad != null){
                if (dificultad.equals("Dummy")) {
                    juego.elementosInterfaz(dificultad, Integer.parseInt(cantidadMinas),0);
                }else {
                    juego.elementosInterfaz(dificultad, Integer.parseInt(cantidadMinas),1);
                }
            }else {
                VentanaAlerta.display("Advertencia","Debes seleccionar una dificultad para el algoritmo");
            }
    }catch (NumberFormatException e){
            VentanaAlerta.display("ERROR","Debes agregar un número de tipo Entero.");
        }

        }

    public static void main(String[] args) {
        launch(args);
    }

}
