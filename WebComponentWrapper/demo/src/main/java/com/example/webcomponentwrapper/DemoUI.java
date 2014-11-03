package com.example.webcomponentwrapper;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.prototype.webcomponentwrapper.WebComponentVaadinServlet;
import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.prototype.webcomponentwrapper.element.EventParam;
import com.vaadin.prototype.webcomponentwrapper.element.Import;
import com.vaadin.prototype.webcomponentwrapper.element.Tag;
import com.vaadin.prototype.webcomponentwrapper.element.WebComponentWrapper;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("valo")
public class DemoUI extends UI {
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
	public static class Servlet extends WebComponentVaadinServlet {
		
	}

    @Tag("paper-button")
    @Import("https://www.polymer-project.org/components/paper-button/paper-button.html")
    public static interface PaperButtonElement extends Element {
        void setRaised(boolean b);
    }

    @Tag("core-icon")
    @Import({
        "https://www.polymer-project.org/components/core-icon/core-icon.html",
        "https://www.polymer-project.org/components/core-icons/core-icons.html",
        "https://www.polymer-project.org/components/core-icons/av-icons.html",
        "https://www.polymer-project.org/components/core-icons/communication-icons.html",
        "https://www.polymer-project.org/components/core-icons/device-icons.html",
        "https://www.polymer-project.org/components/core-icons/editor-icons.html",
        "https://www.polymer-project.org/components/core-icons/hardware-icons.html",
        "https://www.polymer-project.org/components/core-icons/image-icons.html",
        "https://www.polymer-project.org/components/core-icons/maps-icons.html",
        "https://www.polymer-project.org/components/core-icons/notifiacation-icons.html",
        "https://www.polymer-project.org/components/core-icons/social-icons.html",
    })
    public static interface CoreIconElement extends Element {
        void setIcon(String icon);
    }

    public static interface PaperRpc extends ServerRpc {
        public void click(@EventParam("clientX") double x);
    }

    @Override
    protected void init(VaadinRequest request) {
        CssLayout main = new CssLayout();
        main.setSizeFull();
        setContent(main);

        Label label = new Label("Vaadin label");
        main.addComponent(label);

        WebComponentWrapper wrapper = new WebComponentWrapper();
        main.addComponent(wrapper);
        Document root = wrapper.getRoot();
        root.setAttribute("style", "overflow: visible;");

        PaperButtonElement button = Elements.create(PaperButtonElement.class);
        root.appendChild(button);
        button.setRaised(true);
        button.setInnerHtml("Hello <b>world</b>");

        CoreIconElement icon = Elements.create(CoreIconElement.class);
        icon.setIcon("bug-report");
        button.appendChild(icon);

        button.addEventListener(new PaperRpc() {
            @Override
            public void click(double x) {
                Notification.show("Clicked at " + x);
            }
        });


        Element input = Elements.create("input");
        input.setAttribute("type", "date");
        root.appendChild(input);
    }

}