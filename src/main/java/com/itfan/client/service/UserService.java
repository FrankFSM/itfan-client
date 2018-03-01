package com.itfan.client.service;

import com.alibaba.fastjson.JSON;
import com.itfan.client.controller.UserController;
import com.itfan.client.domain.Episode;
import com.itfan.client.domain.ItfanUser;
import com.itfan.client.exception.UserException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author: ralap
 * @date: created at 2018/1/28 20:37
 */
@Slf4j
@Service("userService")
public class UserService {


    @Autowired
    private RestTemplate restTemplate;

    public ItfanUser getUserByUserName(String userName) {
        String userStr = restTemplate
                .getForEntity(
                        UserController.KERNAL_URL + "getUserByUserName?userName=" + userName + "&"
                                + UserController.TOKEN, String.class).getBody();
        ItfanUser user = JSON.parseObject(userStr, ItfanUser.class);
        return user;
    }

    public void regist(ItfanUser user) throws UserException {
        log.info(user.toString());
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("userName", user.getUserName());
        requestEntity.add("password", user.getPassword());
        requestEntity.add("roles", user.getRoles());
        Integer count = restTemplate
                .postForObject(UserController.KERNAL_URL + "insertUser?" + UserController.TOKEN,
                        requestEntity, Integer.class);
    }

    public List<ItfanUser> getAll() {
        String path = UserController.KERNAL_URL + "getAllUser?accessToken={accessToken}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("accessToken", 123);
        ItfanUser[] itfanUsers = restTemplate
                .getForEntity(path, ItfanUser[].class, uriVariables).getBody();
        List<ItfanUser> list = new ArrayList<>();
        for (ItfanUser user : itfanUsers) {
            list.add(user);
        }

        return list;
    }
}
