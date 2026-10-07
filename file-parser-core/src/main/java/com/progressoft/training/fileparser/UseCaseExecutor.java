package com.progressoft.training.fileparser;

import com.progressoft.training.fileparser.exception.FileParserException;

import java.io.FileNotFoundException;

public interface UseCaseExecutor {
    static <I, O> O run(UseCase<I, O> useCase, I input) {
        O output = null;
        try {
            output = useCase.execute(input);
        } catch (FileParserException e) {
            throw new FileParserException("There was an error with executing the use case.");
        }

        return output;
    }

    static <I, O> O runTimed(UseCase<I, O> useCase, I input) {
        long startTime = System.nanoTime();
        O output = null;
        try {
            output = useCase.execute(input);
        } catch (Exception e) {
            throw new FileParserException("There was an error with executing the use case.");
        }

        long endTime = System.nanoTime();
        long elapsed = endTime - startTime;
        System.out.println(useCase.getClass().getName() + " took " + elapsed + "ms");
        return output;
    }

}
