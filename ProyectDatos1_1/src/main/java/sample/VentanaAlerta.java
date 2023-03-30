package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaAlerta {
    /**
     * Metodo que genera una ventana donde se mostrará la alerta.
     * @param title Título de la ventana generada.
     * @param text Descripción de la ventana.
     */

    //Código basado en el siguiente video: https://www.youtube.com/watch?v=SpL3EToqaXA
    public static void display(String title,String text) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        //LABEL
        Label mensaje = new Label();
        mensaje.setText(text);
        mensaje.setTranslateY(-20.0);

        //BUTTON
        Button closeButton = new Button();
        closeButton.setText("OK");
        closeButton.setTranslateY(25);
        closeButton.setOnAction(e -> stage.close());

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