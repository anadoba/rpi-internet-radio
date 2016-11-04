package pl.nadoba.rpi.internet.radio;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Program starting...");
        InternetRadio radio = new InternetRadio();

        radio.playStation("2");
        Thread.sleep(3000L);
        radio.playStation("1");
        Thread.sleep(3000L);

        radio.stopPlayback();
        System.out.println("Program finished");
    }

}
