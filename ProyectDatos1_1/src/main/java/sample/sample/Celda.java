package sample;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;

import java.io.IOException;

/**
 * Objeto que hereda las propiedades de un boton de JavaFX.
 */
public class Celda extends Button {
    //Atributos

    private int numPistas = 0;
    private int identificador = 1;
    private int i;
    private int j;
    private boolean estaRevelada = false;
    private int flag = 0;
    private boolean tieneBandera = false;

    //Instancia del Objeto
    Juego juego = new Juego();


    /**
     * Constructor del objeto
     * @param i índice I en la matriz
     * @param j índice J en la matriz
     */
    public Celda(int i,int j) {

        setStyle("-fx-background-color: #00CE33");

        this.i = i;
        this.j = j;

    }

    /**
     * Devuelve el número con el que se identifica la Celda.
     * @return número con el que se identifica la Celda
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Asigna un indicador de identiddd a la Celda.
     * @param identificador número que según sea 1 o -1, cambiará el tipo de identificación de la Celda.
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    /**
     * Incrementa el número de pistas en la Celda.
     */
    public void aumentarNumPistas(){
        numPistas += 1;
    }

    /**
     * Hace que el objeto Celda muestre de forma textual el valor total de Pistas que guarda.
     */
    public void revelarPista(){
        setText(String.valueOf(numPistas));
    }

    /**
     * Cambia el estilo de la Celda para simular la colocación de una mina
     */
    public void crearBandera(IODevice placaArduino) throws IOException, InterruptedException {

        var LED = placaArduino.getPin(7);
        LED.setMode(Pin.Mode.OUTPUT);

        if (flag == 0) {  //Crear la bandera

            if (!estaRevelada) {
                setStyle("-fx-background-color: #de00ff");
                this.tieneBandera = true;
                getStyleClass().add("inmutable");
                flag = 1;
                juego.disminuirMinas();


                LED.setValue(1);  //Encender el LED
                Thread.sleep(500);  //Esperar un segundo y 200 milisegundos
                LED.setValue(0);  //Apagar el LED


                System.out.println("Se encendió el LED");

            }

        }else {  //Eliminar la bandera
            if (!estaRevelada){
                setStyle("-fx-border-color: black");
                this.tieneBandera = false;
                flag = 0;
                juego.aumentarMinas();



            }
        }
    }

    /**
     * Devuelve el total de pistas almacenadas en la Celda.
     * @return Int del total de pistas.
     */
    public int getNumPistas() {
        return numPistas;
    }

    /**
     * Devuelve el índice I donde está almacenada la matriz.
     * @return Int del índice I.
     */
    public int getI() {
        return i;
    }

    /**
     * Devuelve el índice J donde está almacenada la matriz.
     * @return Int del índice J.
     */
    public int getJ() {
        return j;
    }

    /**
     * Devuelve un boolean sobre si la celda ha sido revelada.
     * @return un boolean.
     */
    public boolean isEstaRevelada() {
        return estaRevelada;
    }

    /**
     * Asigna un valor boolean al objeto Celda
     * @param estaRevelada Valor booleano deseado para el Objeto
     */
    public void setEstaRevelada(boolean estaRevelada) {
        this.estaRevelada = estaRevelada;
    }

    /**
     * Devuelve la existencia de la bandera en la Celda
     * @return Bool de existencia de una Bandera
     */
    public boolean isTieneBandera() {
        return tieneBandera;
    }
}
