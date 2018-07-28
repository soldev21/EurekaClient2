package client.model;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String name;
    private List<Instance> instances = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
}
