package com.wisps;


import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.List;

@SpringBootTest
public class DiscorveryClientTest {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    public void loadBalancerClientTest() throws NacosException {
        for (int i = 0; i < 20; i++) {
            ServiceInstance choose = loadBalancerClient.choose("ins-wisps-user");
            System.out.println("instance" + choose.getInstanceId() + ", uri: " + choose.getHost() + ":" + choose.getPort());
        }
    }

    @Test
    public void nacosDiscoveryClientTest() throws NacosException {
        for (String service : nacosServiceDiscovery.getServices()) {
            System.out.println("service: " + service);
            for (ServiceInstance instance : nacosServiceDiscovery.getInstances(service)) {
                System.out.println("instanceId: " + instance.getInstanceId());
                System.out.println("host: " + instance.getHost());
                System.out.println("port: " + instance.getPort());
                System.out.println("scheme: " + instance.getScheme());
                System.out.println("metadata: " + instance.getMetadata());
                System.out.println("serviceId: " + instance.getServiceId());
                System.out.println("uri: " + instance.getUri());
            }
        }
    }

    @Test
    public void discoveryClientTest(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("instanceId: " + instance.getInstanceId());
                System.out.println("host: " + instance.getHost());
                System.out.println("port: " + instance.getPort());
                System.out.println("scheme: " + instance.getScheme());
                System.out.println("metadata: " + instance.getMetadata());
                System.out.println("serviceId: " + instance.getServiceId());
                System.out.println("uri: " + instance.getUri());
            }
        }
    }
}
