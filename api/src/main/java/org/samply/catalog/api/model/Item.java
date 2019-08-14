package org.samply.catalog.api.model;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class Item {

    private final ItemId id;
    private final SellerId sellerId;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final Category category;

}
