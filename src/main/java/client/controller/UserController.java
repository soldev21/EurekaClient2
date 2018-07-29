package client.controller;

import client.model.MicroInstance;
import client.model.MicroService;
import client.utility.MicroServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    RestTemplate template;

    @Autowired
    MicroServiceRegistry registry;

    private String SERVICE_NAME = "account-service";



    @PostMapping
    public Map<String,MicroService> doPost(@RequestBody String msg){
        return registry.getMicroServices();
    }

    @GetMapping
    public String doGet(){
        String URL = registry.getRandomInstanceURL(SERVICE_NAME);
        System.out.println(URL);
        dispatch("Hello",URL);
        return URL;
    }



    private void dispatch(String msg,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(msg,headers);
        template.exchange(url,HttpMethod.POST,entity,String.class);
    }
}
