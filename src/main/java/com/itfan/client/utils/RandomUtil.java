package com.itfan.client.utils;

import com.itfan.client.domain.Video;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: ralap
 * @date: created at 2018/2/9 15:42
 */
public class RandomUtil {

    public static List<Video> randomVideo(int size, List<Video> videoList) {
        Random r = new Random();
        List<Video> list = new ArrayList<>();
        int i;
        while (list.size() < size) {
            i = r.nextInt(videoList.size());
            if (!list.contains(i)) {
                list.add(videoList.get(i));
            }
        }
        return list;
    }

}
