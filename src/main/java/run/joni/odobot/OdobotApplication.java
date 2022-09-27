package run.joni.odobot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OdobotApplication {

    // Instantiate logger
    private static final Logger LOG = LogManager.getLogger(OdobotApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(OdobotApplication.class, args);


    }




}
