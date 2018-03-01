package com.itfan.client.service;

import com.alibaba.fastjson.JSONObject;
import com.itfan.client.domain.LTRequestModel;
import com.itfan.client.globle.Constant;
import java.nio.charset.Charset;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ralap on 2017/11/12.
 */
@Service
public class TLService {


    public String getInfo(String content) {
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m)
                .build();
        LTRequestModel model = LTRequestModel.createModel(content);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String jsonObj = JSONObject.toJSONString(model);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);

        String result = restTemplate.postForObject(Constant.URL_TL_API, formEntity, String.class);

        return result;
    }
}
