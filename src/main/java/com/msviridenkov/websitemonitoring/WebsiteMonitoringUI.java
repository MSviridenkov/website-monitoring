package com.msviridenkov.websitemonitoring;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 *
 */
@Title("Website Monitoring")
@Theme("valo")
//@Widgetset("com.msviridenkov.websitemonitoring.MyAppWidgetset")
public class WebsiteMonitoringUI extends UI {

    Grid websiteList = new Grid();
    Button pingAll = new Button("Ping All");

    WebsiteService service = WebsiteService.createService();

    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }

    public void configureComponents() {
        pingAll.addClickListener(e -> pingAll());

        websiteList.setContainerDataSource(new BeanItemContainer<>(Website.class));
        websiteList.setColumnOrder("name", "url", "status", "responseTime");
        websiteList.removeColumn("id");
        refreshWebsites();
    }

    private void buildLayout() {
        VerticalLayout websites = new VerticalLayout(websiteList);
        HorizontalLayout gridAndButton = new HorizontalLayout(websites, pingAll);
        setContent(gridAndButton);
    }

    private void refreshWebsites() {
        websiteList.setContainerDataSource(new BeanItemContainer<>(Website.class, service.findAll()));
    }

    private void pingAll() {
        service.pingAll();
        refreshWebsites();
    }

    @WebServlet(urlPatterns = "/*", name = "WebsiteMonitoringUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WebsiteMonitoringUI.class, productionMode = false)
    public static class WebsiteMonitoringUIServlet extends VaadinServlet {
    }
}
