package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.firmata4j.IODevice;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import org.firmata4j.PinEventListener;
import org.firmata4j.firmata.FirmataMessageFactory;
import org.firmata4j.firmata.FirmataDevice;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;

public class Tablero implements PinEventListener {

    //Constructor del tablero
    public Tablero(int cantidadMinas, Stage stage, IODevice placaArduino) {
        //System.out.println("1");
        this.cantidadMinas = cantidadMinas;
        this.stageJuego = stage;
        this.placaArduino = placaArduino;
    }

    Stage stageJuego;
    Juego juego = new Juego();

    private boolean turnoJugador = false;
    private boolean turnoBot = true;

    //Instancia de la lista enlazada
    private ListaEnlazada listaEnlazada = new ListaEnlazada();

    private ListaEnlazada listaGeneral = new ListaEnlazada();

    private static int i;
    private static int j;


    //Cantidad de minas
    private int cantidadMinas;

    //Minas faltantes
    private int minasFaltantes = cantidadMinas;

    //Filas y columnas
    private final int numFilas = 8;
    private final int numColumnas = 8;

    IODevice placaArduino;

    //Matriz del tablero de Celdas
    Nodo[][] matrizTablero = new Nodo[numFilas][numColumnas];

    //PulseCounter counter;

    @Override
    public void onModeChange(IOEvent ioEvent){
    }

    /**
     * Detecta los eventos enviados desde el Arduino a Java
     * @param ioEvent Almacena el evento registrado.
     */
    @Override
    public void onValueChange(IOEvent ioEvent){
        var eventoBoton = ioEvent.getPin();

        int iAnterior = i,jAnterior = j;


        if (eventoBoton.getValue() == 1){  //Si el boton es presionado
            if (eventoBoton.getIndex() == 12){
                if (j - 1 >= 0) {  //Si J no se fue al lado negativo, disminúyala
                    j--;
                }
            }else if (eventoBoton.getIndex() == 11){
                if (j + 1 < numColumnas) {  //Si J no se fue del tablero, auméntela
                    j++;
                }
            }else if (eventoBoton.getIndex() == 10){
                if (i - 1 >= 0){//Si I no se fue al lado negativo, disminúyala
                    i--;
                }
            }else if (eventoBoton.getIndex() == 9){
                if (i + 1 < numFilas) {  //Si I no se fue del tablero, auméntela
                    i++;
                }
            }else if (eventoBoton.getIndex() == 8){
                if (eventoBoton.getValue() == 1){  //Boton presionado
                    Platform.runLater(() ->{
                        matrizTablero[i][j].getDato().fire();
                    });
                }
            }
            if (matrizTablero[iAnterior][jAnterior].getDato().isTieneBandera()){
                matrizTablero[iAnterior][jAnterior].getDato().setStyle("-fx-background-color: #de00ff");
            }else {
            matrizTablero[iAnterior][jAnterior].getDato().setStyle("-fx-border-color: #000");
            }
            matrizTablero[i][j].getDato().setStyle("-fx-border-color: #f50000");

            System.out.println("I: " + i + " J: " + j);
            System.out.println("---------------------");

        }


    }




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
                celda.setOnAction(e -> {  //Este try catch fue producto de añadir el throw exeption del metodo estarMinadoDummy
                    try {
                        estarMinadoDummy( nodo);
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                celda.setOnContextMenuRequested(e -> {
                    try {
                        celda.crearBandera(placaArduino);
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });

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

    /**
     * Crea el tablero pero con Nodos enlazados
     * @param canva Lugar donde se agregan elementos gráficos
     * @param xCoords Ubicación en el canva
     * @param yCoords Ubicación en el canva
     */
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
                celda.setOnAction(e -> {
                    try {
                        estarMinadoAdvanced(nodo);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });  //Implementar después
                celda.setOnContextMenuRequested(e -> {
                    try {
                        celda.crearBandera(placaArduino);
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                //Se asigna el dato que va a guardar el Nodo
                nodo.setDato(celda);

                //Enlazar Nodos e incrementar el tamaño de la lista
                listaGeneral.insertarNodo(nodo);



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

        //Se crean las pistas
        crearPistas();

        //listaEnlazada.mostrarElementos();
        //listaGeneral = listaEnlazada;
        listaGeneral.mostrarElementos();
        System.out.println("----------------------------------------------------------");


    }

    /**
     * Asigna los turnos del bot dummy
     */
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

    /**
     * Asigna los turnos en el bot avanzado
     */
    private void asignarTurnosAdvanced() {

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

    /**
     * Algoritmo del bot dummy
     */
    private void algoritmoDummyBot(){

        int iRandom,jRandom,max=7,min=0;

        while (true) {
            Random numRandomI = new Random();
            Random numRandomJ = new Random();

            iRandom = numRandomI.nextInt((max - min) + 1) + min;
            jRandom = numRandomJ.nextInt((max - min) + 1) + min;

            if (!matrizTablero[iRandom][jRandom].getDato().isEstaRevelada()) {  //Solo si la celda no se ha revelado

                matrizTablero[iRandom][jRandom].getDato().fire();  //Hace la acción de clicar
                //matrizTablero[iRandom][jRandom].getDato().setStyle("-fx-background-color: #0c3ad2");
                matrizTablero[iRandom][jRandom].getDato().setEstaRevelada(true);  //Marca que el Bot la reveló

                juego.setLabelCeldaClickBot("i: " + iRandom + "-" + "j: " + jRandom);

                break;


            }
        }
    }

    /**
     * Intento del algoritmo del bot Advanced
     */
    private void algoritmoAdvancedBot(){
        //Se actualiza la lista enlazada general
        actualizarListaGeneral();

        Nodo nodoSeleccionado;

        int iRandom,jRandom,max = 7,min = 0;

        //Se selecciona de forma aleatoria un elemento de la lista general
        do {

            Random numRandomI = new Random();  //Nuevo número aleatorio
            Random numRandomJ = new Random();  //Nuevo número aleatorio

            //Fórmula para obtener un número aleatorio en X rango de valores
            iRandom = numRandomI.nextInt((max - min) + 1) + min;
            jRandom = numRandomJ.nextInt((max - min) + 1) + min;

            //Teniendo los índices aleatorios se busca el elemento en la lista general
            nodoSeleccionado = listaGeneral.encontrarElemento(iRandom, jRandom);

        } while (nodoSeleccionado == null);

        //Mostrar cuál fue el elemento según el índice que fue elegido
        juego.setLabelCeldaClickBot("i: " + nodoSeleccionado.getDato().getI() + "-" + "j: " + nodoSeleccionado.getDato().getJ());

        //Acción del clicar
        nodoSeleccionado.getDato().fire();

        //System.out.println("\n" + nodoSeleccionado.getDato());
        System.out.println("i: " + nodoSeleccionado.getDato().getI() + " - j: " + nodoSeleccionado.getDato().getJ());
        System.out.println("Iden: " + nodoSeleccionado.getDato().getIdentificador() + " NumPistas: " + nodoSeleccionado.getDato().getNumPistas());

    }


    /**
     * Actualiza los elementos de la lista general
     */
    private void actualizarListaGeneral(){

        //Se recorre el tablero buscando celdas reveladas eliminándolas de la lista general
        for (int i = 0;i < numFilas;i++){
            for (int j = 0; j < numColumnas;j++){

                //Si está revelada elimine ese Nodo del tablero
                if (matrizTablero[i][j].getDato().isEstaRevelada()){
                    listaGeneral.eliminarNodo(matrizTablero[i][j]);

                }

            }
        }
        //Muestra solo los Nodos con celdas sin revelar
        listaGeneral.mostrarElementos();
        //Tamaño de la lista General
        System.out.println(listaGeneral.getSize());

    }


    //Metodo que comprobará si la Celda posee una mina o no para el bot Dummy

    /**
     * Comprueba la existencia de una mina en el Tablero del bot dummy
     * @param nodoCelda Celda donde ocurre el evento
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    private void estarMinadoDummy(Nodo nodoCelda) throws IOException, InterruptedException {
        VentanaAlerta ventanaAlerta = new VentanaAlerta();
        var buzzerPin6 = placaArduino.getPin(6);  //Buzzer del PIN 6
        buzzerPin6.setMode(Pin.Mode.OUTPUT);
        //Si el identificador de la Celda corresponde a una mina, mostrar que el jugador o el bot perdió
        if (nodoCelda.getDato().getIdentificador() == -1) {
            nodoCelda.getDato().setText("*");

            buzzerPin6.setValue(1);  //Enciende el buzzer


            Thread.sleep(250);

            buzzerPin6.setValue(0);  //Apaga el buzzer

            Thread.sleep(250);

            buzzerPin6.setValue(1);  //Enciende el buzzer


            Thread.sleep(250);

            buzzerPin6.setValue(0);  //Apaga el buzzer

            ventanaAlerta.display("Alerta","Se ha pisado una mina, perdiste");
            //ventanaAlerta.cerrarVentana(stageJuego);

        } else {

            if (!nodoCelda.getDato().isEstaRevelada()) {
                revelarCeldasSinPistas(nodoCelda.getDato().getI(),nodoCelda.getDato().getJ());

                buzzerPin6.setValue(1);  //Enciende el buzzer


                Thread.sleep(250);

                buzzerPin6.setValue(0);  //Apaga el buzzer

                asignarTurnosDummy();
            }

        }

    }

    //Metodo que comprobará si la Celda posee una mina o no para el bot Advanced

    /**
     * Comprueba la existencia de una mina en el Tablero del bot dummy
     * @param nodoCelda Celda donde ocurre el evento
     * @throws IOException IOException
     */
    private void estarMinadoAdvanced( Nodo nodoCelda) throws IOException {
        VentanaAlerta ventanaAlerta = new VentanaAlerta();

        //Si el identificador de la Celda corresponde a una mina, mostrar que el jugador o el bot perdió
        if (nodoCelda.getDato().getIdentificador() == -1) {
            nodoCelda.getDato().setText("*");
            ventanaAlerta.display("Alerta","Se ha pisado una mina, perdiste");
            ventanaAlerta.cerrarVentana(stageJuego);


        } else {

            if (!nodoCelda.getDato().isEstaRevelada()) {
                revelarCeldasSinPistas(nodoCelda.getDato().getI(),nodoCelda.getDato().getJ());
                asignarTurnosAdvanced();
            }
        }

    }


    //Crear las minas en los tableros

    /**
     * Crea las minas en el tablero
     * @param numMinas minas ingresadas por el jugador
     */
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
                        //matrizTablero[i][j].getDato().setText("*");  //Testeo para ver minas

                        //Incrementa el total de minas generadas en el tablero
                        minasGeneradas = minasGeneradas + 1;

                    }
                }
            }
        }
    }

    //Crear las pistas alrededor de las minas

    /**
     * Crea las pistas alrededor de las Celdas
     */
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

    /**
     * Revela las casillas sin pistas o minas
     * @param fila filas del tablero
     * @param columnas columnas del tablero
     */
    private void revelarCeldasSinPistas(int fila, int columnas){

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

    /**
     * Devuelve I
     * @return int I
     */
    public static int getI() {
        return i;
    }

    /**
     * Asigna I
     * @param i int I
     */
    public static void setI(int i) {
        Tablero.i = i;
    }

    /**
     * Obtiene J
     * @return Int J
     */
    public static int getJ() {
        return j;
    }

    /**
     * Asigna J
     * @param j int J
     */
    public static void setJ(int j) {
        Tablero.j = j;
    }
}