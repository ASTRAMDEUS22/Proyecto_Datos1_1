package sample;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Permite interactuar con JavaFX y realizar cambios a los elementos gráficos
 */
public class Juego {

    //Definir objetos de la interfaz

    //Botones
    Button empezarJuego;

    //Labels
    Label labelTotalMinas,
            labelTiempo,
            labelContSegundos,
            labelContMinutos,
            labelSeparador;

    static Label labelCeldaClickBot;

    Stage stage = new Stage();



    /**
     * clase que carga la información en el archivo fxml dandole el diseño a la interfaz
     */

    public void elementosInterfaz(int cantidadMinas,int identificador) {

        //

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setText("Empezar");
        empezarJuego.setOnAction(e -> ejecutarJuego(canva,cantidadMinas,identificador));
        empezarJuego.setTranslateX(130);
        empezarJuego.setTranslateY(-200);

        //Labels
        labelTiempo = new Label();
        labelTiempo.setText("Tiempo:");
        labelTiempo.setTranslateX(-90);
        labelTiempo.setTranslateY(-200);

        labelContSegundos = new Label();
        labelContSegundos.setText("");
        labelContSegundos.setTranslateX(0);
        labelContSegundos.setTranslateY(-200);

        labelContMinutos = new Label();
        labelContMinutos.setText("");
        labelContMinutos.setTranslateX(-15);
        labelContMinutos.setTranslateY(-200);

        labelSeparador = new Label();
        labelSeparador.setText(":");
        labelSeparador.setTranslateX(-9);
        labelSeparador.setTranslateY(-200);


        labelTotalMinas = new Label();
        labelTotalMinas.setText("Minas:");
        labelTotalMinas.setTranslateX(-300);
        labelTotalMinas.setTranslateY(-200);

        labelCeldaClickBot = new Label();
        //labelCeldaClickBot.setText("");
        labelCeldaClickBot.setTranslateX(220);
        labelCeldaClickBot.setTranslateY(-100);


        //Agregar elementos de java fx al canva
        canva.getChildren().addAll(
                empezarJuego,
                labelTiempo,
                labelTotalMinas,
                labelContSegundos,
                labelContMinutos,
                labelSeparador,
                labelCeldaClickBot

        );

        //Se define un Stage donde se añadirán los elementos
        Scene panelJuego = new Scene(canva, 1000, 509);
        stage.setTitle("Minesweeper");
        stage.setScene(panelJuego);
        stage.show();
    }

    //Ejecuta el Thread encargado del tiempo
    public void ejecutarContador() {

        Contador contador = new Contador(labelContSegundos,labelContMinutos);

        if (contador.isAlive()) {  //Esto no esta sirviendo ;_;
            contador.interrupt();
        } else {
            //Se inicia el Thread
            contador.start();
        }
    }

    public void ejecutarJuego(StackPane canva,int cantidadMinas,int identificador){

        //Ejecuta la creación de un Panel u otro dependiendo de la dificultad
        if (identificador == 0) {

            //Instancia del tablero del jugador
            Tablero tablero = new Tablero(cantidadMinas);

            //Ejecución de la interfaz del tablero jugador
            tablero.crearPanelJuego(canva, -150, -100);
        }else {

            //Instancia del tablero del jugador
            Tablero tablero = new Tablero(cantidadMinas);

            //Ejecucion de la interfaz del tablero jugador
            tablero.crearPanelJuegoNodos(canva,-150,-100);


        }



        //Se ejecuta el tiempo
        //ejecutarContador();
    }

    //Devuelve el Stage correspondiente a este Objeto
    public Stage getStage() {
        return stage;
    }

    //Metodo que actualiza el indicador de donde clickea el bot
    public void setLabelCeldaClickBot(String texto){
        labelCeldaClickBot.setText(texto);
    }


}

