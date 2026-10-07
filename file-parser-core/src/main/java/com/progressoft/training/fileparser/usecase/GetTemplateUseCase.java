package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.repository.TemplateReader;

import java.util.Optional;

public class GetTemplateUseCase implements UseCase<String, Optional<Template>> {
    private final TemplateReader templates;

    public GetTemplateUseCase(TemplateReader templates) {
        this.templates = templates;
    }

    @Override
    public Optional<Template> execute(String input) {
        return templates.findByName(input);
    }

}
