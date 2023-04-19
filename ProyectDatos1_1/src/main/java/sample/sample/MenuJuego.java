package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;

import java.io.IOException;

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

    //Puerto USB donde esta conectado el ARDUINO
    static String arduinoPort = "COM4";

    static IODevice placaArduino = new FirmataDevice(arduinoPort);

    //Contador de pulsos provicional para implementar la lógica del movimiento
    //PulseCounter counter = new PulseCounter();


    /**
     * Metodo que crea los elementos de la interfaz gráfica
     * @param primaryStage Stage donde serán agregados los elementos de la interfaz
     */
    @Override
    public void start(Stage primaryStage) {

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setText("Empezar");
        empezarJuego.setOnAction(e ->
            ejecutarPanelJuego());


        try {
            placaArduino.start();
            placaArduino.ensureInitializationIsDone();

            System.out.println("Placa iniciada");


            //placaArduino.getPin(12).addEventListener(counter);

        }catch (Exception ex){
            System.out.println("No se pudo conectar al Arduino");
        }


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


    /**
     * Ejecuta la creación del Tablero según lo que el usuario haya solicitado en la interfaz
     */
    private void ejecutarPanelJuego(){
        Juego juego = new Juego();
        String cantidadMinas,dificultad;

        dificultad = seleccionDificultad.getValue();
        cantidadMinas = textoNumeroDeMinas.getText();
        VentanaAlerta ventanaAlerta = new VentanaAlerta();




        try{
            if (dificultad != null){
                //System.out.println(counter);

                /*var botonPin13 = placaArduino.getPin(13);  //Boton del PIN 13
                botonPin13.setMode(Pin.Mode.INPUT);*/
                var botonPin12 = placaArduino.getPin(12);  //Boton del PIN 12
                botonPin12.setMode(Pin.Mode.INPUT);
                var botonPin11 = placaArduino.getPin(11);  //Boton del PIN 11
                botonPin11.setMode(Pin.Mode.INPUT);
                var botonPin10 = placaArduino.getPin(10);  //Boton del PIN 10
                botonPin10.setMode(Pin.Mode.INPUT);
                var botonPin9 = placaArduino.getPin(9);  //Boton del PIN 9
                botonPin9.setMode(Pin.Mode.INPUT);
                var botonPin8 = placaArduino.getPin(8);  //Boton del PIN 8
                botonPin8.setMode(Pin.Mode.INPUT);





                if (dificultad.equals("Dummy")) {
                    juego.elementosInterfaz(Integer.parseInt(cantidadMinas),0,placaArduino);
                }else {
                    juego.elementosInterfaz(Integer.parseInt(cantidadMinas),1,placaArduino);
                }
            }else {
                ventanaAlerta.display("Advertencia","Debes seleccionar una dificultad para el algoritmo");
            }
    }catch (NumberFormatException e){
            ventanaAlerta.display("ERROR","Debes agregar un número de tipo Entero.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Ejecuta lo relacionado con JavaFx y los elementos de la interfaz
     * @param args args
     */
    public static void main(String[] args) {



        launch(args);
    }

}
