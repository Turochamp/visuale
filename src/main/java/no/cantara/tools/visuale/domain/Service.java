
package no.cantara.tools.visuale.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "tag",
        "service_type",
        "healthy_nodes",
        "need_codebase_chores",
        "nodes"
})
public class Service {

    @JsonProperty("name")
    private String name;
    @JsonProperty("tag")
    private String tag = "";
    @JsonProperty("service_type")
    private String serviceType = "";
    @JsonProperty("nodes")
    private Set<Node> nodes = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Service withName(String name) {
        this.name = name;
        return this;
    }


    @JsonProperty("need_codebase_chores")
    public boolean needCodeChores() {
        int secure_nodes = 0;
        for (Node n : nodes) {
            if (n.isSecure()) {
                secure_nodes++;
            }

        }
        return secure_nodes == 0;
    }


    @JsonProperty("healthy_nodes")
    public int getHealthyNodes() {
        int healthy_nodes = 0;
        for (Node n : nodes) {
            if (n.isHealthy()) {
                healthy_nodes++;
            }

        }
        return healthy_nodes;
    }


    @JsonProperty("nodes")
    public Set<Node> getNodes() {
        return nodes;
    }

    @JsonProperty("nodes")
    public void setNodes(Set<Node> nodesV) {
        this.nodes = new TreeSet<Node>(new MyNodeNameComp());
        nodes.addAll(nodesV);
    }

    public Service withNodes(Set<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    public Service withNode(Node node) {
        if (this.nodes == null) {
            this.nodes = new TreeSet<Node>(new MyNodeNameComp());
        }
        this.nodes.add(node);
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Service withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                "tag='" + tag + '\'' +
                "serviceType='" + serviceType + '\'' +
                ", nodes=" + getNodes() +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public class MyNodeNameComp implements Comparator<Node> {

        @Override
        public int compare(Node e1, Node e2) {
            if (e1.getName() != null && e2.getName() != null) {
                return e1.getName().compareTo(e2.getName());
            }
            if (e1.getIp() != null && e2.getIp() != null) {
                return e1.getIp().compareTo(e2.getIp());
            }
            return 1;
        }
    }

}