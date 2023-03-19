package sample;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TableroJuego {

    /**
     * Constructor de la clase TableroJuego.
     * @param cantidadMinas El total de minas que se van a generar en el tablero.
     * @param dificultad Según la dificultad deseada, se ejecutará un algoritmo de dificultad.
     */
    public TableroJuego(int cantidadMinas,String dificultad) {
        //Cantidad de minas
        this.cantidadMinas = cantidadMinas;
    }

    //Instancia del Objeto para acceder al escenario
    Juego ventanaJuego = new Juego();

    //Turnos
    boolean jugadorTurno = true;
    boolean botTurno = false;

    //Cantidad de minas
    int cantidadMinas;

    //Filas y columnas

    int numFilas = 8;
    int numColumnas = 8;

    //Matriz de celdas del jugador
    Celda[][] matrizJugador = new Celda[numFilas][numColumnas];

    //Matriz de celdas del bot
    Celda[][] matrizBot = new Celda[numFilas][numColumnas];

    /**
     * crea la matriz y le añade los objetos de tipo celda.
     *
     */
    void crearPanelJuego(StackPane canva) {
        int xCoordsP1 = -400;
        int yCoordsP1 = -100;

        int xCoordsP2 = 100;
        int yCoordsP2 = -100;

        for (int i = 0; i < numFilas; i++) {

            for (int j = 0; j < numColumnas; j++) {

                if (matrizJugador[i][j] != null && matrizBot[i][j] != null){
                    matrizJugador[i][j].setVisible(false);
                    matrizBot[i][j].setVisible(false);
                }

                //Creación del tablero del jugador
                Celda celdaP1 = new Celda();
                Celda celdaP2 = new Celda();

                //Caracteristicas PanelJugador
                celdaP1.setMinWidth(40);
                celdaP1.setMinHeight(40);

                celdaP1.setTranslateX(xCoordsP1);
                celdaP1.setTranslateY(yCoordsP1);
                celdaP1.setStyle("-fx-border-color: black");

                celdaP1.setOnAction(e -> estarMinado(e, celdaP1,ventanaJuego.getStage()));

                //Caracteristicas PanelBot
                celdaP2.setMinWidth(40);
                celdaP2.setMinHeight(40);

                celdaP2.setTranslateX(xCoordsP2);
                celdaP2.setTranslateY(yCoordsP2);
                celdaP2.setStyle("-fx-border-color: black");
                celdaP2.setIdentificador(celdaP1.getIdentificador());

                celdaP2.setOnAction(e -> estarMinado(e, celdaP2,ventanaJuego.getStage()));

                matrizJugador[i][j] = celdaP1;
                matrizBot[i][j] = celdaP2;

                canva.getChildren().addAll(matrizJugador[i][j],matrizBot[i][j]);

                xCoordsP1 += 40;
                xCoordsP2 += 40;
            }
            xCoordsP1 = -400;
            yCoordsP1 += 40;

            xCoordsP2 = 100;
            yCoordsP2 += 40;

        }
        generarMinas(cantidadMinas);
        crearPistas();
    }

    /**
     * Genera minas en posiciones aleatorias en el tablero
     * @param numMinas define el maximo de minas que se generarán
     */
    public void generarMinas(int numMinas){
        //Minas creadas en el Tablero
        int minasGeneradas = 0;

        //Mientras hayan menos minas de las necesitadas mantenga el bucle
        while (minasGeneradas < numMinas){

            for (int i = 0;i < numFilas;i++) {
                for (int j = 0; j < numColumnas; j++) {

                    //En caso de que dentro de los bucles for se haya cumplido el condicional del while, cierre los ciclos for
                    if (minasGeneradas == numMinas){
                        break;
                    }
                    //Genera un numero aleatorio
                    double numRandom = Math.random();

                    //Mientras el número generado sea mayor al especificado, crear una mina
                    if (numRandom >= 0.8) {

                        matrizJugador[i][j].setIdentificador(-1);
                        matrizJugador[i][j].setText("*");

                        matrizBot[i][j].setIdentificador(-1);
                        matrizBot[i][j].setText("*");

                        //Incrementa el total de minas generadas en el tablero
                        minasGeneradas = minasGeneradas + 1;

                    }
                }
            }
        }

        System.out.println(minasGeneradas);
        System.out.println(numMinas);

    }

    /**
     * Identifica si la celda está limpia o minada, en caso de estar minada termina el juego, si no, cambia el color de la celda.
     * @param event captura la accion del boton.
     * @param celda objeto que será añadido a la matriz.
     * @param escenario Es el escenario del Objeto Juego, con él se puede cerrar la ventana
     */
    public void estarMinado(ActionEvent event, Celda celda, Stage escenario) {
        //Si la celda es una Mina avisar que se ha perdido el juego
        if (celda.getIdentificador() == -1) {
            celda.setStyle("-fx-background-color: #ff0000");
            celda.setText("*");

            sufrirDerrota(escenario);


        } else {
            celda.setStyle("-fx-background-color: #00ff23");
            definirTurnos();
        }
    }

    /**
     * Ejecuta la pantalla derrota.
     * @param escenario es el Stage del Objeto Juego, permitiendo así cerrar el juego.
     */
    public void sufrirDerrota(Stage escenario){
        VentanaPerder.display(escenario);  //Si se pisa una mina se muestra una alerta y se cierra el juego
    }

    /**
     * Selecciona una casilla aleatoria de la matriz y selecciona esa Celda
     */
    public void algoritmoDummyBot(){

        int iRandom = (int)((Math.random() * (7))+0);  //Indice i aleatorio
        int jRandom = (int)((Math.random() * (7))+0);  //Indice j aleatorio

        matrizBot[iRandom][jRandom].fire();

    }

    /**
     * Define los turnos del jugador y la máquina según cada uno selecciona una celda
     */
    public void definirTurnos(){
        System.out.println(jugadorTurno);
        System.out.println(botTurno);
        if (jugadorTurno){  //Si es el turno del jugador, desactive la matriz del bot
            for (int i = 0;i<numFilas;i++){
                for (int j = 0;j < numColumnas;j++){

                    matrizBot[i][j].setDisable(true);
                    matrizJugador[i][j].setDisable(false);

                    jugadorTurno = false;
                    botTurno = true;
                }
            }
        }else {  //Si es el turno del bot, desactive la matriz del jugador
            for (int i = 0;i<numFilas;i++) {
                for (int j = 0; j < numColumnas; j++) {

                    matrizJugador[i][j].setDisable(true);
                    matrizBot[i][j].setDisable(false);

                    jugadorTurno = true;
                    botTurno = false;

                }
            }
            algoritmoDummyBot();  //Despues de asignarse los valores boolean, se llama al bot
        }

    }

    /**
     * Crea las pistas en la matriz identificando el número de minas alrededor de la celda.
     */
    public void crearPistas() {

        //Analiza toda la matriz en busca de celdas con minas
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (matrizJugador[i][j].getIdentificador() == 1) {

                    //Si arriba hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i - 1][j].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si arriba derecha hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i - 1][j + 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si a la derecha hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i][j + 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si a la derecha abajo hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i + 1][j + 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si a la abajo hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i + 1][j].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Abajo izquierda
                    try {
                        if (matrizJugador[i + 1][j - 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si a la izquierda hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i][j - 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }


                    //Si a la izquierda arriba hay una mina, aumente el número de pistas
                    try {
                        if (matrizJugador[i - 1][j - 1].getIdentificador() == -1) {
                            matrizJugador[i][j].aumentarNumPistas();
                            matrizBot[i][j].aumentarNumPistas();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }

                }
            }
        }
    }
}
