package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        logger.debug("Program starting...");
        InternetRadio radio = new InternetRadio();

        radio.playStation("2");
        Thread.sleep(3000L);
        radio.playStation("11");
        Thread.sleep(3000L);

        radio.stopPlayback();
        logger.debug("Program finished");
    }

}
