package pl.nadoba.rpi.internet.radio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testResumingPlayback() {
        InternetRadio radio = new InternetRadio();
        radio.resumePlayback();
        assertTrue(radio.isPlaying());
    }

    public void testRadioStations() {
        RadioStations stations = new RadioStations();

        assertEquals(RemoteControlCommand.CH1, stations.getCurrentStationKey());

        stations.nextStationKey();
        stations.nextStationKey();
        stations.nextStationKey();
        stations.nextStationKey();
        stations.previousStationKey();

        assertEquals(RemoteControlCommand.CH4, stations.getCurrentStationKey());
    }
}
