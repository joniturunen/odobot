package run.joni.odobot.eventlisteners;

import discord4j.core.object.entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    // Set message contents to default string value
    private final String odoResponse = "Commander, laws change depending on who's making them. Cardassians one day, Federation the next... but justice is justice.";
    private static final Logger LOG = LogManager.getLogger(MessageListener.class.getName());
    public Mono<Void> processCommand(Message eventMessage) {
        // Log the fact that the message was received
        LOG.info("Received message from " + eventMessage.getAuthor().get().getUsername() + " : " + eventMessage.getContent());
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!odo"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(odoResponse))
                .then();
    }
}