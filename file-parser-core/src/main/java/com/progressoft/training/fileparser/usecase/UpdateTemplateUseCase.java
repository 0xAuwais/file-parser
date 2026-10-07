package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.repository.TemplateRepositoryInterface;

import java.io.FileNotFoundException;

public class UpdateTemplateUseCase implements UseCase<Template, Template> {
    private final TemplateRepositoryInterface templatesRepository;

    public UpdateTemplateUseCase(TemplateRepositoryInterface templates) {
        this.templatesRepository = templates;
    }

    @Override
    public Template execute(Template input) {
        if (templatesRepository.findByName(input.name()).isEmpty()) {
            throw new TemplateNotFoundException(input.name());
        }
        return templatesRepository.save(input);
    }
}
