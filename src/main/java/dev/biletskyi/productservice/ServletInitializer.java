package dev.biletskyi.productservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//Потрібен, коли збираємо WAR і деплоїмо в зовнішній контейнер
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MarketplaceDemoApplication.class);
    }

}
