package client.model;

import java.util.ArrayList;
import java.util.List;

public class MicroService {
    private String name;
    private List<MicroInstance> microInstances = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MicroInstance> getMicroInstances() {
        return microInstances;
    }

    public void setMicroInstances(List<MicroInstance> microInstances) {
        this.microInstances = microInstances;
    }
}
