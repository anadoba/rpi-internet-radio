package pl.nadoba.rpi.internet.radio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RadioStations {

    private Map<String, URL> stations;

    public RadioStations() {
        try {
            stations = new HashMap<String, URL>() {{
                put("1", new URL("http://n-2-5.dcs.redcdn.pl/sc/o2/Eurozet/live/antyradio.livx?audio=5"));
                put("2", new URL("http://radio.flex.ru:8000/radionami"));
            }};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL get(String key) {
        return stations.containsKey(key) ? stations.get(key) : stations.get("1");
    }
}
