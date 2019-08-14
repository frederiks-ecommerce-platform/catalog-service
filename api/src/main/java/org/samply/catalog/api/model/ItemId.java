package org.samply.catalog.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemId {
    @JsonValue
    private final String value;
}
