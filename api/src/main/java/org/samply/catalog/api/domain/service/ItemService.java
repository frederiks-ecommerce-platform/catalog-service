package org.samply.catalog.api.domain.service;

import java.util.UUID;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.samply.catalog.api.domain.model.Item;
import org.samply.catalog.api.domain.model.ItemCreationDTO;
import org.samply.catalog.api.domain.model.SellerId;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;

@ApplicationScoped
public class ItemService {

    @Inject
    @Stream("item-created-log")
    Emitter<KafkaMessage<String, Item>> emitter;

    public Single<Item> addItem(ItemCreationDTO item, SellerId sellerId) {
        Item itemCreatedEvent = createItemEvent(item, sellerId);
        CompletionStage<Void> itemCreatedAck = postItemCreatedEvent(itemCreatedEvent).ack();

        return Completable.fromFuture(itemCreatedAck.toCompletableFuture())
                          .toSingle(() -> itemCreatedEvent);
    }

    private Item createItemEvent(ItemCreationDTO item, SellerId sellerId) {

        return Item.newBuilder()
                   .setId(UUID.randomUUID().toString())
                   .setSellerId(sellerId.getValue())
                   .setTitle(item.getTitle())
                   .setDescription(item.getDescription())
                   .setCategory(item.getCategory())
                   .setPrice(item.getPrice())
                   .build();
    }

    private KafkaMessage<String, Item> postItemCreatedEvent(Item item) {
        KafkaMessage<String, Item> msg = KafkaMessage.of(item.getId(), item);
        emitter.send(msg);
        return msg;
    }

    @Incoming("item-created-log")
    CompletionStage<Void> initStream(KafkaMessage<String, Item> msg) {
        return msg.ack();
    }

}
