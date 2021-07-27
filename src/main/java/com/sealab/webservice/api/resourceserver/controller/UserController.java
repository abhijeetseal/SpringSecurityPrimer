package com.sealab.webservice.api.resourceserver.controller;


import com.sealab.webservice.api.resourceserver.entity.Activationtoken;
import com.sealab.webservice.api.resourceserver.entity.User;
import com.sealab.webservice.api.resourceserver.repository.ActivationTokenRepository;
import com.sealab.webservice.api.resourceserver.service.ActivationEmailService;
import com.sealab.webservice.api.resourceserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivationEmailService activationEmailService;



    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        log.info("inside saveUser :: controller");
        User userObj = userService.saveUser(user);

        if(userObj != null){

            Activationtoken activationtoken = new Activationtoken(user);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Your Registration!");
            mailMessage.setFrom("a******@outlook.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:9002/users/confirmaccount?token="+activationtoken.getConfirmationToken());

            activationEmailService.sendEmail(mailMessage, activationtoken);
        }

        return userObj;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        log.info("inside getUser :: controller");
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public List<User> getUsers(){
        log.info("inside getUsers :: controller");
        return userService.getUsers();
    }


    @GetMapping(value="/confirmaccount")
    public User confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        Activationtoken token = activationEmailService.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setActive(true);
            return userService.saveUser(user);
        }
        else
        {
            log.info("Link is expired");
            return null;
        }


    }

}
