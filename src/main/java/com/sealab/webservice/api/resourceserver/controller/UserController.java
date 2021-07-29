package com.sealab.webservice.api.resourceserver.controller;


import com.sealab.webservice.api.resourceserver.entity.Activationtoken;
import com.sealab.webservice.api.resourceserver.entity.User;
import com.sealab.webservice.api.resourceserver.repository.ActivationTokenRepository;
import com.sealab.webservice.api.resourceserver.service.ActivationEmailService;
import com.sealab.webservice.api.resourceserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivationEmailService activationEmailService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        log.info("inside saveUser :: controller");
        User userObj = userService.saveUser(user);
        String host = "http://localhost:9003";
        if(userObj != null){
            Activationtoken activationtoken = new Activationtoken(user);
            try {

                String ip = InetAddress.getLocalHost().getHostAddress();
                String hostName = InetAddress.getLocalHost().getHostName();
                log.info("ip: "+ ip);
                log.info("hostName: "+ hostName);
                int port = applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
                log.info("port: "+ port);
                String host1 = "http://".concat(ip.concat(":")) +port;
                log.info("Host: "+ host1);
            }catch (UnknownHostException e){
                log.error("Unknown Host");
            }
            log.info("Constructing email : " + "To confirm your account, please click here : "
                    +host+"/confirmaccount?token="+activationtoken.getConfirmationToken());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Your Registration!");
            mailMessage.setFrom("a******@outlook.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +host+"/confirmaccount?token="+activationtoken.getConfirmationToken());

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
