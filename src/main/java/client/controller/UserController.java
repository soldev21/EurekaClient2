package client.controller;

import client.model.MicroInstance;
import client.model.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    RestTemplate template;

    private String ACCOUNT_SERVICE_URL = "http://account-service/";

    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping
    public List<MicroService> doPost(@RequestBody String msg){
        List<String> services = discoveryClient.getServices();
        List<MicroService> microMicroServices = new ArrayList<>();
        for (String service : services){
            microMicroServices.add(buildService(service));
        }
        return microMicroServices;
    }

    private MicroService buildService(String name){
        MicroService microService = new MicroService();
        microService.setName(name);
        for (ServiceInstance instance : discoveryClient.getInstances(name)){
            microService.getMicroInstances().add(buildInstance(instance));
        }
        return microService;
    }

    private MicroInstance buildInstance(ServiceInstance instance){
        MicroInstance microMicroInstance = new MicroInstance();
        microMicroInstance.setPort(instance.getPort());
        microMicroInstance.setHost(instance.getHost());
        return microMicroInstance;
    }

    private void dispatch(String msg,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(msg,headers);
        template.exchange(url,HttpMethod.POST,entity,String.class);
    }
}
