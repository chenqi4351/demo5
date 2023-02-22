package com.example.demo;

import demo.postgres.dao.login.AccountExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@SpringBootApplication
public class Demo5Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo5Application.class, args);


        //mybatisdemo ac=new mybatisdemo();
        //System.out.println(ac.getObj());
        //Activemq ms=new Activemq();
        //ms.sender();
        //ms.JmsReceiver();

    }
    public static void test(){
        Logger logger = LoggerFactory.getLogger(Demo5Application.class);
        while(true){
            String url="https://www.chrddf.top//checklogin.php";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            UUID uuid = UUID.randomUUID();
            params.add("email", "domain"+uuid);
            uuid = UUID.randomUUID();
            params.add("password", "account"+uuid);
            uuid = UUID.randomUUID();
            params.add("code", "key"+uuid);
            String  rs=sendPostRequest(url , params);
            logger.info(rs);
            System.out.println(rs);
        }
    }
    public static String sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //System.out.println(requestEntity);
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }


}
