package com.progressoft.training.fileparser.repository;

import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;

import java.util.*;

public class InMemoryTemplateRepository implements TemplateRepositoryInterface{
    LinkedHashMap<String, Template> templates = new LinkedHashMap<>();

    public InMemoryTemplateRepository() {
    }

    public InMemoryTemplateRepository(Collection<Template> template) {
        if (template == null) {
            throw new FileParserException("Collection of templates must not be null");
        }
        for (Template temp : template) {
            templates.put(temp.name(), temp);
        }
    }

    public Optional<Template> findByName(String name) {
        Optional<Template> found = Optional.empty();
        for (String tempname : templates.keySet()) {
            if (tempname.equals(name)) {
                found = Optional.of(templates.get(name));
            }
        }
        return found;
    }

    public List<Template> findAll() {
        return List.copyOf(templates.values());
    }

    public Template save(Template template) {
        return templates.put(template.name(), template);
    }

    public void deleteByName(String name) {
        Optional<Template> found = this.findByName(name);
        if (templates.remove(name) == null) {
            throw new TemplateNotFoundException(name);
        }
    }
}
