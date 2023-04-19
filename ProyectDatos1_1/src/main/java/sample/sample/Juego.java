package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.firmata4j.IODevice;

/**
 * Permite interactuar con JavaFX y realizar cambios a los elementos gráficos
 */
public class Juego {

    //Definir objetos de la interfaz

    //Botones
    Button cerrarJuego;

    private Label labelTiempo;
    private Label labelContSegundos;
    private Label labelContMinutos;
    private Label labelSeparador;

    private static Label labelCeldaClickBot,
            labelCantidadMinas;

    private static int cantidadTotalMinas;
    private final int limiteAumentoMinas = cantidadTotalMinas;





    Stage stage = new Stage();

    /**
     * Crea la zona donde se visualizarán los elementos gráficos.
     * @param cantidadMinas Cantidad de minas asignadas por el usuario
     * @param identificador Dificuldad elegida
     * @param placaArduino Identificador del arduino
     */
    public void elementosInterfaz(int cantidadMinas, int identificador, IODevice placaArduino) {

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();



        cantidadTotalMinas = cantidadMinas;

        //Botones
        cerrarJuego = new Button();
        cerrarJuego.setText("Salir");
        cerrarJuego.setOnAction(e -> {
            stage.close();

        });
        cerrarJuego.setTranslateX(100);
        cerrarJuego.setTranslateY(-200);

        //Labels
        labelTiempo = new Label();
        labelTiempo.setText("Tiempo:");
        labelTiempo.setTranslateX(-140);
        labelTiempo.setTranslateY(-150);

        labelContSegundos = new Label();
        labelContSegundos.setText("");
        labelContSegundos.setTranslateX(-87);
        labelContSegundos.setTranslateY(-150);

        labelContMinutos = new Label();
        labelContMinutos.setText("");
        labelContMinutos.setTranslateX(-100);
        labelContMinutos.setTranslateY(-150);

        labelSeparador = new Label();
        labelSeparador.setText(":");
        labelSeparador.setTranslateX(-94);
        labelSeparador.setTranslateY(-150);


        //Labels
        Label labelTotalMinas = new Label();
        labelTotalMinas.setText("Minas:");
        labelTotalMinas.setTranslateX(-140);
        labelTotalMinas.setTranslateY(-190);

        labelCantidadMinas = new Label();
        labelCantidadMinas.setText(String.valueOf(cantidadMinas));
        labelCantidadMinas.setTranslateX(-100);
        labelCantidadMinas.setTranslateY(-190);

        labelCeldaClickBot = new Label();
        //labelCeldaClickBot.setText("");
        labelCeldaClickBot.setTranslateX(220);
        labelCeldaClickBot.setTranslateY(-100);

        //Crear el panel
        ejecutarJuego(canva,cantidadMinas,identificador,placaArduino);

        //Ejecutar el temporizador
        ejecutarContador();

        //Agregar elementos de java fx al canva
        canva.getChildren().addAll(
                cerrarJuego,
                labelTiempo,
                labelTotalMinas,
                labelCantidadMinas,
                labelContSegundos,
                labelContMinutos,
                labelSeparador,
                labelCeldaClickBot

        );

        //Se define un Stage donde se añadirán los elementos
        Scene panelJuego = new Scene(canva, 600, 509);
        stage.setTitle("Minesweeper");
        stage.setScene(panelJuego);
        stage.show();
    }

    /**
     * Ejecuta el Thread encargado del tiempo
     */
    public void ejecutarContador() {

        Contador contador = new Contador(labelContSegundos,labelContMinutos);

        if (contador.isAlive()) {  //Esto no esta sirviendo ;_;
            contador.interrupt();
        } else {
            //Se inicia el Thread
            contador.start();
        }
    }

    /**
     * Crea el tablero según toda la información enviada por el usuario
     * @param canva Zona donde se añadirán los elementos gráficos.
     * @param cantidadMinas Cantidad de minas enviadas por el usuario
     * @param identificador Dificultad enviada por el usuario
     * @param placaArduino Placa arduino conectada
     */
    private void ejecutarJuego(StackPane canva,int cantidadMinas,int identificador,IODevice placaArduino){

        Tablero tablero = new Tablero(cantidadMinas,stage,placaArduino);

        //Ejecuta la creación de un Panel u otro dependiendo de la dificultad
        if (identificador == 0) {
            //Instancia del tablero del jugador


            placaArduino.getPin(12).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(11).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            /*placaArduino.getPin(10).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(9).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(8).addEventListener(tablero);  //Enviar señal de evento al objeto tablero*/



            //Ejecución de la interfaz del tablero jugador
            tablero.crearPanelJuego(canva, -150, -100);
        }else {

            //placaArduino.getPin(12).addEventListener(counter);

            placaArduino.getPin(12).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(11).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(10).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(9).addEventListener(tablero);  //Enviar señal de evento al objeto tablero
            placaArduino.getPin(8).addEventListener(tablero);  //Enviar señal de evento al objeto tablero

            //Ejecucion de la interfaz del tablero jugador
            tablero.crearPanelJuegoNodos(canva,-150,-100);


        }

    }

    //Devuelve el Stage correspondiente a este Objeto

    /**
     * Se obtiene el Stage
     * @return Stage
     */
    public Stage getStage() {
        return stage;
    }

    //Metodo que actualiza el indicador de donde clica el bot

    /**
     * Cambia el texto del Label
     * @param texto String del nuevo texto
     */
    public void setLabelCeldaClickBot(String texto){
        labelCeldaClickBot.setText(texto);
    }

    /**
     * Cambia el total de las minas
     * @param texto String del nuevo texto
     */
    public void setLabelTotalMinas(String texto){
        labelCantidadMinas.setText(texto);
    }

    /**
     * Obtiene el Label que maneja todas las minas
     * @return Objeto label
     */
    public Label getLabelCantidadMinas() {
        return labelCantidadMinas;
    }

    /**
     * Disminuye las minas hay en el juego según se colocan banderas
     */
    public void disminuirMinas(){
        if (cantidadTotalMinas - 1 >= 0) {
            cantidadTotalMinas --;
            labelCantidadMinas.setText(String.valueOf(cantidadTotalMinas));
        }

    }

    /**
     * Aumenta las minas hay en el juego según se colocan banderas
     */
    public void aumentarMinas(){
        if(cantidadTotalMinas + 1 <= limiteAumentoMinas) {
            cantidadTotalMinas++;
            labelCantidadMinas.setText(String.valueOf(cantidadTotalMinas));
        }

    }

}

