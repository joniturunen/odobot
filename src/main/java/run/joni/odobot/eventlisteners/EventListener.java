package run.joni.odobot.eventlisteners;

import discord4j.core.event.domain.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event> {
    Logger LOG = LogManager.getLogger(EventListener.class.getName());

    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        LOG.error("Error while processing event", error);
        return Mono.empty();
    }

}
