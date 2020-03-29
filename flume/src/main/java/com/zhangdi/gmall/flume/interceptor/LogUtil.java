package com.zhangdi.gmall.flume.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class LogUtil {

    public static boolean validateStart(String log) {
        //判断文件是否为空
        if (StringUtils.isBlank(log)) {
            return false;
        }
        //如果不以大括号开头，或大括号结尾返回false
        if (!log.startsWith("{") || !log.endsWith("}")) {
            return false;
        }
        return true;
    }

    public static boolean validateEvent(String log) {
        //判断是否为空
        if (StringUtils.isBlank(log)) {
            return false;
        }

        //对log文件进行以 | 切割，因为|在正则表达式里是特殊
        // 字符，所以要加个\，但是\在java里也是特殊字符，
        // 所以在加个\
        String[] split = log.split("\\|");
        //判断文件是否被切割成两份
        if (split.length != 2) {
            return false;
        }
        //把切割的两份，第一份时间为0，第二份json为1
        String timeStamp = split[0];
        String json = split[1];
        //判断split[0]长度是不是13位
        if (timeStamp.length() != 13) {
            return false;
        }
        //判断timeStamp里面是不是都是数字
        if (!NumberUtils.isDigits(timeStamp)) {
            return false;
        }
        if (!json.startsWith("{") || !json.endsWith("}")) {
            return false;
        }
        return true;
    }

}
