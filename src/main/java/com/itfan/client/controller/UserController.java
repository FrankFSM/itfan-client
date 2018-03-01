package com.itfan.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itfan.client.domain.Episode;
import com.itfan.client.domain.ItfanUser;
import com.itfan.client.domain.Video;
import com.itfan.client.exception.UserException;
import com.itfan.client.service.UserService;
import com.itfan.client.utils.RandomUtil;
import com.sun.deploy.util.VersionID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * UserController
 *
 * @author: ralap
 * @date: created at 2018/1/28 12:18
 */
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String BASE_URL = "http://localhost:8888/";
    public static final String KERNAL_URL = BASE_URL + "itfan-kernel/client/";
    public static final String TOKEN = "accessToken=123";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    /**
     * 首页
     */
    @RequestMapping("/")
    public String index(Map<String, Object> map) {

        String carouselListStr = restTemplate
                .getForEntity(KERNAL_URL + "carouselList?" + TOKEN, String.class).getBody();
        List carouselList = this.jsonToList(carouselListStr);

        String getForEntityStr = restTemplate
                .getForEntity(KERNAL_URL + "moviesList?" + TOKEN, String.class).getBody();
        List<Video> moviesList = this.jsonToList(getForEntityStr);

        String hotTVListStr = restTemplate
                .getForEntity(KERNAL_URL + "hotTVList?" + TOKEN, String.class).getBody();
        List<Video> hotTVList = this.jsonToList(hotTVListStr);
        String zyListStr = restTemplate
                .getForEntity(KERNAL_URL + "zyList?" + TOKEN, String.class).getBody();
        List<Video> zyList = this.jsonToList(zyListStr);
        map.put("carouselList", carouselList);
        map.put("moviesList", moviesList);
        map.put("hotTVList", hotTVList);
        map.put("zyList", zyList);
        return "index";
    }

    public List jsonToList(String jsonStr) {
        List<Video> list = new ArrayList();
        JSONArray platformList = JSON.parseArray(jsonStr);
        for (Object jsonObject : platformList) {
            Video platformModel = JSON.parseObject(jsonObject.toString(), Video.class);
            list.add(platformModel);
        }
        return list;
    }

    @GetMapping("/movieRefeash")
    @ResponseBody
    public List movieRefeash() {
        String getForEntityStr = restTemplate
                .getForEntity(KERNAL_URL + "moviesList?" + TOKEN, String.class).getBody();
        List<Video> moviesList = this.jsonToList(getForEntityStr);
        List<Video> videos = RandomUtil.randomVideo(10, moviesList);
        return videos;
    }

    @GetMapping("/hotRefeash")
    @ResponseBody
    public List hotRefeash() {
        String getForEntityStr = restTemplate
                .getForEntity(KERNAL_URL + "hotTVList?" + TOKEN, String.class).getBody();
        List<Video> moviesList = this.jsonToList(getForEntityStr);
        List<Video> videos = RandomUtil.randomVideo(10, moviesList);
        return videos;
    }

    @GetMapping("/zyRefeash")
    @ResponseBody
    public List zyRefeash() {
        String getForEntityStr = restTemplate
                .getForEntity(KERNAL_URL + "zyList?" + TOKEN, String.class).getBody();
        List<Video> moviesList = this.jsonToList(getForEntityStr);
        List<Video> videos = RandomUtil.randomVideo(10, moviesList);
        return videos;
    }

    /**
     * 解析页
     */
    @GetMapping("/analysis")
    public String analysis() {
        return "analysis";
    }

    /**
     * 播放视频
     */
    @GetMapping("/play")
    public String paly(HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> map) {
        String url = request.getParameter("u");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = KERNAL_URL + "play?url={url}&accessToken={accessToken}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("url", url);
        uriVariables.put("accessToken", 123);
        Video video = restTemplate.getForEntity(path, Video.class, uriVariables)
                .getBody();
        map.put("video", video);
        return "video";
    }

    @GetMapping("/episode")
    @ResponseBody
    public List<Episode> episodes(HttpServletRequest request) {
        String url = request.getParameter("v");
        String path = KERNAL_URL + "episode?url={url}&accessToken={accessToken}";

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("url", url);
        uriVariables.put("accessToken", 123);

        List<Episode> list = restTemplate
                .getForEntity(path, List.class, uriVariables).getBody();
        return list;
    }

    @GetMapping("/video")
    @ResponseBody
    public Video video(HttpServletRequest request) {
        String url = request.getParameter("v");

        Video video = restTemplate
                .getForEntity(KERNAL_URL + "video?url=" + url + "&" + TOKEN, Video.class)
                .getBody();
        return video;
    }

    @GetMapping("/searchView")
    public String search() {
        return "search";
    }


    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }

    @PostMapping(value = "/regist")
    public String regist(ItfanUser user) {
        try {
            user.setRoles("ROLE_USER");
            userService.regist(user);
        } catch (UserException e) {
            e.printStackTrace();
        }
        return "login";
    }


}
