package interceptor2;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class Type implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {

        byte[] body = event.getBody();

        String utf8 = new String(body, Charset.forName("utf8"));

        if(utf8.contains("start")){
            event.getHeaders().put("topic","topic-start");
        } else {
            event.getHeaders().put("topic", "topic-event");
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(list);
        }
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{
        @Override
        public Interceptor build() {
            return new Type();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
