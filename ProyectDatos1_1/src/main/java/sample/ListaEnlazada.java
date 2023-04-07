package sample;

public class ListaEnlazada {

    private Nodo head;
    private Nodo last;
    private int size;

    //Constructor
    public ListaEnlazada() {
        this.head = null;
        this.size = 0;
        this.last = null;
    }

    public boolean estaVacia() {
        return this.head == null;
    }

    //Devuelve el tamaño de la lista
    public int getSize() {
        return this.size;
    }

    //Inserta un elemento al principio de la lista
    public void insertarNodo(Nodo dato) {

        //Si la lista esta vacia, el head y el last tendrán el valor del primer dato ingresado
        if (this.head == null){
            this.head = dato;
            this.last = dato;
        }else {

            //Instancia de un Nodo temporal
            Nodo nodoTemp = new Nodo();

            //Se le asigna el mismo valor del nodo que está ingresando
            nodoTemp.setDato(dato.getDato());

            //El ultimo apunta al nuevo nodo que se ha creado
            this.last.setNext(nodoTemp);

            //El nodoTemp pasa a ser el nuevo Nodo final
            this.last = nodoTemp;

            //Incrementa el tamaño de la lista
            this.size++;
        }
    }

    //Elimina un Nodo en cualquier parte de la lista
    public void eliminarNodo(Nodo nodoEliminar){
        Nodo nodoTemp = this.head;  //Se crea un nodo temporal para recorrer la lista
        Nodo nodoReferenciaAnterior = null;

        while (true){

            //Si el current es igual al objeto a eliminar y si es el head
            if (nodoTemp.getDato() == nodoEliminar.getDato() && nodoEliminar == this.head){
                this.head = this.head.getNext();
                this.size--;
            }
            //Si el current es igual al objeto a eliminar
            else if (nodoTemp.getDato() == nodoEliminar.getDato()){
                nodoReferenciaAnterior.setNext(nodoTemp.getNext());
                this.size--;
                break;


            }else {

                nodoReferenciaAnterior = nodoTemp;  //El anterior pasa a ser el nodo temporal
                nodoTemp = nodoTemp.getNext();  //Nodo temporal pasa a ser el Nodo al que esta apuntando

            }

        }

    }

    public void mostrarElementos(){

        /*
            Código extraído de la primera presentación de las estructuras de datos lineales del profesor Noguera
         */

        Nodo current = this.head;  //Se le asigna al current el valor del primer elemento de la lista

        int i = 0;

        while (current != null){


            //Muestra en forma de filas y columnas las celdas que contienen pistas
            /*
            if (i < 8){
                System.out.print(current.getDato().getIdentificador());
            }else if (i == 8){
                i = 0;
                System.out.println("\n");
            }*/

            i ++;

            current = current.getNext();  //Se asigna que el current sea el siguiente del mismo
        }



    }

    public Nodo encontrarElemento(int i,int j){

        //El current será igual que el primer elemento de la lista
        Nodo current = this.head;

        //Mientras el current sea un elemento de la lista, ejecute
        while (current != null){

            //Si el elemento tiene los mismos índices ingresados
            if (current.getDato().getI() == i || current.getDato().getJ() == j)
                break;  //Rompe el ciclo y va al return

            current = current.getNext();  //Se recorre la lista enlazada

        }

        //Devuelve el Nodo que será seleccionado
        return current;  //Devuelve el elemento que coincide

    }

    public Nodo getHead() {
        return head;
    }
}
