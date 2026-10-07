package com.progressoft.training.fileparser.repository;

import com.progressoft.training.fileparser.domain.Template;

import java.util.List;
import java.util.Optional;

public interface TemplateReader {
    public Optional<Template> findByName(String name);

    public List<Template> findAll();
}
