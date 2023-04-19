package sample;

/**
 * Objeto Nodo.
 */
public class Nodo {

    //Dato que almacena la lista
    private Celda dato;
    //Al siguiente elemento que se va a apuntar
    private Nodo next;
    private Nodo fin;

    /**
     * Constructor del Objeto.
     */
    public Nodo() {
        this.next = null;  //Empieza apuntando a nulo
    }

    /**
     * Metodo que devuelve el tipo de dato que almacena el Nodo.
     * @return Devuelve el Dato que está almacenando el Nodo.
     */
    public Celda getDato() {
        return dato;
    }

    /**
     * Metodo que le asigna un Dato a almacenar al Nodo.
     * @param dato Dato que se desea establecer para el Nodo.
     */
    public void setDato(Celda dato) {
        this.dato = dato;
    }

    /**
     * Metodo que devuelve el Nodo al cual está apuntando este Nodo
     * @return Nodo que se está apuntando.
     */
    public Nodo getNext() {
        return this.next;
    }

    /**
     * Metodo que cambia el apuntador del Nodo actual a por el que se le asigne.
     * @param next Nodo al cuál se apuntará
     */
    public void setNext(Nodo next) {
        this.next = next;
    }

}
