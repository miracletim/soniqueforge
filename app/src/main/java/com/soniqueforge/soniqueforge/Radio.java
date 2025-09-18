package com.soniqueforge.soniqueforge;

public class Radio {
    private String name;
    private String frequency;

    private String streamLink;
    private String location;

    public Radio() {}

    public Radio(String name, String frequency, String streamLink, String location) {
        this.name = name;
        this.frequency = frequency;
        this.streamLink = streamLink;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStreamLink() {
        return streamLink;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
