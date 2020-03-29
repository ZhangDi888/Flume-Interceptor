package interceptor2;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class LogUtil1 {

    public static boolean validateEvent(String utf8) {
        if(StringUtils.isBlank(utf8)){
            return false;
        }
        String[] split = utf8.split("\\|");

        if(split.length != 2){
            return false;
        }

        String time = split[0];
        String value = split[1];

        if(time.length() != 13){
            return false;
        }

        if(!NumberUtils.isDigits(time)){
            return false;
        }

        if(!value.startsWith("{")|| !value.endsWith("}")){
            return false;
        }
        return true;
    }

    public static boolean validateStart(String utf8) {
        if(StringUtils.isBlank(utf8)){
            return false;
        }
        if(!utf8.startsWith("{")|| !utf8.endsWith("}")){
            return false;
        }
        return true;
    }
}
