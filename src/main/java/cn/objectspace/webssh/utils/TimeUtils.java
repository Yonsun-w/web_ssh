package cn.objectspace.webssh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @(#)TimeUtils.java, 5月 11, 2023.
 * <p>
 * Copyright 2023 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * @wenjiahua
 */
public class TimeUtils {
    // 类主体部分
    // 格式
    public static List<String> getPretimeStr(String timeStr, int pre) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date time = sdf.parse(timeStr); // 将时间字符串转换为 Date 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time); // 设置时间为指定时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= pre; i++) {
            calendar.add(Calendar.HOUR_OF_DAY, 1); // 小时加 1
            list.add(sdf2.format(calendar.getTime()));
        }
        return list;
    }
}