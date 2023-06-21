package org.example.Serivce;

import org.example.utils.NDArrayIOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yonsun
 */
@Service
public class PointService {
    // 类主体部分
    final int left = 113, top = 36, right = 120, bottom = 42;
    public List<List<String>> getPoints(String time) {
        File file = new File(getFileName(time));
        System.out.println(file.isFile());
        if (!file.isFile()) {
            return new ArrayList<>();
        }
        try {
            List<List<String>> list = new ArrayList<>();
            float[] res =NDArrayIOUtils.readNpyFile(file.getAbsolutePath());
            if (res == null) {
                return new ArrayList<>();
            }
            for (int i = 0; i < res.length; i+= 1) {
                if (res[i]!= 0) {
                    list.add(getLatLon(i));
                }
            }
            System.out.println(list);
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    String getFileName(String time) {
        //return time + ".npy";
        return "/Users/yonsun/202007111650_h0.npy";
    }
    // lat lon
    public List<String> getLatLon(int index) {
        int row = index / 159 + 1;
        int col = index % 159 + 1;
        List<String> list = new ArrayList<>();
        list.add(Math.min(row * 0.04 + top, bottom) + "");
        list.add(Math.min(col * 0.04 + left, right) + "");
        return list;
    }

    public String getCity(int lat, int lon) {
        if (lat < top || lat > bottom || lon < left || lon > right) {
            return null;
        }
        //
        //
        return "北京";
    }

    public static void main(String[] args) {
        PointService pointService = new PointService();
        System.out.println(pointService.getPoints(""));
    }
}