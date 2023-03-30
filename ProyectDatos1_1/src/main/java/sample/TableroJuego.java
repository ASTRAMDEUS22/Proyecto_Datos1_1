package sample;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TableroJuego {

    /**
     * Constructor de la clase TableroJuego.
     * @param cantidadMinas El total de minas que se van a generar en el tablero.
     *
     */
    public TableroJuego(int cantidadMinas,int identificadorUniversal) {
        //Cantidad de minas
        this.cantidadMinas = cantidadMinas;
        this.identificadorUniversal = identificadorUniversal;
    }

    //Instancia del Objeto para acceder al escenario
    Juego ventanaJuego = new Juego();

    //Turnos
    boolean jugadorTurno = false;
    boolean botTurno = true;

    //Cantidad de minas
    int cantidadMinas;

    //Instancia lista enlazada
    ListaEnlazada listaEnlazadaElementosMatriz = new ListaEnlazada();

    //Lista enlazada de casillas que no hayan sido reveladas
    ListaEnlazada listaGeneral = new ListaEnlazada();

    //Lista enlazada donde no hay posibilidad de que haya minas
    ListaEnlazada listaSegura = new ListaEnlazada();

    //Lista enlazada donde el algoritmo no pudo determinar la certeza de la existencia de una mina
    ListaEnlazada listaIncertidumbre = new ListaEnlazada();



    //Filas y columnas
    int numFilas = 8;
    int numColumnas = 8;

    //Condicionales universales para una acción u otra
    int identificadorUniversal;

    //Matriz de celdas del jugador
    Celda[][] matrizJugador = new Celda[numFilas][numColumnas];

    //Matriz de celdas del bot
    Celda[][] matrizBot = new Celda[numFilas][numColumnas];

    //Matrices de nodos del bot avanzado
    Nodo[][] matrizNodos = new Nodo[numFilas][numColumnas];



    /**
     * crea la matriz y le añade los objetos de tipo celda.
     *
     */
    void crearPanelJuego(StackPane canva,Stage stageJuego) {
        int xCoordsP1 = -400;
        int yCoordsP1 = -100;

        //Crear el panel del jugador
        for (int i = 0; i < numFilas; i++) {

            for (int j = 0; j < numColumnas; j++) {

                if (matrizJugador[i][j] != null && matrizBot[i][j] != null) {
                    canva.getChildren().remove(matrizJugador[i][j]);
                    canva.getChildren().remove(matrizBot[i][j]);
                }

                //Creación del tablero del jugador
                Celda celdaP1 = new Celda(i, j);

                //Caracteristicas PanelJugador
                celdaP1.setMinWidth(40);
                celdaP1.setMinHeight(40);

                celdaP1.setTranslateX(xCoordsP1);
                celdaP1.setTranslateY(yCoordsP1);
                celdaP1.setStyle("-fx-border-color: black");

                celdaP1.setOnAction(e -> estarMinado(e, celdaP1, ventanaJuego.getStage(), stageJuego));
                celdaP1.setOnContextMenuRequested(e -> celdaP1.crearBandera());
                matrizJugador[i][j] = celdaP1;

                //crearPanelBot(canva, celdaP1,stageJuego);

                canva.getChildren().add(matrizJugador[i][j]);

                xCoordsP1 += 40;

            }
            xCoordsP1 = -400;
            yCoordsP1 += 40;

        }

        crearPanelBots(canva,stageJuego,identificadorUniversal);
        crearMinas(cantidadMinas);
        crearPistas();
        //listaEnlazada.mostarElementos();
    }

    private void crearPanelBots(StackPane canva, Stage stageJuego,int identificadorUniversal) {
        int xCoordsP2 = 100;
        int yCoordsP2 = -100;

        System.out.println(identificadorUniversal);

        if (identificadorUniversal == 0){  //Si el identificador es 0, cree un panel con propiedades del algoritmo dummy
            for (int i = 0; i < numFilas; i++) {

                for (int j = 0; j < numColumnas; j++) {

                    Celda celdaP2 = new Celda(i, j);

                    //Caracteristicas PanelBot
                    celdaP2.setMinWidth(40);
                    celdaP2.setMinHeight(40);

                    celdaP2.setTranslateX(xCoordsP2);
                    celdaP2.setTranslateY(yCoordsP2);
                    celdaP2.setStyle("-fx-border-color: black");
                    celdaP2.setIdentificador(matrizJugador[i][j].getIdentificador());

                    celdaP2.setOnAction(e -> estarMinado(e, celdaP2, ventanaJuego.getStage(), stageJuego));


                    matrizBot[i][j] = celdaP2;

                    //System.out.println(iteraciones ++);
                    canva.getChildren().add(matrizBot[i][j]);

                    xCoordsP2 += 40;
                }
                xCoordsP2 = 100;
                yCoordsP2 += 40;
            }
        }

        else {  //Si el identificador es 1, cree un panel con propiedades del algoritmo advanced

            for (int i = 0; i < numFilas; i++) {

                for (int j = 0; j < numColumnas; j++) {

                    //Instancia de la Celda
                    Celda celda = new Celda(i,j);

                    //Caracteristicas de la celda
                    celda.setMinWidth(40);
                    celda.setMinHeight(40);

                    celda.setTranslateX(xCoordsP2);
                    celda.setTranslateY(yCoordsP2);
                    celda.setStyle("-fx-border-color: black");
                    celda.setIdentificador(matrizJugador[i][j].getIdentificador());


                    //celda.setOnAction(e -> estarMinado(e, celda, ventanaJuego.getStage(), stageJuego));

                    Nodo nodo = new Nodo();
                    nodo.setDato(celda);


                    listaEnlazadaElementosMatriz.insertarPrimero(nodo);


                    matrizNodos[i][j] = nodo;



                    xCoordsP2 += 40;
                }

                xCoordsP2 = 100;
                yCoordsP2 += 40;

            }
            for (int i = 0;i < numFilas;i++){
                for (int j = 0;j < numColumnas;j++){
                    canva.getChildren().add(matrizNodos[i][j].getDato());



                }
            }

        }

        algoritmoAdvancedBot();

    }



    /**
     * Identifica si la celda está limpia o minada, en caso de estar minada termina el juego, si no, cambia el color de la celda.
     * @param event captura la accion del boton.
     * @param celda objeto que será añadido a la matriz.
     * @param escenario Es el escenario del Objeto Juego, con él se puede cerrar la ventana
     */
    private void estarMinado(ActionEvent event, Celda celda, Stage escenario,Stage stageJuego) {
        //Si la celda es una Mina avisar que se ha perdido el juego
        if (identificadorUniversal == 0) {
            if (celda.getIdentificador() == -1) {
                celda.setStyle("-fx-background-color: #ff0000");
                celda.setText("*");

                sufrirDerrota(escenario, stageJuego);

            } else {

                if (!celda.isEstaRevelada()) {
                    revelarCeldasSinPistas(celda.getI(), celda.getJ());
                }

                definirTurnos();
            }
        }else {
            if (celda.getIdentificador() == -1) {
                celda.setStyle("-fx-background-color: #ff0000");
                celda.setText("*");

                sufrirDerrota(escenario, stageJuego);

            } else {
                if (!celda.isEstaRevelada()) {
                    revelarCeldasSinPistas(celda.getI(), celda.getJ());
                }

                //matrizNodos[2][4].getDato().getIdentificador();
                definirTurnos();
            }
        }
    }

    /**
     * Revela de forma recursiva las celdas que no poseen pistas o minas.
     * @param fila Fila donde se ubica la celda
     * @param columnas Columna donde se ubica la celda
     */
    public void revelarCeldasSinPistas(int fila, int columnas){
        if (identificadorUniversal == 0) {
            if (fila < 0 || columnas < 0 || fila >= numFilas || columnas >= numColumnas) {  //Si está fuera del tablero retorne nada
                return;
            }

            if (matrizJugador[fila][columnas].isEstaRevelada() && matrizBot[fila][columnas].isEstaRevelada()) {  //Si la celda fue relevada salga de la ejecucion
                return;
            }

            if (matrizJugador[fila][columnas].getNumPistas() != 0 && matrizBot[fila][columnas].getNumPistas() != 0) {  //Si la casilla tiene pistas, termine la ejecución

                matrizJugador[fila][columnas].revelarPista();
                matrizJugador[fila][columnas].setEstaRevelada(true);

                matrizBot[fila][columnas].revelarPista();
                matrizBot[fila][columnas].setEstaRevelada(true);

                return;
            }


            matrizJugador[fila][columnas].setEstaRevelada(true);  //Se marca la celda como revelada
            matrizJugador[fila][columnas].setStyle("-fx-background-color: #00CE33");  //Se le asigna un color
            matrizJugador[fila][columnas].setDisable(true);

            matrizBot[fila][columnas].setEstaRevelada(true);  //Se marca la celda como revelada
            matrizBot[fila][columnas].setStyle("-fx-background-color: #00CE33");  //Se le asigna un color
            matrizBot[fila][columnas].setDisable(true);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    revelarCeldasSinPistas(fila + i, columnas + j);
                }
            }
        }else {
            if (fila < 0 || columnas < 0 || fila >= numFilas || columnas >= numColumnas) {  //Si está fuera del tablero retorne nada
                return;
            }

            if (matrizJugador[fila][columnas].isEstaRevelada() && matrizNodos[fila][columnas].getDato().isEstaRevelada()) {  //Si la celda fue relevada salga de la ejecucion
                return;
            }

            if (matrizJugador[fila][columnas].getNumPistas() != 0 && matrizNodos[fila][columnas].getDato().getNumPistas() != 0) {  //Si la casilla tiene pistas, termine la ejecución

                matrizJugador[fila][columnas].revelarPista();
                matrizJugador[fila][columnas].setEstaRevelada(true);

                matrizNodos[fila][columnas].getDato().revelarPista();
                matrizNodos[fila][columnas].getDato().setEstaRevelada(true);

                return;
            }


            matrizJugador[fila][columnas].setEstaRevelada(true);  //Se marca la celda como revelada
            matrizJugador[fila][columnas].setStyle("-fx-background-color: #00CE33");  //Se le asigna un color
            matrizJugador[fila][columnas].setDisable(true);

            matrizNodos[fila][columnas].getDato().setEstaRevelada(true);  //Se marca la celda como revelada
            matrizNodos[fila][columnas].getDato().setStyle("-fx-background-color: #00CE33");  //Se le asigna un color
            matrizNodos[fila][columnas].getDato().setDisable(true);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    revelarCeldasSinPistas(fila + i, columnas + j);
                }
            }
        }


    }

    /**
     * Ejecuta la pantalla derrota.
     * @param escenario es el Stage del Objeto Juego, permitiendo así cerrar el juego.
     */
    public void sufrirDerrota(Stage escenario,Stage stageJuego){
        VentanaPerder.display(escenario,stageJuego);  //Si se pisa una mina se muestra una alerta y se cierra el juego
    }

    /**
     * Selecciona una casilla aleatoria de la matriz y selecciona esa Celda
     */
    private void algoritmoDummyBot(){

        while (true) {
            int iRandom = (int) ((Math.random() * (7)) + 0);  //Indice i aleatorio
            int jRandom = (int) ((Math.random() * (7)) + 0);  //Indice j aleatorio
            if (!matrizJugador[iRandom][jRandom].isEstaRevelada() || !matrizBot[iRandom][jRandom].isEstaRevelada()) {

                matrizBot[iRandom][jRandom].fire();
                System.out.println(iRandom + "-" + "-" + jRandom);
                matrizBot[iRandom][jRandom].setStyle("-fx-background-color: #0c3ad2");
                matrizBot[iRandom][jRandom].setEstaRevelada(true);
                break;


            }
        }
    }

    public void algoritmoAdvancedBot(){

        //Añade Nodos a la lista general para que sean analizados en busca de minas
        for (int i = 0;i < numFilas;i++){
            for (int j = 0;j < numColumnas;j++){
                System.out.println("1");
                if (!matrizNodos[i][j].getDato().isEstaRevelada()){  //Si la Celda en el Nodo no ha sido revelada, añadir a la lista
                    listaGeneral.insertarPrimero(matrizNodos[i][j]);
                }else {
                    listaGeneral.eliminarNodo(matrizNodos[i][j],listaGeneral);  //Si la celda fue revelada, eliminarla de la lista
                }
            }
        }

        Nodo current = listaEnlazadaElementosMatriz.getHead();  //El Nodo temporal es igual al primer elemento en la lista enlazada

        while (current != null){
            System.out.println(current.getDato().getIdentificador());
            current = current.getNext();
        }


    }

    /**
     * Define los turnos del jugador y la máquina según cada uno selecciona una celda
     */
    public void definirTurnos(){
        if (identificadorUniversal == 0) {
            if (jugadorTurno) {  //Si es el turno del jugador, desactive la matriz del bot

                jugadorTurno = false;
                botTurno = true;
               algoritmoDummyBot();


            } else {  //Si es el turno del bot, desactive la matriz del jugador
                //System.out.println(botTurno);


                jugadorTurno = true;
                botTurno = false;

            }
        }else {
            if (jugadorTurno) {  //Si es el turno del jugador, desactive la matriz del bot

                jugadorTurno = false;
                botTurno = true;

                algoritmoAdvancedBot();


            } else {  //Si es el turno del bot, desactive la matriz del jugador
                //System.out.println(botTurno);

                jugadorTurno = true;
                botTurno = false;

            }
        }

    }

    /**
     * Genera minas en posiciones aleatorias en el tablero
     * @param numMinas define el maximo de minas que se generarán
     */
    private void crearMinas(int numMinas){
        //Minas creadas en el Tablero
        int minasGeneradas = 0;

        if (identificadorUniversal == 0) {
            //Mientras hayan menos minas de las necesitadas mantenga el bucle
            while (minasGeneradas != numMinas) {

                for (int i = 0; i < numFilas; i++) {
                    for (int j = 0; j < numColumnas; j++) {

                        //En caso de que dentro de los bucles for se haya cumplido el condicional del while, cierre los ciclos
                        if (minasGeneradas == numMinas) {
                            break;
                        }

                        //Genera un numero aleatorio
                        double numRandom = Math.random();

                        //Mientras el número generado sea mayor al especificado, crear una mina
                        if (numRandom >= 0.8) {

                            matrizJugador[i][j].setIdentificador(-1);
                            //matrizJugador[i][j].setText("*");

                            matrizBot[i][j].setIdentificador(-1);
                            //matrizBot[i][j].setStyle("-fx-background-color: #000");

                            //Incrementa el total de minas generadas en el tablero
                            minasGeneradas = minasGeneradas + 1;

                        }
                    }
                }
            }
        }else {
            //Mientras hayan menos minas de las necesitadas mantenga el bucle
            while (minasGeneradas != numMinas) {

                for (int i = 0; i < numFilas; i++) {
                    for (int j = 0; j < numColumnas; j++) {

                        //En caso de que dentro de los bucles for se haya cumplido el condicional del while, cierre los ciclos
                        if (minasGeneradas == numMinas) {
                            break;
                        }

                        //Genera un numero aleatorio
                        double numRandom = Math.random();

                        //Mientras el número generado sea mayor al especificado, crear una mina
                        if (numRandom >= 0.8) {

                            matrizJugador[i][j].setIdentificador(-1);
                            //matrizJugador[i][j].setText("*");

                            matrizNodos[i][j].getDato().setIdentificador(-1);
                            //matrizBot[i][j].setStyle("-fx-background-color: #000");

                            //Incrementa el total de minas generadas en el tablero
                            minasGeneradas = minasGeneradas + 1;

                        }
                    }
                }
            }
        }
    }


    /**
     * Crea las pistas en la matriz identificando el número de minas alrededor de la celda.
     */
    private void crearPistas() {
        if (identificadorUniversal == 0) {
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


                        //Si abajo hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i + 1][j].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizBot[i][j].aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si abajo izquierda hay una mina, aumente el número de pistas
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
        }else {  //Crea las pistas en la matriz con nodos
            //Analiza toda la matriz en busca de celdas con minas
            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    if (matrizJugador[i][j].getIdentificador() == 1) {

                        //Si arriba hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i - 1][j].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si arriba derecha hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i - 1][j + 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si a la derecha hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i][j + 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si a la derecha abajo hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i + 1][j + 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si abajo hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i + 1][j].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si abajo izquierda hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i + 1][j - 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si a la izquierda hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i][j - 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }


                        //Si a la izquierda arriba hay una mina, aumente el número de pistas
                        try {
                            if (matrizJugador[i - 1][j - 1].getIdentificador() == -1) {
                                matrizJugador[i][j].aumentarNumPistas();
                                matrizNodos[i][j].getDato().aumentarNumPistas();
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }

                    }
                }
            }
        }
    }

}
