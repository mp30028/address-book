package com.zonesoft.addressbook.services.rendering;

import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class HtmlView {
	
	private static final String PREFIX = "/templates/";
	private static final String SUFFIX = ".html";
	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final TemplateMode TEMPLATE_MODE = TemplateMode.HTML;
	
	private final TemplateEngine templateEngine ;
	
	
	public HtmlView() {
		this.templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(PREFIX);
        resolver.setSuffix(SUFFIX);
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        resolver.setTemplateMode(TEMPLATE_MODE);
        templateEngine.setTemplateResolver(resolver);		
	}
	
	
    public String create(String templatePath, Map<String, Object> model) {
        Context context = new Context();
        for (String key: model.keySet()) {
        	context.setVariable(key, model.get(key));
		}
        return templateEngine.process(templatePath, context);
    }
	
	
}
