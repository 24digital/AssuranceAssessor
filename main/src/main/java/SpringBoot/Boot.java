package SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Marion on 8/29/2014.
 */
@ComponentScan
@EnableAutoConfiguration
public class Boot {

    public static void main(String[] args)
    {
        SpringApplication.run(Boot.class, args);
    }
}
