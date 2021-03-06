package com.zonesoft.addressbook.tryouts;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.time.LocalDateTime;
public class HelloMain {
    public static void main(String[] args) {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML); // HTML5 option was deprecated in 3.0.0
        templateEngine.setTemplateResolver(resolver);
        Context ct = new Context();
        ct.setVariable("name", "foo");
        ct.setVariable("date", LocalDateTime.now().toString());
        System.out.println(templateEngine.process("greeting.html", ct));
    }
}