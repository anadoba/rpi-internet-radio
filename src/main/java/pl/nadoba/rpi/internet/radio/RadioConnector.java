package pl.nadoba.rpi.internet.radio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RadioConnector {

    private HttpURLConnection urlConnection;
    private InputStream inputStream;
    private Player player;


    public void playRadioStream(URL url) throws IOException, JavaLayerException {
        // Connection
        urlConnection = (HttpURLConnection) url.openConnection();

        // Connecting
        urlConnection.connect();

        // Playing
        inputStream = urlConnection.getInputStream();
        player = new Player(inputStream);
        player.play();
    }

    public void stop() throws IOException {
        player.close();
        inputStream.close();
        urlConnection.disconnect();
    }
}
