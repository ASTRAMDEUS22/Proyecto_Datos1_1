package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Contador que actualiza labels y les cambia el valor cada segundo.
 */
public class Contador extends Thread{
    private int segundos = 0;
    private int minutos = 0;
    private int limiteMinutos = 1000;
    private Label labelSegundos;
    private Label labelMinutos;

    /**
     * Constructor del objeto Contador
     * @param labelSegundos label donde se verá reflejado el cambio del tiempo cada segundo
     * @param labelMinutos label donde se vera reflejado el cambio del tiempo cada minuto.
     */
    public Contador(Label labelSegundos,Label labelMinutos) {
        this.labelSegundos = labelSegundos;
        this.labelMinutos = labelMinutos;
    }

    @Override
    /**
     * Ejecuta el contador y actualiza los labels utilizando un Hilo que se ejecuta con el Hilo principal de Java FX.
     */
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

            //Se ejecuta el Thread sobre el hilo principal de JavaFX
            Platform.runLater(() -> {
                labelSegundos.setText(String.valueOf(segundos));
                labelMinutos.setText(String.valueOf(minutos));

            });
        }
    }

}
