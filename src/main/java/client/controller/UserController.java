package client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    RestTemplate template;

    private String ACCOUNT_SERVICE_URL = "http://account-service/";

    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping
    public void doPost(@RequestBody String msg){
        System.out.println(discoveryClient.getServices());
        System.out.println(msg);
        dispatch(msg,ACCOUNT_SERVICE_URL);
    }

    private void dispatch(String msg,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(msg,headers);
        template.exchange(url,HttpMethod.POST,entity,String.class);
    }
}
