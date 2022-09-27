package run.joni.odobot.configuration;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    public GatewayDiscordClient gatewayDiscordClient() {
        LOG.info("Creating Discord client...");
        LOG.info("OdoBot version: " + ODO_VERSION);
        return DiscordClientBuilder.create(odoDiscordToken)
                .build()
                .login()
                .block();
    }

}
