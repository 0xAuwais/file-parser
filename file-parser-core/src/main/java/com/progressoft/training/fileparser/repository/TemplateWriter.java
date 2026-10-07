package com.progressoft.training.fileparser.repository;

import com.progressoft.training.fileparser.domain.Template;

public interface TemplateWriter {
    public Template save(Template template);

    public void deleteByName(String name);
}
