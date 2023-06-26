package cn.objectspace.webssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class RouterController {
    @RequestMapping("/websshpage")
    public String websshpage(){
        return "webssh";
    }

    @RequestMapping("/login")
    public String client(){
        return "login";
    }
}
