package sample;

/**
 * Objeto ListaEnlazada
 */
public class ListaEnlazada {

    private Nodo head;
    private Nodo last;
    private int size;

    /**
     * Constructor del Objeto
     */
    public ListaEnlazada() {
        this.head = null;
        this.size = 0;
        this.last = null;
    }

    /**
     * Método que devuelve el tamaño actual de la ListaEnlazada
     * @return Int del tamaño de la ListaEnlazada
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Método que inserta un elemento al final de la ListaEnlazada
     * @param dato Es el nuevo Nodo que será añadido a la lista
     */
    public void insertarNodo(Nodo dato) {

        //Si la lista esta vacia, el head y el last tendrán el valor del primer dato ingresado
        if (this.head == null){
            this.head = dato;
            this.last = dato;
            this.size++;
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

    /**
     * Método que busca un Nodo en concreto y lo elimina de la ListaEnlazada
     * @param nodoEliminar Es el nodo que se desea eliminar de la ListaEnlazada
     */
    public void eliminarNodo(Nodo nodoEliminar){
        Nodo nodoTemp = this.head;  //Se crea un nodo temporal para recorrer la lista
        Nodo nodoReferenciaAnterior = null;

        while (nodoTemp != null && nodoTemp.getDato() != nodoEliminar.getDato()){
            nodoReferenciaAnterior = nodoTemp;
            nodoTemp = nodoTemp.getNext();
        }


        if (nodoTemp != null) {
            //Si el head es igual al objeto a eliminar
            if (nodoReferenciaAnterior == null) {
                this.head = nodoTemp.getNext();
                this.size--;
            }
            else  {
                nodoReferenciaAnterior.setNext(nodoTemp.getNext());
                this.size--;
            }
        }
    }

    /**
     * Muestra todos los elementos de la ListaEnlazada
     */
    public void mostrarElementos(){

        /*
            Código extraído de la primera presentación de las estructuras de datos lineales del profesor Noguera
         */

        Nodo current = this.head;  //Se le asigna al current el valor del primer elemento de la lista

        while (current != null){

            System.out.println(current.getDato());


            current = current.getNext();  //Se asigna que el current sea el siguiente del mismo
        }

    }

    /**
     * Método que busca un elemento de la matriz por medio de los indices
     * @param i Indice de las filas
     * @param j Indice de las columnas
     * @return Devuelve el elemento que se desea encontrar
     */
    public Nodo encontrarElemento(int i,int j){

        //El current será igual que el primer elemento de la lista
        Nodo current = this.head;

        //Mientras el current sea un elemento de la lista, ejecute
        while (current != null){

            //Si el elemento tiene los mismos índices ingresados
            if (current.getDato().getI() == i && current.getDato().getJ() == j)
                break;  //Rompe el ciclo y va al return

            current = current.getNext();  //Se recorre la lista enlazada

        }

        return current;  //Devuelve el elemento que coincide

    }

}
