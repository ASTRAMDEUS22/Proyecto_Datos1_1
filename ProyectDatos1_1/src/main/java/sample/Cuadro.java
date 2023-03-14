package sample;
import javafx.scene.control.Button;

/**
 * Objeto que hereda las propiedades de un boton de JavaFX
 */
public class Cuadro extends Button {
    //Atributos
    private boolean hayMina = false;  //Inicialización de la variable

    int cantidadMinas;

    /**
     * Constructor del Objeto Cuadro
     */
    public Cuadro() {

        double numRandom = Math.random();  //Se guarda el resultado del numero random

        setStyle("-fx-background-color: #00CE33");

        if (numRandom > 0.9){  //Se da una probabilidad del 15% que en ese cuadro se genere una mina
            hayMina = true;
        }else{
            hayMina = false;
        }

    }

    //Getter
    public int getCantidadMinas() {
        return cantidadMinas;
    }

    //Setter
    public void setCantidadMinas(int cantidadMinas) {
        this.cantidadMinas = cantidadMinas;
    }

    //Getter hay mina
    public boolean getterHayMina() {  //Getter que devuelve si existe una mina en el Cuadro
        return hayMina;
    }

}
