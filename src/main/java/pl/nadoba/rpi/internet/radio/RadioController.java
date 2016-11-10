package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;
import org.lirc.LIRCException;
import org.lirc.util.SimpleLIRCClient;

import java.io.File;
import java.io.IOException;

public class RadioController {

    private final static Logger logger = Logger.getLogger(RadioController.class);

    private final File configFile = new File(this.getClass().getClassLoader().getResource("internetRadio.lirc").getFile());

    private SimpleLIRCClient lirc;
    private InternetRadio radio;

    public RadioController(InternetRadio radio) {
        try {
            lirc = new SimpleLIRCClient(configFile);
            lirc.addIRActionListener(commandString -> {
                final RemoteControlCommand command = RemoteControlCommand.valueOf(commandString);
                logger.debug("Remote button " + command + " pressed.");
                switch (command) {
                    case PLAY_PAUSE:
                        if (radio.isPlaying()) {
                            radio.stopPlayback();
                        } else {
                            radio.playStation(RemoteControlCommand.CH1);
                        }
                        break;
                    case PREV_CH:
                        radio.playPreviousStation();
                        break;
                    case NEXT_CH:
                        radio.playNextStation();
                        break;
                    // TODO: execute volume-related commands below, possibly add some private field with current volume
                    case VOL_DOWN:
                        break;
                    case VOL_UP:
                        break;
                    default: // used by CH buttons
                        radio.playStation(command);
                        break;
                }
            });
        } catch (LIRCException e) {
            logger.error("LIRC failure - cfg file is " + configFile.toString(), e);
        } catch (IOException e) {
            logger.error("IO exception when reading the config file " + configFile.toString(), e);
        }
    }
}
