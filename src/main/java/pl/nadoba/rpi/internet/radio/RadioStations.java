package pl.nadoba.rpi.internet.radio;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RadioStations {

    private final static Logger logger = Logger.getLogger(RadioStations.class);

    private Map<String, URL> stations;

    public RadioStations() {
        try {
            stations = new HashMap<String, URL>() {{
                put("1", new URL("http://n-2-5.dcs.redcdn.pl/sc/o2/Eurozet/live/antyradio.livx?audio=5"));
                put("2", new URL("http://radio.flex.ru:8000/radionami"));
            }};
        } catch (MalformedURLException e) {
            logger.error("Error when setting up radio stations list on app start", e);
        }
    }

    public URL get(String key) {
        if (stations.containsKey(key)) {
            return stations.get(key);
        } else {
            URL stationUrl = stations.get("1");
            logger.warn("Unexpected station key requested: " + key + " - falling back to default " + stationUrl.toString());
            return stationUrl;
        }
    }
}
