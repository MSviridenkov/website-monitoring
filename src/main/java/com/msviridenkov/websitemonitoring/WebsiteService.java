package com.msviridenkov.websitemonitoring;

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

    public void ping(Website website) {
        websites.put(website.getId(), website.ping());
    }

    public List<Website> findAll() {
        return new ArrayList<>(websites.values());
    }

    public void pingAll() {
        for (Website website : websites.values()) {
            ping(website);
        }
    }
}
