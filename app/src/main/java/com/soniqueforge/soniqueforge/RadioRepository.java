package com.soniqueforge.soniqueforge;

import java.util.ArrayList;
import java.util.List;

public class RadioRepository {
    public static List<Radio> getStations() {
        List<Radio> stations = new ArrayList<>();

        stations.add(new Radio("Step FM", "99.8", "http://139.162.195.139:8020/radio.mp3", "Mbale"));
        stations.add(new Radio("Big FM", "97.6", "https://stream.zeno.fm/30mcqwg0yuhvv", "Mbale"));
        stations.add(new Radio("City FM", "98.1", "http://cast1.asurahosting.com:8332/stream", "Kampala"));
        stations.add(new Radio("KKCR-Kagadi Kibaale Community", "91.7", "https://cast5.asurahosting.com/proxy/habinco5/stream", "Kibaale"));
        stations.add(new Radio("Open Gate FM", "103.2", "http://139.162.195.139:8010/radio.mp3", "Mbale"));
        stations.add(new Radio("Record FM", "97.7", "http://139.162.195.139:8010/radio.mp3", "Kampala"));
        stations.add(new Radio("Smart FM", "89", "https://smart.radioca.st/stream", "Jinja"));
        stations.add(new Radio("Tropical FM", "88.4", "https://stream.zeno.fm/rs0m1rd5g2zuv", "Kampala"));
        stations.add(new Radio("CBS Radio Buganda FM Ey Obujjajja", "88.8", "http://s5.voscast.com:9908/EYOBUJJAJJA", "Kampala"));
        stations.add(new Radio("Kodheyo FM", "89.4", "https://khodeyo.radioca.st/stream", "Jinja"));

        return stations;
    }
}
