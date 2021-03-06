package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.LockSupport;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Program starting...");

        InternetRadio radio = new InternetRadio();
        RadioController controller = new RadioController(radio);

        // TODO: find a cleaner way to keep the listeners inside 'controller' running
        for(;;) LockSupport.park();

        /*
        radio.playStation(RemoteControlCommand.CH2);
        Thread.sleep(3000L);
        radio.playStation(RemoteControlCommand.CH9);
        Thread.sleep(3000L);

        radio.stopPlayback();


        logger.info("Program finished");
        */
    }

}
