package org.samply.catalog.api.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {

    private final String message;

}
