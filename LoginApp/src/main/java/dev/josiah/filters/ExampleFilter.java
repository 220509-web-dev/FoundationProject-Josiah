package dev.josiah.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ExampleFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return true;
    }
}
