package com.msviridenkov.websitemonitoring;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by msviridenkov on 15.11.15.
 */
public class WebsiteForm extends FormLayout {
    Button save = new Button("Save", this::save);
    Button cancel = new Button("Cancel", this::cancel);
    TextField name = new TextField("Name");
    TextField url = new TextField("Url");
    Button pingButton = new Button("Ping", this::ping);
    Button editButton = new Button("Edit", this::editButton);

    Website website;

    BeanFieldGroup<Website> formFieldBindings;

    public WebsiteForm() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout formActions = new HorizontalLayout(save, cancel);
        formActions.setSpacing(true);

        HorizontalLayout actions = new HorizontalLayout(pingButton, editButton);
        actions.setSpacing(true);

        addComponents(actions, name, url, formActions);
    }

    public void save(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();

            getUI().service.save(website);

            String msg = String.format("Saved '%s %s'.",
                    website.getName(),
                    website.getUrl());
            Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
            getUI().refreshWebsites();
        } catch (FieldGroup.CommitException e) {
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Notification.Type.TRAY_NOTIFICATION);
        setVisible(false);
        getUI().websiteList.select(null);
    }

    public void newWebsite(Website website) {
        setVisible(website != null);
        setMyVisible(false);
        doForm(website);
    }

    public void doForm(Website website) {
        this.website = website;
        if(website != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(website, this);
            if (url.getValue() == "") {
                url.setValue("http://");
            }
            name.focus();
        }
    }

    public void editButton(Button.ClickEvent event) {
        setMyVisible(false);
        doForm(website);
    }

    public void showActions(Website website) {
        this.website = website;
        setVisible(website != null);
        setMyVisible(true);
    }

    private void setMyVisible(boolean visible) {
        pingButton.setVisible(visible);
        editButton.setVisible(visible);
        save.setVisible(!visible);
        cancel.setVisible(!visible);
        name.setVisible(!visible);
        url.setVisible(!visible);
    }

    public void ping(Button.ClickEvent event) {
        getUI().service.ping(website);
        getUI().refreshWebsites();
    }

    @Override
    public WebsiteMonitoringUI getUI() {
        return (WebsiteMonitoringUI) super.getUI();
    }
}
