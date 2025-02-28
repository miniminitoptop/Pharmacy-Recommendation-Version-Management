package com.example.project.direction.controller;

import com.example.project.direction.dto.UserDto;
import com.example.project.direction.entity.UserEntity;
import com.example.project.direction.repository.UserRepository;
import com.example.project.direction.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/join")
public class JoinController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;
    @Autowired
    private LoginService loginService;

    @RequestMapping({"/", ""})
    public String Join(HttpServletRequest request, Model model){
        Map<String, ?> flashMap  = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            model.addAttribute("id_error", flashMap.get("id_error"));
            model.addAttribute("password_error", flashMap.get("password_error"));
        }

        return "/join";
    }

    @PostMapping("/action")
    @ResponseBody
    public ResponseEntity<Map<String, String>> joinAction(@Valid UserDto userDto, BindingResult bindingResult, Model model){
        // jquery 의 serialize 를 이용해 파리미터 형식 (test1=123&test2=456&test3=789) -> ModelAttribute 사용
        Map<String, String> error_text = new HashMap<>();
        if(bindingResult.hasErrors()){
            if(!bindingResult.getFieldErrors("userId").isEmpty())
                error_text.put("id_error", bindingResult.getFieldErrors("userId").get(0).getDefaultMessage());
            if(!bindingResult.getFieldErrors("password").isEmpty()) {
                error_text.put("pw_error", bindingResult.getFieldErrors("password").get(0).getDefaultMessage());
            }
            return new ResponseEntity<>(error_text, HttpStatus.BAD_REQUEST);
        }

        if(loginService.create(userDto, error_text) == true) {
            return new ResponseEntity<>(error_text, HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());

        session.setAttribute("user", userEntity);

        session.setMaxInactiveInterval(7200);

        return new ResponseEntity<>(error_text, HttpStatus.OK);
    }

}
