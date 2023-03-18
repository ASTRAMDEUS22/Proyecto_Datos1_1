package sample;
import javafx.scene.control.Button;

/**
 * Objeto que hereda las propiedades de un boton de JavaFX
 */
public class Celda extends Button {
    //Atributos

    int numPistas = 0;
    int identificador = 1;


    /**
     * Constructor del Objeto Celda
     */
    public Celda() {

        setStyle("-fx-background-color: #00CE33");

        if (identificador == -1){  //Se da una probabilidad del 15% que en ese cuadro se genere una mina
            setText("M");

        }
    }

    //Getter del identificador del objeto
    public int getIdentificador() {
        return identificador;
    }

    //Setter del identificador del objeto
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    //Incrementar numero de pistas en 1
    public void aumentarNumPistas(){
        numPistas += 1;
        setText(String.valueOf(numPistas));
    }

    public int getNumPistas() {
        return numPistas;
    }

}
