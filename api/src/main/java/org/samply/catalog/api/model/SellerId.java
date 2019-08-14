package org.samply.catalog.api.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class SellerId {

    private final String value;

}
