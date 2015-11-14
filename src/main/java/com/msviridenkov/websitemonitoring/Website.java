package com.msviridenkov.websitemonitoring;


/**
 * Created by msviridenkov on 14.11.15.
 */
public class Website {
    private Long id;

    private String name = "";
    private String url = "";
    private String availability = "";
    private String pingTime = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        if (availability) {
            this.availability = "Yes";
        } else {
            this.availability = "No";
        }

    }

    public String getPingTime() {
        return pingTime;
    }

    public void setPingTime(long pingTime) {
        this.pingTime = Long.toString(pingTime);
    }
}
