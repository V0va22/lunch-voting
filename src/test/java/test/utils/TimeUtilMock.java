package test.utils;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Primary
@Component
public class TimeUtilMock implements TimeUtil {

    private boolean future;

    public Calendar getCalendar(){
        return future ? new GregorianCalendar(3000,0,0,0,0) : new GregorianCalendar(0,0,0,0,0);
    }

    public void setFuture() {
        this.future = true;
    }
    public void setPast() {
        this.future = false;
    }
}
