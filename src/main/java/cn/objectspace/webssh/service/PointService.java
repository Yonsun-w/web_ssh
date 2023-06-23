package cn.objectspace.webssh.service;

import cn.objectspace.webssh.utils.HttpUtils;
import cn.objectspace.webssh.utils.NDArrayIOUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yonsun
 */
@Service
public class PointService {
    //经纬度
    String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=WkQm52ps4kw23O9DuEI0PqRdhKOja8zM&output=json&coordtype=wgs84ll&location=%s,%s";
    // 类主体部分
    final int left = 113, bottom = 37, right = 120, top = 42;
    @Value("${NPY.trueDirPath}")
    String trueDirPath;

    public Map<String, Integer> getCityPointsNumber(String time) throws Exception {
        List<List<String>> list = getPoints(time);
        Map<String, Integer> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        for (List<String> l : list) {
            String city = getCity(l.get(0), l.get(1));
            if (StringUtils.isEmpty(city)) {
                continue;
            }
            map.put(city, map.getOrDefault(city, 0) + 1);
        }
        return map;
    }

    //获取所有点位信息
    public List<List<String>> getPoints(String time) throws Exception {
        File file = new File(getFileName(time));
        System.out.println(file.isFile());
        if (!file.isFile()) {
            return new ArrayList<>();
        }
        try {
            List<List<String>> list = new ArrayList<>();
            float[] res = NDArrayIOUtils.readNpyFile(file.getAbsolutePath());
            if (res == null) {
                return new ArrayList<>();
            }
            for (int i = 0; i < res.length; i+= 1) {
                if (res[i]!= 0) {
                    List t = getLatLon(i);
                    t.add(res[i]);
                    list.add(t);
                }
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    String getFileName(String time) throws Exception{
        if (StringUtils.isEmpty(time) || time.length() <=2 ) {
            throw new Exception("no such file");
        }
        //return time + ".npy";
        return trueDirPath + time + "_truth.npy";
    }

    public List<String> getLatLon(int index) {
        int row = index / 159 + 1;
        int col = index % 159 + 1;
        List<String> list = new ArrayList<>();
        list.add(Math.max(top - row * 0.04, bottom) + "");
        list.add(Math.min(col * 0.04 + left, right) + "");
        return list;
    }

    public String getCity(String lat, String lon) {
        String getUrl = String.format(url,lat,lon);
        try {
            String res = HttpUtils.sendGetRequest(getUrl, null, String.class);
            String regex = "\"city\"\\s*:\\s*\"([\\u4e00-\\u9fa5]+)\"\\s*,?";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(res);
            System.out.println(res);
            if (matcher.find()) {
                System.out.println("find");
                return matcher.group(1);
            }
        } catch (Exception e) {
            System.out.println("error  eeee");
            return e.toString();
        }
        return "";
    }

    public static void main(String[] args) {
//        String URL = "https://ep-ehr-test.zhenguanyu.com/staffInfo/listStaffByLdap";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        List<String> ldaps = new ArrayList<>();
//        ldaps.add("wenjiahua");
//        HttpEntity<List<String>> entity = new HttpEntity<>(ldaps, headers);
//        String res = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
//        System.out.println(res);


        String URL = "https://ep-ehr-test.zhenguanyu.com/staffInfo/listStaffByLdap";
        // URL = "http://www.baidu.com";
        final RestTemplate restTemplate = new RestTemplate();
        List<String> ldaps = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<List<String>> entity = new HttpEntity<>(ldaps, headers);
        ldaps.add("wenjiahua");
        ResponseEntity<String> responseEntity
                = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        String res = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();

        System.out.println(res);
    }
}