package com.msviridenkov.websitemonitoring;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 *
 */
@Title("Website Monitoring")
@Theme("valo")
public class WebsiteMonitoringUI extends UI {

    Grid websiteList = new Grid();
    Button pingAll = new Button("Ping all");
    Button newWebsite = new Button("New website");

    WebsiteForm websiteForm = new WebsiteForm();

    WebsiteService service = WebsiteService.createService();

    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }

    public void configureComponents() {
        pingAll.addClickListener(e -> pingAll());
        newWebsite.addClickListener(e -> websiteForm.newWebsite(new Website()));

        websiteList.setContainerDataSource(new BeanItemContainer<>(Website.class));
        websiteList.setColumnOrder("name", "url", "status", "responseTime");
        websiteList.removeColumn("id");
        websiteList.setSelectionMode(Grid.SelectionMode.SINGLE);
        websiteList.addSelectionListener(e -> websiteForm.showActions((Website) websiteList.getSelectedRow()));
        refreshWebsites();
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(pingAll, newWebsite);
        actions.setWidth("100%");
        pingAll.setWidth("100%");
        newWebsite.setWidth("100%");
        actions.setExpandRatio(pingAll, 1);
        actions.setExpandRatio(newWebsite, 1);

        VerticalLayout leftLayout = new VerticalLayout(actions, websiteList);
        leftLayout.setSizeFull();
        websiteList.setWidth("100%");
        websiteList.setHeight("100%");
        leftLayout.setExpandRatio(websiteList, 1);

        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setFirstComponent(leftLayout);
        hsplit.setSecondComponent(websiteForm);

        setContent(hsplit);
    }

    public void refreshWebsites() {
        websiteList.setContainerDataSource(new BeanItemContainer<>(Website.class, service.findAll()));
        websiteForm.setVisible(false);
    }

    private void pingAll() {
        service.pingAll();
        refreshWebsites();
    }

    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = WebsiteMonitoringUI.class, productionMode = false)
    public static class WebsiteMonitoringUIServlet extends VaadinServlet {
    }
}
