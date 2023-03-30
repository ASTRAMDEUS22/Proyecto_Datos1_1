package sample;

public class ListaEnlazada {

    private Nodo head;
    private int size;

    //Constructor
    public ListaEnlazada() {
        this.head = null;
        this.size = 0;
    }

    public boolean estaVacia() {
        return this.head == null;
    }

    //Devuelve el tamaño de la lista
    public int getSize() {
        return size;
    }


    //Inserta un elemento al principio de la lista
    public void insertarPrimero(Nodo dato) {
        Nodo nodotemp = new Nodo();
        nodotemp.setDato(dato.getDato());
        nodotemp.setNext(this.head);
        this.head = nodotemp;
        this.size++;
    }

    //Elimina un Nodo en cualquier parte de la lista
    public void eliminarNodo(Nodo nodoEliminar,ListaEnlazada listaGeneral){
        Nodo nodoTemp = listaGeneral.getHead();  //Se crea un nodo temporal para recorrer la lista
        Nodo nodoReferenciaAnterior = null;

        while (true){
            if (nodoTemp.getDato() == nodoEliminar.getDato()){

                nodoTemp.setNext(nodoEliminar.getNext());  //El nodo temporal apunta al siguiente del nodo que será eliminado
                nodoReferenciaAnterior.setNext(nodoTemp.getNext());  //El nodo anterior apunta al siguiente del nodo temporal
                nodoEliminar.setNext(null);  //Se elimina el puntero del nodo a eliminar
                break;

            }else {

                nodoReferenciaAnterior = nodoTemp;  //El anterior pasa a ser el nodo temporal
                nodoTemp = nodoTemp.getNext();  //Nodo temporal pasa a ser el Nodo al que esta apuntando

            }

        }




    }

    public Nodo getHead() {
        return head;
    }
}
