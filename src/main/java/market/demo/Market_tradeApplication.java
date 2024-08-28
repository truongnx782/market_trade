package market.demo;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class Market_tradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Market_tradeApplication.class, args);
    }
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzdfzxbmy",
                "api_key", "989353715489512",
                "api_secret", "eESrJoEBv4SfjogF1UJmuDeAIm8",
                "secure", true
        ));
    }
}
