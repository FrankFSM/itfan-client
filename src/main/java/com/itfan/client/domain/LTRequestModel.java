package com.itfan.client.domain;

import com.itfan.client.globle.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ralap on 2017/11/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LTRequestModel {

    private String key;
    private String info;
    private String loc;
    private String userid;


    public static LTRequestModel createModel(String info) {
        LTRequestModel model = new LTRequestModel();
        model.setKey(Constant.TL_APIKEY);
        model.setInfo(info);
        model.setUserid(Constant.TL_USER_ID);
        return model;
    }

}
