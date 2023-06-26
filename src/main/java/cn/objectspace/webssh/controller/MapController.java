package cn.objectspace.webssh.controller;

import cn.objectspace.webssh.service.PointService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @(#)MapController.java, 5月 11, 2023.
 * <p>
 * Copyright 2023 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * @wenjiahua
 */
@RestController
@CrossOrigin
public class MapController {
    // 类主体部分
    @Autowired
    PointService pointService;

    @PostMapping("/isWhere")
    public String isWhere(@RequestBody JSONObject jsonObject) throws Exception{
        if (!jsonObject.containsKey("period")) {
            return "缺少period参数";
        }
        String time = jsonObject.get("period").toString();
        JSONObject res = new JSONObject();
        try {
            res.put("dict", pointService.getCityPointsNumber(time));
            return res.toJSONString();
        } catch (Exception e) {
            System.out.println(e);
            return "查看后端";
        }
    }
    @PostMapping("/readRealData")
    public String readRealData(@RequestBody JSONObject jsonObject) throws Exception{
        if (!jsonObject.containsKey("period")) {
            return "缺少period参数";
        }
        String time = jsonObject.get("period").toString();
        JSONObject res = new JSONObject();
        try {
            List list = pointService.getPoints(time);
            res.put("count", list.size());
            res.put("point",list);
            return res.toJSONString();
        } catch (Exception e) {
            System.out.println(e);
            return "查看后端";
        }
    }

    @PostMapping("/isWhereFor12h")
    public String isWhereFor12h(@RequestBody JSONObject jsonObject) throws Exception{
        if (!jsonObject.containsKey("period")) {
            return "缺少period参数";
        }
        if (!jsonObject.containsKey("selectModel")) {
            System.out.println("缺少模型 默认");
        }
        String time = jsonObject.get("period").toString();

        JSONObject res = new JSONObject();
        try {
            List list = pointService.getPoints(time);
            res.put("count", list.size());
            res.put("point",list);
            return res.toJSONString();
        } catch (Exception e) {
            System.out.println(e);
            return "查看后端";
        }
    }

    public String test(@PathVariable("test1") String test1,
                       @PathVariable("test") String test2) {
        return test1 + test2;
    }


}

