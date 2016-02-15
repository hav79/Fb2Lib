package ru.fb2lib.templater;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by hav on 18.01.16.
 */
public class PageGenerator {
    private static PageGenerator ourInstance = new PageGenerator();

//    private static final String HTML_DIR = "/templates";
    private String htmlDir;
    private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    public static PageGenerator getInstance() {
        return ourInstance;
    }

    private PageGenerator() {
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
//            cfg.setDirectoryForTemplateLoading();
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    public void setHtmlDir(String htmlDir) {
        this.htmlDir = htmlDir;

        try {
            cfg.setTemplateLoader(new FileTemplateLoader(new File(this.htmlDir)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
