package ch.uzh.ifi.hase.soprafs21;

import com.fasterxml.jackson.databind.Module;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OpenAPI2SpringBoot implements CommandLineRunner {

    @Override
    public void run(String... arg0) {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) {
        new SpringApplication(OpenAPI2SpringBoot.class).run(args);
    }

    static class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

    @Bean
    public GeometryFactory geometryFactory() {
        // 3857 is the most common spatial reference identifier, also used in Google Maps.
        return new GeometryFactory(new PrecisionModel(), 3857);
    }

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "https://sopra-fs21-group-02-client.herokuapp.com")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .allowedHeaders("Content-Type", "Authorization");
            }
        };
    }


    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}
