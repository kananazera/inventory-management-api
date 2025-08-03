package az.inventory.inventorymanagementapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocation = uploadDir.replace("\\", "/");

        if (!resourceLocation.startsWith("file:")) {
            if (resourceLocation.matches("^[a-zA-Z]:/.*")) {
                resourceLocation = "file:///" + resourceLocation;
            } else {
                resourceLocation = "file:" + resourceLocation;
            }
        }

        if (!resourceLocation.endsWith("/")) {
            resourceLocation += "/";
        }

        registry.addResourceHandler("/upload/products/**")
                .addResourceLocations(resourceLocation);
    }
}
