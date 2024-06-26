package org.pao.audiolibrarypao;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Audio Library", version = "v1"))
public class AudioLibraryPaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioLibraryPaoApplication.class, args);
    }
}
