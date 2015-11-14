package test.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeUtilImpl implements TimeUtil{

    public Calendar getCalendar(){
        return Calendar.getInstance();
    }
}
