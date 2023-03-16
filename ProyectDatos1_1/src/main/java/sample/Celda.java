package sample;
import javafx.scene.control.Button;

/**
 * Objeto que hereda las propiedades de un boton de JavaFX
 */
public class Celda extends Button {
    //Atributos
    private boolean hayMina = false;  //Inicialización de la variable

    int cantidadMinas;
    int numPistas = 0;
    int identificador = 0;

    /**
     * Constructor del Objeto Celda
     */
    public Celda() {

        double numRandom = Math.random();  //Se guarda el resultado del numero random



        setStyle("-fx-background-color: #00CE33");

        if (numRandom > 0.8){  //Se da una probabilidad del 15% que en ese cuadro se genere una mina
            hayMina = true;
            setText("M");
            identificador = 1;
        }else{
            hayMina = false;
            
        }



    }

    //Getter cantidad de minas
    public int getCantidadMinas() {
        return cantidadMinas;
    }

    //Getter numero de pistas
    public int getNumPistas(){
        return numPistas;
    }

    //Incrementar numero de pistas en 1
    public void aumentarNumPistas(){
        if (identificador == 0) {

            numPistas += 1;
            setText(String.valueOf(numPistas));
        }
    }

    //Setter
    public void setCantidadMinas(int cantidadMinas) {
        this.cantidadMinas = cantidadMinas;
    }

    //Getter hay mina
    public boolean getterHayMina() {  //Getter que devuelve si existe una mina en el Celda
        return hayMina;
    }

}
