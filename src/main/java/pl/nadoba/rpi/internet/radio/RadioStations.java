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
                put(RemoteControlCommand.CH1, new URL("http://n-2-5.dcs.redcdn.pl/sc/o2/Eurozet/live/antyradio.livx?audio=5"));
                put(RemoteControlCommand.CH2, new URL("http://radio.flex.ru:8000/radionami"));
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
