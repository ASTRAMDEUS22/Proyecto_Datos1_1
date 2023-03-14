package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

/**
 * Controla las acciones realizadas por los elementos de la interfaz gráfica
 */
public class Controlador {

    @FXML
    private Label tiempoTranscurrido;

    @FXML
    private MenuButton botonMenu;

    @FXML
    private Label labelMinasEncontradas;

    @FXML
    private GridPane panelCeldas;

    @FXML
    private Button botonEmpezar;

    private Node[][] matrizCeldas = null;


    @FXML
    void iniciarJuego(ActionEvent event){

        int numFilas = 8;
        int numColumnas = 8;


        for (int i = 0;i < numFilas;i++){

            for (int j = 0;j < numColumnas;j ++){

                Cuadro celda = new Cuadro();

                celda.setCantidadMinas(10);

                celda.setMaxWidth(439);
                celda.setMaxHeight(33);
                celda.setTranslateX(0);
                celda.setTranslateY(-2.0);

                celda.setOnAction(e -> estarMinado(e,celda));


                panelCeldas.add(celda,i,j);



            }
        }
        iniciarMatriz();
        //System.out.println(matrizCeldas);

    }



    @FXML
    void iniciarMatriz() {

        //Numero de filas y columnas de la matriz
        int numFilas = 8;
        int numColumnas = 8;

        //Se crea el gridpane
        this.matrizCeldas = new Node[numFilas][numColumnas];
        for (Node node : this.panelCeldas.getChildren()){
            this.matrizCeldas[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
        }

    }



    void estarMinado(ActionEvent event,Cuadro celda){
        if (celda.getterHayMina()){
            int filaTemp = 0;
            int columnaTemp = 0;

            /*for (int i = 0; i < panelCeldas.getRowCount();i ++){
                for (int j = 0; j < panelCeldas.getColumnCount();j++){
                    switch (i) {

                        case 0 -> {  //Arriba
                            filaTemp = i - 1;


                        }

                        case 1 -> {  //Esquina superior derecha
                            filaTemp = i - 1;
                            columnaTemp = j + 1;
                        }
                        case 2 -> {  //Derecha
                            columnaTemp = j + 1;
                        }

                        case 3 -> {  //Esquina inferior derecha
                            filaTemp = i + 1;
                            columnaTemp = j + 1;
                        }
                        case 4 -> {  //Abajo
                            filaTemp = i + 1;
                        }

                        case 5 -> {  //Esquina inferior izquierda
                            filaTemp = i + 1;
                            columnaTemp = j - 1;
                        }

                        case 6 -> {  //Izquierda
                            columnaTemp = j - 1;
                        }

                        case 7 -> {  //Izquierda arriba
                            filaTemp = i - 1;
                            columnaTemp = j - 1;
                        }
                    }

                }
            }*/


        }else{
            celda.setStyle("-fx-background-color: #00A7FF");
        }
    }



    @FXML
    void desplegarDificultad(ActionEvent event) {



    }


}