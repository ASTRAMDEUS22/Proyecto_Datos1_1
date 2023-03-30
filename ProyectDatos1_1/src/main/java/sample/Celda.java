package sample;
import javafx.scene.control.Button;

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
     * @return devuelve el Int del indicador.
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
        if (numPistas != 0) {
            setText(String.valueOf(numPistas));
        }
    }

    /**
     * Crea una bandera que marca la ubicación de una posible mina.
     */
    public void crearBandera(){
        if (!estaRevelada) {
            setStyle("-fx-background-color: #de00ff");
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
}
