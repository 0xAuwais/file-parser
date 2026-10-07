package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.domain.ListTemplatesQuery;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.repository.TemplateReader;

import java.util.List;

public class ListTemplatesUseCase implements UseCase<ListTemplatesQuery, List<Template>> {
    private final TemplateReader templates;

    public ListTemplatesUseCase(TemplateReader templates) {
        this.templates = templates;
    }

    @Override
    public List<Template> execute(ListTemplatesQuery input) {
        return templates.findAll();
    }
}
