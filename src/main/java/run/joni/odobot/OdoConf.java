package run.joni.odobot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import run.joni.odobot.eventlisteners.EventListener;

import java.util.List;


@Configuration
@PropertySource("classpath:application.yml")
public class OdoConf {

    private static final Logger LOG = LogManager.getLogger(OdoConf.class.getName());

    @Value("${discord.token}")
    private String odoDiscordToken;

    private static String ODO_VERSION;

    // Static is callable without instantiating the class from main()
    public static String getOdoVersion() {
        return ODO_VERSION;
    }

    // @Value annotation can't inject static values so we need to use a setter
    @Value("${odobot.version}")
    public void setOdoVersionStatic(String odobotVersion) {
        OdoConf.ODO_VERSION = odobotVersion;
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        try {
            LOG.info("OdoBot version: " + ODO_VERSION);
            LOG.info("Creating Discord client...");
            // Create a new Discord client
            GatewayDiscordClient client = DiscordClientBuilder.create(odoDiscordToken)
                    .build()
                    .login()
                    .block();

            // Register event listeners in a loop
            eventListeners.forEach(listener -> {
                assert client != null;
                client.on(listener.getEventType())
                        .flatMap(listener::execute)
                        .onErrorResume(listener::handleError)
                        .subscribe();
            });

            return client;

        } catch (Exception e) {
            LOG.error("Error while creating Discord client", e);
            return null;
        }
    }
}
