package dev.biletskyi.productservice.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//This class allows us to see Swagger page by default
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "redirect:/swagger-ui/index.html";
    }

}
