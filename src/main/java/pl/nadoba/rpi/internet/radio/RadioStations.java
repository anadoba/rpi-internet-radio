package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RadioStations {

    private final static Logger logger = Logger.getLogger(RadioStations.class);

    private final RemoteControlCommand defaultStationKey = RemoteControlCommand.CH1;

    private Map<RemoteControlCommand, URL> stations;
    private RemoteControlCommand currentStationKey = defaultStationKey;

    public RadioStations() {
        try {
            stations = new HashMap<RemoteControlCommand, URL>() {{
                put(RemoteControlCommand.CH1, /* ANTY RADIO */ new URL("http://n-2-5.dcs.redcdn.pl/sc/o2/Eurozet/live/antyradio.livx?audio=5"));
                put(RemoteControlCommand.CH2, /* ESKA ROCK */ new URL("http://waw04.ic2.scdn.smcloud.net/t041-1.mp3"));
                put(RemoteControlCommand.CH3, /* RADIO ZET */ new URL("http://n-2-15.dcs.redcdn.pl/sc/o2/Eurozet/live/audio.livx?audio=5"));
                put(RemoteControlCommand.CH4, /* RMF FM */ new URL("http://31.192.216.4:8000/rmf_fm"));
                put(RemoteControlCommand.CH5, /* RMF MAXXX */ new URL("http://217.74.72.10:8000/rmf_maxxx"));
                put(RemoteControlCommand.CH6, /* RMF CLASSIC */ new URL("http://195.150.20.242:8000/rmf_classic"));
                put(RemoteControlCommand.CH7, /* ESKA TROJMIASTO */ new URL("http://waw04.ic1.scdn.smcloud.net/t048-1.mp3"));
                put(RemoteControlCommand.CH8, /* RADIO GDANSK */ new URL("http://stream.task.gda.pl:8000/rg1"));
                put(RemoteControlCommand.CH9, /* TOK FM */ new URL("http://wroclaw.radio.pionier.net.pl:8000/pl/tuba10-1.mp3"));
            }};
        } catch (MalformedURLException e) {
            logger.error("Error when setting up radio stations list on app start", e);
        }
    }

    public URL get(RemoteControlCommand key) {
        if (stations.containsKey(key)) {
            currentStationKey = key;
        } else {
            currentStationKey = defaultStationKey;
            logger.warn("Unexpected station key requested: " + key + " - falling back to default " + currentStationKey);
        }
        return stations.get(currentStationKey);
    }

    public RemoteControlCommand getCurrentStationKey() {
        return currentStationKey;
    }

    public RemoteControlCommand previousStationKey() {
        int currentStationNumber = Integer.valueOf(currentStationKey.toString().substring(2));
        int previousStationNumber = currentStationNumber - 1;

        if (previousStationNumber == 0) {
            currentStationKey = RemoteControlCommand.CH9;
        } else {
            currentStationKey = RemoteControlCommand.valueOf("CH" + previousStationNumber);
        }

        return currentStationKey;
    }

    public RemoteControlCommand nextStationKey() {
        int currentStationNumber = Integer.valueOf(currentStationKey.toString().substring(2));
        int nextStationNumber = currentStationNumber + 1;

        if (nextStationNumber == 10) {
            currentStationKey = RemoteControlCommand.CH1;
        } else {
            currentStationKey = RemoteControlCommand.valueOf("CH" + nextStationNumber);
        }

        return currentStationKey;
    }
}
