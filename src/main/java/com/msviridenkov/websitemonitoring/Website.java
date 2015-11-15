package com.msviridenkov.websitemonitoring;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by msviridenkov on 14.11.15.
 */
public class Website {
    private Long id;

    private String name = "";
    private String url = "";
    private String status = "";
    private String responseTime = "";

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

    public String getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        if (status) {
            this.status = "Up";
        } else {
            this.status = "Down";
        }

    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = Long.toString(responseTime);
    }
    public void setStringResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Website ping() {
        String url = this.getUrl();
        if (url == "http://") {
            url = "";
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);

            long startTime = System.currentTimeMillis();
            connection.connect();
            long pingTime = System.currentTimeMillis() - startTime;

            this.setStatus(connection.getResponseCode() == 200);
            this.setResponseTime(pingTime);
        } catch (IOException exception) {
            this.setStatus(false);
            this.setStringResponseTime("");
        }

        return this;
    }
}
