package com.progressoft.training.fileparser;

import java.io.FileNotFoundException;

public interface UseCase<I,O> {
    O execute(I input);
}
