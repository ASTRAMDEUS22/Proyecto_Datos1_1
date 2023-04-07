package sample;

import javafx.event.ActionEvent;

import javafx.scene.layout.StackPane;

public class Tablero {

    //Constructor del tablero
    public Tablero(int cantidadMinas) {
        //System.out.println("1");
        this.cantidadMinas = cantidadMinas;
    }

    Juego juego = new Juego();

    private boolean turnoJugador = false;
    private boolean turnoBot = true;

    //Instancia de la lista enlazada
    ListaEnlazada listaEnlazada = new ListaEnlazada();

    ListaEnlazada listaGeneral = new ListaEnlazada();


    //Cantidad de minas
    int cantidadMinas;

    //Filas y columnas
    int numFilas = 8;
    int numColumnas = 8;

    //Matriz del tablero de Celdas
    Nodo[][] matrizTablero = new Nodo[numFilas][numColumnas];

    //Panel para el Jugador y el Bot Dummy
    public void crearPanelJuego(StackPane canva, int xCoords, int yCoords) {
        int xCoordsPrime = xCoords;

        //Recorrer la matriz y añadir los elementos
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {

                //Instamcia de la celda
                Celda celda = new Celda(i, j);

                //Crear Nodo
                Nodo nodo = new Nodo();

                //Caracteristicas de la Celda
                celda.setMinWidth(40);
                celda.setMinHeight(40);
                celda.setStyle("-fx-border-color: black");

                //Ubicación de la Celda
                celda.setTranslateX(xCoords);
                celda.setTranslateY(yCoords);

                //Accionar la celda
                celda.setOnAction(e -> estarMinadoDummy(e, nodo));
                celda.setOnContextMenuRequested(e -> celda.crearBandera());

                //Asignarle el dato que a a guardar
                nodo.setDato(celda);

                //Asignar en un índice I J el valor almacenado en esa posición
                matrizTablero[i][j] = nodo;

                //Añadir las Celdas al StackPane del JavaFX
                canva.getChildren().add(matrizTablero[i][j].getDato());

                //Mover la celda a la derecha para simular la matriz
                xCoords += 40;
            }

            xCoords = xCoordsPrime;
            yCoords += 40;

        }

        //Se crean las minas
        crearMinas(cantidadMinas);

        //Se crean las minas
        crearPistas();

    }

    //Panel para el Bot Advanced
    public void crearPanelJuegoNodos(StackPane canva, int xCoords, int yCoords) {
        int xCoordsPrime = xCoords;

        //Recorrer la matriz y añadir los elementos
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {

                //Instamcia de la celda
                Celda celda = new Celda(i, j);

                //Crear un Nodo y asignarle el dato a almacenar
                Nodo nodo = new Nodo();

                //Caracteristicas de la Celda
                celda.setMinWidth(40);
                celda.setMinHeight(40);
                celda.setStyle("-fx-border-color: black");

                //Ubicación de la Celda
                celda.setTranslateX(xCoords);
                celda.setTranslateY(yCoords);

                //Accionar la celda
                celda.setOnAction(e -> estarMinadoAdvanced(e,nodo));  //Implementar después
                celda.setOnContextMenuRequested(e -> celda.crearBandera());

                //Se asigna el dato que va a guardar el Nodo
                nodo.setDato(celda);

                //Enlazar Nodos e incrementar el tamaño de la lista
                listaEnlazada.insertarNodo(nodo);



                //Agregar los Nodos a la matriz
                matrizTablero[i][j] = nodo;

                //Añadir el Nodo al StackPane del JavaFX
                canva.getChildren().add(matrizTablero[i][j].getDato());

                //Mover la celda a la derecha para simular la matriz
                xCoords += 40;
            }

            xCoords = xCoordsPrime;
            yCoords += 40;


        }

        //Se crean las minas
        crearMinas(cantidadMinas);

        //Se crean las minas
        crearPistas();

        listaEnlazada.mostrarElementos();
        listaGeneral = listaEnlazada;
        System.out.println("----------------------------------------------------------");
        listaGeneral.mostrarElementos();


    }

    private void asignarTurnosDummy(){

        if (turnoBot){
            turnoJugador = true;
            turnoBot = false;
            algoritmoDummyBot();

        }else {

            turnoJugador = false;
            turnoBot = true;

        }

    }

    private void asignarTurnosAdvanced(){

        if (turnoBot){
            turnoJugador = true;
            turnoBot = false;
            algoritmoAdvancedBot();

        }else {

            turnoJugador = false;
            turnoBot = true;

        }

    }


    //Algoritmo del Bot dummy
    private void algoritmoDummyBot(){

        while (true) {
            int iRandom = (int) ((Math.random() * (7)) + 0);  //Indice i aleatorio
            int jRandom = (int) ((Math.random() * (7)) + 0);  //Indice j aleatorio
            if (!matrizTablero[iRandom][jRandom].getDato().isEstaRevelada()) {  //Solo si la celda no se ha revelado

                matrizTablero[iRandom][jRandom].getDato().fire();  //Hace la acción de clicar
                //matrizTablero[iRandom][jRandom].getDato().setStyle("-fx-background-color: #0c3ad2");
                matrizTablero[iRandom][jRandom].getDato().setEstaRevelada(true);  //Marca que el Bot la reveló

                juego.setLabelCeldaClickBot("i: " + iRandom + "-" + "j: " + jRandom);

                break;


            }
        }
    }

    private void algoritmoAdvancedBot(){
        //Se actualiza la lista enlazada general
        actualizarListaGeneral();

        Nodo nodoSeleccionado;

        //Se selecciona de forma aleatoria un elemento de la lista general

        do {
            int iRandom = (int) ((Math.random() * (7)) + 0);  //Indice i aleatorio
            int jRandom = (int) ((Math.random() * (7)) + 0);  //Indice j aleatorio

            nodoSeleccionado = listaGeneral.encontrarElemento(iRandom, jRandom);

        } while (nodoSeleccionado == null);

        //System.out.println("\n" + nodoSeleccionado.getDato());
        System.out.println("i: " + nodoSeleccionado.getDato().getI() + " - j: " + nodoSeleccionado.getDato().getJ());
        System.out.println("Iden: " + nodoSeleccionado.getDato().getIdentificador() + " NumPistas: " + nodoSeleccionado.getDato().getNumPistas());



    }

    private void actualizarListaGeneral(){

        //Se recorre el tablero buscando celdas reveladas eliminandolas de la lista general
        for (int i = 0;i < numFilas;i++){
            for (int j = 0; j < numColumnas;j++){

                //Si está revelada elimine ese Nodo del tablero
                if (matrizTablero[i][j].getDato().isEstaRevelada()){
                    listaGeneral.eliminarNodo(matrizTablero[i][j]);
                }

            }
        }

    }


    //Metodo que comprobará si la Celda posee una mina o no para el bot Dummy
    private void estarMinadoDummy(ActionEvent event, Nodo nodoCelda) {

        //Si el identificador de la Celda corresponde a una mina, mostrar que el jugador o el bot perdió
        if (nodoCelda.getDato().getIdentificador() == -1) {
            nodoCelda.getDato().setText("*");
            VentanaAlerta.display("Alerta","Se ha pisado una mina, perdiste");

        } else {

            if (!nodoCelda.getDato().isEstaRevelada()) {
                revelarCeldasSinPistas(nodoCelda.getDato().getI(),nodoCelda.getDato().getJ());
                //System.out.println(matrizTablero[nodoCelda.getDato().getI()][nodoCelda.getDato().getJ()].getDato().getNumPistas());
                asignarTurnosDummy();
            }
        }

    }

    //Metodo que comprobará si la Celda posee una mina o no para el bot Advanced
    private void estarMinadoAdvanced(ActionEvent event, Nodo nodoCelda) {

        //Si el identificador de la Celda corresponde a una mina, mostrar que el jugador o el bot perdió
        if (nodoCelda.getDato().getIdentificador() == -1) {
            nodoCelda.getDato().setText("*");
            VentanaAlerta.display("Alerta","Se ha pisado una mina, perdiste");

        } else {

            if (!nodoCelda.getDato().isEstaRevelada()) {
                revelarCeldasSinPistas(nodoCelda.getDato().getI(),nodoCelda.getDato().getJ());
                //System.out.println(matrizTablero[nodoCelda.getDato().getI()][nodoCelda.getDato().getJ()].getDato().getNumPistas());
                asignarTurnosAdvanced();
            }
        }

    }


    //Crear las minas en los tableros
    private void crearMinas(int numMinas){
        //Minas creadas en el Tablero
        int minasGeneradas = 0;

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

                        matrizTablero[i][j].getDato().setIdentificador(-1);
                        //matrizTablero[i][j].getDato().setText("*");

                        //Incrementa el total de minas generadas en el tablero
                        minasGeneradas = minasGeneradas + 1;

                    }
                }
            }
        }
    }

    //Crear las pistas alrededor de las minas
    private void crearPistas() {

        //System.out.printf("Entró");

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {

                //Preguntar por diferentes índices en la matriz para hacer el aumento de pistas
                if (matrizTablero[i][j].getDato().getIdentificador() == 1) {

                    //Verifica que no este fuera del tablero
                    if (i - 1 >= 0){
                        //Si arriba hay una mina, aumente el número de pistas
                        if (matrizTablero[i - 1][j].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }

                    //Verifica que no este fuera del tablero
                    if (i - 1 >= 0 && j + 1 <= numColumnas - 1){
                        //Si arriba derecha hay una mina, aumente el número de pistas
                        if (matrizTablero[i - 1][j + 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }

                    //Verifica que este dentro del tablero
                    if (j + 1 <= numColumnas - 1){
                        //Si a la derecha hay una mina, aumente el número de pistas
                        if (matrizTablero[i][j + 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }

                    //Verifica que este dentro del tablero
                    if (i + 1 <= numFilas - 1 && j + 1 <= numColumnas - 1){
                        //Si a la derecha abajo hay una mina, aumente el número de pistas
                        if (matrizTablero[i + 1][j + 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }


                    //Verifica que este dentro del tablero
                    if (i + 1 <= numFilas - 1) {
                        //Si abajo hay una mina, aumente el número de pistas
                        if (matrizTablero[i + 1][j].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }


                    //Verifica que este dentro del tablero
                    if (i + 1 <= numFilas - 1 && j - 1 >= 0) {
                        //Si abajo izquierda hay una mina, aumente el número de pistas
                        if (matrizTablero[i + 1][j - 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }


                    //Verifica que este dentro del tablero
                    if (j - 1 >= 0){
                        //Si a la izquierda hay una mina, aumente el número de pistas
                        if (matrizTablero[i][j - 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }


                    //Verifica que este dentro del tablero
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        //Si a la izquierda arriba hay una mina, aumente el número de pistas
                        if (matrizTablero[i - 1][j - 1].getDato().getIdentificador() == -1) {
                            matrizTablero[i][j].getDato().aumentarNumPistas();
                        }
                    }

                }


            }
        }
    }

    //Revela de forma recursiva las Celdas que no poseen pistas
    public void revelarCeldasSinPistas(int fila, int columnas){

        if (fila < 0 || columnas < 0 || fila >= numFilas || columnas >= numColumnas) {  //Si está fuera del tablero retorne nada
            return;
        }

        if (matrizTablero[fila][columnas].getDato().isEstaRevelada()) {  //Si la celda fue relevada salga de la ejecucion
            return;
        }

        if (matrizTablero[fila][columnas].getDato().getNumPistas() != 0) {  //Si la casilla tiene pistas, termine la ejecución

            matrizTablero[fila][columnas].getDato().revelarPista();
            matrizTablero[fila][columnas].getDato().setEstaRevelada(true);
            matrizTablero[fila][columnas].getDato().setDisable(true);

            return;
        }


        matrizTablero[fila][columnas].getDato().setEstaRevelada(true);  //Se marca la celda como revelada
        matrizTablero[fila][columnas].getDato().setStyle("-fx-background-color: #00CE33");  //Se le asigna un color
        matrizTablero[fila][columnas].getDato().setDisable(true);

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