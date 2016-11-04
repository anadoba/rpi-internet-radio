package pl.nadoba.rpi.internet.radio;

import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class InternetRadio {

    private RadioConnector connector = new RadioConnector();
    private RadioStations stations = new RadioStations();
    private ExecutorService executor = Executors.newCachedThreadPool();

    private AtomicBoolean isPlaying = new AtomicBoolean(false);

    public void playStation(String stationKey) {
        if (isPlaying.get()) {
            stopPlayback();
        }

        URL stationUrl = stations.get(stationKey);
        Runnable playback = generatePlaybackRunnable(stationUrl);
        executor.execute(playback);
        isPlaying.set(true);
    }

    public void stopPlayback() {
        try {
            connector.stop();
            isPlaying.set(false);
        } catch (IOException e) {
            System.out.println("Exception when trying to close radio input stream");
            e.printStackTrace();
        }
    }

    private Runnable generatePlaybackRunnable(URL stationUrl) {
        return () -> {
            try {
                connector.playRadioStream(stationUrl);
            } catch (IOException e) {
                System.out.println("I/O exception occured while connecting to " + stationUrl.toString());
                e.printStackTrace();
            } catch (JavaLayerException e) {
                System.out.println("JLayer exception while trying to play " + stationUrl.toString());
                e.printStackTrace();
            }
        };
    }

}
