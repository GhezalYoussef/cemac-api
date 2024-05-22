package sncf.reseau.cemac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.availability.AvailabilityProbesAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.sncf.reseau.jraf.ws.rest.config.frontend.ConfigFrontendPropertiesTS;

@SpringBootApplication
@Import({ConfigFrontendPropertiesTS.class, AvailabilityProbesAutoConfiguration.class})
public class ApiCemacApplication {

   public static void main(String[] args) {
      SpringApplication.run(ApiCemacApplication.class, args);
   }

}

