package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;
import org.lirc.LIRCException;
import org.lirc.util.SimpleLIRCClient;

import java.io.File;
import java.io.IOException;

public class RadioController {

    private final static Logger logger = Logger.getLogger(RadioController.class);

    private SimpleLIRCClient lirc;
    private InternetRadio radio;

    public RadioController(File configFile, InternetRadio radio) {
        try {
            lirc = new SimpleLIRCClient(configFile);
            lirc.addIRActionListener(commandString -> {
                final RemoteControlCommand command = RemoteControlCommand.valueOf(commandString);
                logger.info("Remote button " + command + " pressed.");
                switch (command) {
                    default: // used by CH buttons
                        radio.playStation(command);
                        break;
                }
            });
        } catch (LIRCException e) {
            logger.error("LIRC failure - cfg file " + configFile.toString(), e);
        } catch (IOException e) {
            logger.error("IO exception when reading the file " + configFile.toString(), e);
        }
    }
}
