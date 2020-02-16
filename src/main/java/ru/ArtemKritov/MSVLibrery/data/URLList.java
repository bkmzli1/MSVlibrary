package ru.ArtemKritov.MSVLibrery.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class URLList {
    String name, url;
    public static final Logger logger = LogManager.getLogger();

    public URLList(String name, String url) {
        logger.trace("addURL: " + name + "/" + url);
        this.name = name;
        this.url = url;
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

}
