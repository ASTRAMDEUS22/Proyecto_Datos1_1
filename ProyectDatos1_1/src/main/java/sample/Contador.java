package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Contador extends Thread{
    int segundos = 0;
    int minutos = 0;
    int limiteMinutos = 1000;
    Label labelSegundos;
    Label labelMinutos;

    public Contador(Label labelSegundos,Label labelMinutos) {
        this.labelSegundos = labelSegundos;
        this.labelMinutos = labelMinutos;
    }

    @Override
    public void run(){
        while (minutos < limiteMinutos){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }

                segundos++;

                if (segundos >= 60) {
                    minutos++;
                    segundos = 0;
                }

            Platform.runLater(() -> {
                labelSegundos.setText(String.valueOf(segundos));
                labelMinutos.setText(String.valueOf(minutos));

            });
        }
    }

}
