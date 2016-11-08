package pl.nadoba.rpi.internet.radio;

import javazoom.jl.decoder.JavaLayerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class InternetRadio {

    private final static Logger logger = Logger.getLogger(InternetRadio.class);

    private RadioConnector connector = new RadioConnector();
    private RadioStations stations = new RadioStations();
    private ExecutorService executor = Executors.newCachedThreadPool();

    private AtomicBoolean isPlaying = new AtomicBoolean(false);

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public void playStation(RemoteControlCommand stationKey) {
        if (isPlaying.get()) {
            stopPlayback();
        }

        URL stationUrl = stations.get(stationKey);
        Runnable playback = generatePlaybackRunnable(stationUrl);
        executor.execute(playback);
        isPlaying.set(true);
        logger.info("Started to play station " + stationUrl.toString());
    }

    public void stopPlayback() {
        try {
            connector.stop();
            isPlaying.set(false);
            logger.info("Stopped the playback");
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
                logger.error("I/O exception occured while connecting to " + stationUrl.toString(), e);
            } catch (JavaLayerException e) {
                logger.error("JLayer exception while trying to play " + stationUrl.toString(), e);
            }
        };
    }

}
