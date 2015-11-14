package com.msviridenkov.websitemonitoring;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by msviridenkov on 14.11.15.
 */
public class WebsiteService {

    static String[] names = {"Google", "Yandex", "OK", "VK"};
    static String[] urls = {"http://google.ru", "http://yandex.ru", "http://ok.ru", "http://vk.com"};

    private HashMap<Long, Website> websites = new HashMap<>();
    private long nextId = 0;

    private static WebsiteService instance;

    public static WebsiteService createService() {
        if (instance == null) {
            final WebsiteService websiteService = new WebsiteService();

            for (int i = 0; i < names.length; i++) {
                Website website = new Website();
                website.setName(names[i]);
                website.setUrl(urls[i]);
                websiteService.save(website);
            }

            instance = websiteService;
        }

        return instance;
    }

    public void save(Website website) {
        if (website.getId() == null) {
            website.setId(nextId++);
        }

        websites.put(website.getId(), website);
    }

    public List<Website> findAll() {
        return new ArrayList<>(websites.values());
    }

    public void pingAll() {
        for (Website website : websites.values()) {
            websites.put(website.getId(), ping(website));
        }
    }

    public static Website ping(Website website) {
        String url = website.getUrl();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);

            long startTime = System.currentTimeMillis();
            connection.connect();
            long pingTime = System.currentTimeMillis() - startTime;

            website.setAvailability(connection.getResponseCode() == 200);
            website.setPingTime(pingTime);
        } catch (IOException exception) {
            website.setAvailability(false);
        }

        return website;
    }
}
