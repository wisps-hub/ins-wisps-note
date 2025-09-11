package com.wisps.helper;

import com.wisps.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceHelper {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public User getUser(Long id) {
        //无负载
        ServiceInstance service = discoveryClient.getInstances("ins-wisps-user").get(0);
        String url = "http://" + service.getHost() + ":" + service.getPort() + "/user/" + id;
        //基于loadBalancerClient负载
        ServiceInstance balanceService = loadBalancerClient.choose("ins-wisps-user");
        String balanceUrl = "http://" + balanceService.getHost() + ":" + balanceService.getPort() + "/user/" + id;
        //基于@LoadBalanced负载
        String atBalanceUrl = "http://ins-wisps-user/user/" + id;
        System.out.println("远程调用url：" + atBalanceUrl);
        ResponseEntity<User> resp = restTemplate.getForEntity(atBalanceUrl, User.class);
        return resp.getBody();
    }

}
