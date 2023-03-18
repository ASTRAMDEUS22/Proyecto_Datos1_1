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
            labelTiempo;


    /**
     * clase que carga la información en el archivo fxml dandole el diseño a la interfaz
     */

    public void elementosInterfaz() {

        Stage stage = new Stage();


        TableroJuego tableroJuego = new TableroJuego(10);

        //Creacion del canva donde se trabajará
        StackPane canva = new StackPane();

        //Botones
        empezarJuego = new Button();
        empezarJuego.setText("Empezar");
        empezarJuego.setOnAction(e -> tableroJuego.crearPanelJuego(canva, stage));

        empezarJuego.setTranslateX(130);
        empezarJuego.setTranslateY(-200);

        //Labels
        labelTiempo = new Label();
        labelTiempo.setText("Tiempo:");
        labelTiempo.setTranslateX(-20);
        labelTiempo.setTranslateY(-200);

        labelTotalMinas = new Label();
        labelTotalMinas.setText("Minas:");
        labelTotalMinas.setTranslateX(-150);
        labelTotalMinas.setTranslateY(-200);

        //Agregar elementos de java fx al canva
        canva.getChildren().addAll(
                empezarJuego,
                labelTiempo,
                labelTotalMinas

        );

        Scene panelJuego = new Scene(canva, 1000, 509);
        stage.setTitle("Minesweeper");
        stage.setScene(panelJuego);
        stage.show();


    }

}

