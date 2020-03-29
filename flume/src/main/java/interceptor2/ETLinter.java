package interceptor2;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class ETLinter implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String utf8 = new String(body, Charset.forName("utf8"));

        if(utf8.contains("start")){
            if(LogUtil1.validateStart(utf8)){
                return event;
            }
        } else {
            if(LogUtil1.validateEvent(utf8)){
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        Iterator<Event> iterator = list.iterator();
        while (iterator.hasNext()){
            Event next = iterator.next();
            if(intercept(next) == null){
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
            return new ETLinter();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
