package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.DuplicateTemplateException;
import com.progressoft.training.fileparser.repository.TemplateRepositoryInterface;

import java.io.FileNotFoundException;

public class CreateTemplateUseCase implements UseCase<Template, Template> {
    private final TemplateRepositoryInterface templatesRepository;

    public CreateTemplateUseCase(TemplateRepositoryInterface templates) {
        this.templatesRepository = templates;
    }

    @Override
    public Template execute(Template input){
        templatesRepository.findByName(input.name()).ifPresent(existing -> {
            throw new DuplicateTemplateException(input.name());
        });
        return templatesRepository.save(input);
    }
}
