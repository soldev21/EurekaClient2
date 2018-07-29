package client.utility;

import client.model.MicroInstance;
import client.model.MicroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MicroServiceRegistry {

    @Autowired
    DiscoveryClient discoveryClient;


    public Map<String,MicroService> getMicroServices(){
        List<String> services = discoveryClient.getServices();
        Map<String,MicroService> microMicroServices = new HashMap<>();
        for (String service : services){
            microMicroServices.put(service,buildService(service));
        }
        return microMicroServices;
    }

    public MicroInstance getRandomInstance(String serviceName){
        MicroService microService = getMicroServices().get(serviceName);
        int n = microService.getMicroInstances().size();
        return microService.getMicroInstances().get(RandomUtils.nextInt(n));
    }

    public String getRandomInstanceURL(String serviceName){
        MicroInstance instance = getRandomInstance(serviceName);
        return "http://"
                .concat(instance.getHost())
                .concat(":")
                .concat(String.valueOf(instance.getPort()))
                .concat("/");
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

}
