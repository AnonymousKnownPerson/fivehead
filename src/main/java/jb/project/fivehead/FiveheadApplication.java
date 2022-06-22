package jb.project.fivehead;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FiveheadApplication {
    private static final Logger log = LogManager.getLogger();
    public static void main(String[] args) {
        SpringApplication.run(FiveheadApplication.class, args);

    }

}
