package sample;

public class Nodo {

    //Dato que almacena la lista
    private Celda dato;
    //Al siguiente elemento que se va a apuntar
    private Nodo next;
    private Nodo fin;

    //Constructor
    public Nodo() {
        this.next = null;  //Empieza apuntando a nulo
    }

    //Devuelve el dato del nodo
    public Celda getDato() {
        return dato;
    }

    //Cambia el dato del nodo
    public void setDato(Celda dato) {
        this.dato = dato;
    }

    //Devuelve a cual esta apuntando
    public Nodo getNext() {
        return this.next;
    }

    //Se le asigna un nuevo apuntador
    public void setNext(Nodo next) {
        this.next = next;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }
}
