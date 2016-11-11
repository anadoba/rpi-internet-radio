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
        urlConnection.addRequestProperty("Cache-Control","no-cache");
        urlConnection.addRequestProperty("Connection","keep-alive");
        urlConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        urlConnection.addRequestProperty("Pragma","no-cache");
urlConnection.addRequestProperty("Range","bytes=0-");
urlConnection.addRequestProperty("Accept-Encoding","identity;q=1, *;q=0");
urlConnection.addRequestProperty("Accept-Language","pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4");

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
