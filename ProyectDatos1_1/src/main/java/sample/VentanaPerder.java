package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaPerder {
    /**
     * Muestra todos los elementos graficos de la interfaz
     * @param ventanaJuego es el escenario del "Juego"
     */

    //Código basado en el siguiente video: https://www.youtube.com/watch?v=SpL3EToqaXA
    public static void display(Stage ventanaJuego) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Haz perdido");

        //LABEL
        Label mensaje = new Label();
        mensaje.setText("Seleccionaste una mina, perdiste");
        mensaje.setTranslateY(-20.0);

        //BUTTON
        Button closeButton = new Button();
        closeButton.setText("Cerrar");
        closeButton.setTranslateY(25);
        closeButton.setOnAction(e -> {
            stage.close();
            ventanaJuego.close();
        });

        StackPane canva = new StackPane();
        canva.getChildren().addAll(
                mensaje,
                closeButton
        );
        canva.setAlignment(Pos.CENTER);

        Scene ventana = new Scene(canva,300,100);
        stage.setScene(ventana);
        stage.showAndWait();


    }

}
