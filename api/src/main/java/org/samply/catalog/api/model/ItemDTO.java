package org.samply.catalog.api.model;

import java.math.BigDecimal;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemDTO {

    private final ItemId id;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final Category category;

}
