package com.msviridenkov.websitemonitoring;


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
}
