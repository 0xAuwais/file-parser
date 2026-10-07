package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.repository.TemplateWriter;

public class DeleteTemplateUseCase implements UseCase<String,Void> {
    private final TemplateWriter templatesRepository;

    public DeleteTemplateUseCase(TemplateWriter templates) {
        this.templatesRepository = templates;
    }
    @Override
    public Void execute(String input){
        templatesRepository.deleteByName(input);
        return null;
    }
}
