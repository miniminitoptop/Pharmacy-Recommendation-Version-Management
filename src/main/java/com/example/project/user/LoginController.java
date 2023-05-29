package com.example.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginService loginService;

    @RequestMapping({"/", ""})
    public String Login(){ return "login"; };


    @PostMapping("/action")
    @ResponseBody
    public ResponseEntity<Map<String, String>> LoginAction(UserDto userDto, Model model) {

        Map<String, String> error_text = new HashMap<>();

        if (loginService.isUser(userDto.getUserId(), userDto.getPassword(), error_text) == false)
            return new ResponseEntity<>(error_text, HttpStatus.BAD_REQUEST);

        UserEntity user = loginService.getUser(userDto, error_text);

        session.setAttribute("user", user);

        session.setMaxInactiveInterval(7200);

        Map<String, String> userDtoMap = new HashMap<>();
        userDtoMap.put("userId", userDto.getUserId());
        userDtoMap.put("password", userDto.getPassword());

        return new ResponseEntity<>(userDtoMap, HttpStatus.OK);
    }

    @RequestMapping("/logout")
    public String LoginAction(){
        session.invalidate();

        return "redirect:/login";
    }


}
