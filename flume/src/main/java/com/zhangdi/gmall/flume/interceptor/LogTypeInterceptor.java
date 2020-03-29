package com.zhangdi.gmall.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.List;

public class LogTypeInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取event对象
        byte[] body = event.getBody();
        //把字节数组转成string
        String log = new String(body, Charset.forName("utf8"));
        //如果开头是start然后分组
        if (log.contains("start")) {
            event.getHeaders().put("topic", "topic_start");
        } else {
            event.getHeaders().put("topic", "topic_event");
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new LogTypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

}
