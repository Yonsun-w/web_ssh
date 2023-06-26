package cn.objectspace.webssh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @(#)ConfigController.java, 5月 11, 2023.
 * <p>
 * Copyright 2023 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * @wenjiahua
 */
@RestController
@CrossOrigin
public class ConfigController {
    // 类主体部分

    @PostMapping("/getServerData")
    public String getServerData() {
        JSONObject res = new JSONObject();
        JSONObject m = new JSONObject();
        m.put("已用内存", 1.70);
        m.put("剩余内存", 2.30);
        m.put("总内存", 4.00);
        JSONObject GPU = new JSONObject();
        GPU.put("温度", 43);
        GPU.put("资源使用", 6.30);
        JSONObject cpu = new JSONObject();
        cpu.put("温度", 13);
        cpu.put("资源使用", 0.30);
        res.put("Memory",m);
        res.put("GPU",GPU);
        res.put("CPU",cpu);
        return res.toJSONString();
    }

    @GetMapping("/getConfigList")
    public String getConfigList() {
        return "[{\n" +
                "\t\"name\": \"LMoe多专家集成预报模型\"\n" +
                "}, {\n" +
                "\t\"name\": \"LightNet雷电预报模型\"\n" +
                "}, {\n" +
                "\t\"name\": \"ADSNet雷电预报模型\"\n" +
                "}, {\n" +
                "\t\"name\": \"PredRNN时空序列预报模型\"\n" +
                "}]";
    }
}