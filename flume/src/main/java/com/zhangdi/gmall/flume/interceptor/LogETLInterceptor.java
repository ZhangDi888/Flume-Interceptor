package com.zhangdi.gmall.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class LogETLInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取资料
        byte[] body = event.getBody();
        //把字节数组转成string，并且转成utf8格式
        String log = new String(body, Charset.forName("utf8"));
        //如果log文件包含start，就用工具类判断是否为true，如果为true就返回event
        if (log.contains("start")) {
            if (LogUtil.validateStart(log)) {
                return event;
            }
        } else {
            if (LogUtil.validateEvent(log)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        //list是个集合，如果里面有不合法的东西，就应该踢出去
        //for循环没有踢出去的功能，需要用到迭代器
        Iterator<Event> iterator = list.iterator();
        //遍历iterator
        while (iterator.hasNext()) {
            //获取相应的event
            Event next = iterator.next();
            //如果获取的文件是空，直接移除
            if (intercept(next) == null) {
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public void close() {

    }
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new LogETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
