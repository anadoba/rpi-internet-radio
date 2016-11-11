package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;
import org.lirc.LIRCException;
import org.lirc.util.SimpleLIRCClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class RadioController {

    private final static String pulseAudioDeviceId = "1";
    private final static int startupVolume = 70;
    private final static int volumeStep = 10;
    private final static int maxVolume = 150;
    private final static int minVolume = 1;
    private final static Logger logger = Logger.getLogger(RadioController.class);
    private final File configFile = new File(this.getClass().getClassLoader().getResource("internetRadio.lirc").getFile());

    private int volume;
    private SimpleLIRCClient lirc;
    private InternetRadio radio;

    public RadioController(InternetRadio radio) {
        try {
            lirc = new SimpleLIRCClient(configFile);
            String[] setVolumeArgs = new String[] {"pactl", "set-sink-volume", pulseAudioDeviceId, startupVolume + "%"};
            new ProcessBuilder(setVolumeArgs).start();
            volume = startupVolume;
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
                    case VOL_DOWN:
                        int decreasedVolume = volume - volumeStep;
                        if (decreasedVolume < minVolume) break;

                        String[] decreaseVolumeArgs = new String[] {"pactl", "set-sink-volume", pulseAudioDeviceId, "--", "-" +decreasedVolume + "%"};
                        try {
                            new ProcessBuilder(decreaseVolumeArgs).start();
                            volume = decreasedVolume;
                        } catch (IOException e) {
                            logger.error("Error when decreasing volume - " + Arrays.toString(decreaseVolumeArgs), e);
                        }
                        break;
                    case VOL_UP:
                        int increasedVolume = volume + volumeStep;
                        if (increasedVolume > maxVolume) break;
                        String[] increaseVolumeArgs = new String[] {"pactl", "set-sink-volume", pulseAudioDeviceId, "--", "+" +increasedVolume + "%"};
                        try {
                            new ProcessBuilder(increaseVolumeArgs).start();
                            volume = increasedVolume;
                        } catch (IOException e) {
                            logger.error("Error when increasing volume - " + Arrays.toString(increaseVolumeArgs), e);
                        }
                        break;
                    default: // used by CH buttons
                        radio.playStation(command);
                        break;
                }
            });
        } catch (LIRCException e) {
            logger.error("LIRC failure - cfg file is " + configFile.toString(), e);
        } catch (IOException e) {
            logger.error("IO exception when reading the config file " + configFile.toString() + " or setting startup volume - " + startupVolume, e);
        }
    }
}
