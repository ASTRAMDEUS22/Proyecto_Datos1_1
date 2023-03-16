package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Permite interactuar con JavaFX y realizar cambios a los elementos gráficos
 */
public class Main extends Application {

    //Definir objetos de la interfaz
        //Botones
    Button empezarJuego;

        //Combobox
    ComboBox<String> seleccionDificultad;

        //Labels
    Label labelTotalMinas,
                labelTiempo;

    //Numeros de filas y columnas
    int numFilas = 8;
    int numColumnas = 8;
    //Lista de personas
    Celda[][] matrizJuego = new Celda[numFilas][numColumnas];
    CeldaTemporal[][] matrizCeldaTemp = new CeldaTemporal[numFilas][numColumnas];



    /**
     * clase que carga la información en el archivo fxml dandole el diseño a la interfaz
     * @param stage es el escenario en el cual serán añadidos todos los elementos
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setText("Empezar");
        empezarJuego.setOnAction(e -> crearPanelJuego(canva));

        empezarJuego.setTranslateX(130);
        empezarJuego.setTranslateY(-200);

        //Labels
        labelTiempo = new Label();
        labelTiempo.setText("Tiempo:");
        labelTiempo.setTranslateX(-20);
        labelTiempo.setTranslateY(-200);

        labelTotalMinas = new Label();
        labelTotalMinas.setText("Minas:");
        labelTotalMinas.setTranslateX(-150);
        labelTotalMinas.setTranslateY(-200);

        //Agregar elementos de java fx al canva
        canva.getChildren().addAll(
                empezarJuego,
                labelTiempo,
                labelTotalMinas

        );


        Scene scene = new Scene(canva, 378, 509);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();



    }

    void crearPanelJuego(StackPane canva){
        int xCoords = -150;
        int yCoords = -100;


        for (int i = 0;i < numFilas;i++){

            for (int j = 0;j < numColumnas;j ++){
                Celda celda = new Celda();

                celda.setCantidadMinas(10);

                celda.setMinWidth(40);
                celda.setMinHeight(40);

                celda.setTranslateX(xCoords);
                celda.setTranslateY(yCoords);
                celda.setStyle("-fx-border-color: black");




                celda.setOnAction(e -> estarMinado(e,celda,canva));

                matrizJuego[i][j] = celda;
                canva.getChildren().add(matrizJuego[i][j]);

                xCoords += 40;


            }
            xCoords = -150;
            yCoords += 40;

        }

        xCoords = -150;
        yCoords = -100;

        for (int i = 0;i < numFilas;i ++){

            for (int j = 0;j < numColumnas;j++){
                CeldaTemporal celda = new CeldaTemporal();

                celda.setStyle("-fx-background-color: #00CE33");
                celda.setMinWidth(40);
                celda.setMinHeight(40);

                celda.setTranslateX(xCoords);
                celda.setTranslateY(yCoords);
                celda.setStyle("-fx-border-color: black");

                celda.setOnAction(e -> desaparecerCeldasTemp(numFilas,numColumnas));

                matrizCeldaTemp[i][j] = celda;
                canva.getChildren().add(matrizCeldaTemp[i][j]);

                xCoords += 40;


            }
            xCoords = -150;
            yCoords += 40;

        }

    }

    void desaparecerCeldasTemp(int numFilas,int numColumnas){

        for (int i = 0;i < numFilas;i ++){

            for (int j = 0;j < numColumnas;j++){

                matrizCeldaTemp[i][j].setVisible(false);

            }
        }

    }

    void estarMinado(ActionEvent event, Celda celda, StackPane canva) {


        if (celda.getterHayMina()) {
            int filaTemp = 0;
            int columnaTemp = 0;
            celda.setStyle("-fx-background-color: #ff0000");

        } else {
            celda.setStyle("-fx-background-color: #00A7FF");
            crearPistas(celda);


        }
    }

    void crearPistas(Celda celda) {
        /* Analizar esto para hacer las pistas
        public static void fillBoardWithClues(int[][] board) {
            int rows = board.length;
            int cols = board[0].length;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] == -1) {
                        continue;
                    }

                    int count = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (row + i < 0 || row + i >= rows || col + j < 0 || col + j >= cols) {
                                continue;
                            }

                            if (board[row + i][col + j] == -1) {
                                count++;
                            }
                        }
                    }

                    board[row][col] = count;
                }
            }
        }
        */


        //Analiza toda la matriz en busca de celdas con minas
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (matrizJuego[i][j].getterHayMina()) {

                    //Arriba
                    try{
                        if (matrizJuego[i - 1][j].getterHayMina()){
                            continue;
                        }else{
                            matrizJuego[i - 1][j].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Arriba derecha
                    try{
                        if (matrizJuego[i - 1][j + 1].getterHayMina()){
                            continue;
                        }else{
                            matrizJuego[i - 1][j + 1].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Derecha
                    try {
                        if (matrizJuego[i][j + 1].getterHayMina()) {
                            continue;
                        } else {
                            matrizJuego[i][j + 1].aumentarNumPistas();
                        }
                    }catch(IndexOutOfBoundsException e){}


                    //Derecha abajo
                    try{
                        if (matrizJuego[i + 1][j + 1].getterHayMina()){
                            continue;
                        }else{
                            matrizJuego[i + 1][j + 1].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Abajo
                    try{
                        if (matrizJuego[i + 1][j].getterHayMina()){
                            continue;
                        }else {
                            matrizJuego[i + 1][j].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Abajo izquierda
                    try{
                        if (matrizJuego[i + 1][j - 1].getterHayMina()){
                            continue;
                        }else {
                            matrizJuego[i + 1][j - 1].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Izquierda
                    try{
                        if (matrizJuego[i][j - 1].getterHayMina()){
                            continue;
                        }else {
                            matrizJuego[i][j - 1].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}


                    //Izquierda arriba
                    try{
                        if (matrizJuego[i - 1][j - 1].getterHayMina()){
                            continue;
                        }else {
                            matrizJuego[i - 1][j - 1].aumentarNumPistas();
                        }
                    }catch (IndexOutOfBoundsException e){}

                }
            }
        }
    }

    /**
     * Clase main que permite ejecutar el programa.
     * @param args Parámetro necesario en el método main
     */
    public static void main(String[] args) {
        launch();
    }
}