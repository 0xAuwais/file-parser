package com.progressoft.training.fileparser.mapper;

import com.progressoft.training.fileparser.domain.ParsedRow;

@FunctionalInterface
public interface RowMapper<T> {
    T map(ParsedRow row);
}
