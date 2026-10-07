package com.progressoft.training.fileparser.repository;

import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.exception.TemplateStoreException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonTemplateRepository implements TemplateRepositoryInterface {
    public record TemplateFile(List<Template> templates) {
    }

    private Map<String, Template> templates = new LinkedHashMap<>();
    private TemplateFile templatesFile = new TemplateFile(new ArrayList<>());
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path storeFile;

    public JsonTemplateRepository(Path storeFile) {
        this.storeFile = storeFile;
        this.load(storeFile);
        for (Template template : templatesFile.templates) {
            templates.put(template.name(), template);
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
        if (template == null) {
            throw new FileParserException("Collection of templates must not be null");
        }
        Template temp = templates.put(template.name(), template);
        this.flush(storeFile);
        return temp;
    }

    public void deleteByName(String name) {
        Optional<Template> found = this.findByName(name);
        if (templates.remove(name) == null) {
            throw new TemplateNotFoundException(name);
        }
        this.flush(storeFile);
    }


    private void load(Path file) throws TemplateStoreException {
        if (Files.notExists(file)) {
            return;
        }
        try {
            this.templatesFile = mapper.readValue(file.toFile(), TemplateFile.class);
            //this.save(template);

        } catch (Exception e) {
            throw new TemplateStoreException("Error occurred while loading template file, cant read it?", e);
        }
    }

    private void flush(Path file) throws TemplateStoreException {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), new TemplateFile(List.copyOf(templates.values())));
        } catch (Exception e) {
            throw new TemplateStoreException("Error occurred while loading template file", e);
        }
    }
}
