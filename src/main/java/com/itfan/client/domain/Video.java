package com.itfan.client.domain;

import lombok.Data;

/**
 * Created by ralap on 2017/8/14.
 */
@Data
public class Video {

    /**
     * 标题
     */
    private String title;
    /**
     * 原生视频地址
     */
    private String rawUrl;
    /**
     * 视频封面
     */
    private String imageUrl;

    /**
     * 视屏真实地址
     */
    private String realUrl;

    /**
     * 视频类型
     */
    private String type;

    /* [版权] 视频源地址 */
    private String value;

    /* [版权] 视频提供方 */
    private String provider;

    /* [版权] 视频解析方名称 */
    private String parserName;

    /* [版权] 视频解析方官网 */
    private String parser;

}
