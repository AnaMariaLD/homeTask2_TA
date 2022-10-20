package org.example.properties.conventors;

import org.example.enumeration.SupportedBrowsers;

import static org.example.enumeration.SupportedBrowsers.*;

public class SupportedBrowserConverter {

    public static SupportedBrowsers valueOfWebBrowser(String webBrowserName){
        return switch (webBrowserName){
            case "local_chrome" -> LOCAL_CHROME;
            case "local_firefox" -> LOCAL_FIREFOX;
            case "local_edge" -> LOCAL_EDGE;
            default -> throw new IllegalArgumentException();
        };
    }
}
