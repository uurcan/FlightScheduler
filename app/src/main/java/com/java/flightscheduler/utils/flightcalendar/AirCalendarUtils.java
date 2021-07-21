package com.java.flightscheduler.utils.flightcalendar;

import android.content.Context;

import com.java.flightscheduler.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class AirCalendarUtils {

    private static List<String> airCalendarWeekdays = new ArrayList<>();
    private static AirCalendarIntent.Language airCalendarLanguage = AirCalendarIntent.Language.EN;

    static boolean isWeekend(String strDate) {
        int weekNumber = getWeekNumberInt(strDate);
        return weekNumber == 6 || weekNumber == 7;
    }

    private static int getWeekNumberInt(String strDate) {
        int week = 0;
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        try {
            calendar.setTime(Objects.requireNonNull(df.parse(strDate)));
        } catch (Exception e) {
            return 0;
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = 7;
                break;
            case 1:
                week = 1;
                break;
            case 2:
                week = 2;
                break;
            case 3:
                week = 3;
                break;
            case 4:
                week = 4;
                break;
            case 5:
                week = 5;
                break;
            case 6:
                week = 6;
                break;
        }
        return week;
    }

    static String getDateDay(Context context, String date, int weekStart) throws Exception {
        String day = "" ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH) ;
        Date nDate = dateFormat.parse(date) ;
        Calendar cal = Calendar.getInstance() ;
        cal.setFirstDayOfWeek(weekStart);
        cal.setTime(Objects.requireNonNull(nDate));
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        if (dayNum == 1) {
            dayNum = 6;
        } else {
            dayNum-=2;
        }

        if (airCalendarWeekdays.isEmpty()) {
            if (airCalendarLanguage == AirCalendarIntent.Language.EN) {
                String[] weekdaysEn = context.getResources().getStringArray(R.array.label_calendar_en);
                day = weekdaysEn[dayNum];
            }
            return day ;
        } else {
            return airCalendarWeekdays.get(dayNum);
        }
    }

    static void setWeekdays(List<String> weekdays) {
        airCalendarWeekdays.clear();
        airCalendarWeekdays.addAll(weekdays);
    }

    static void setLanguage(AirCalendarIntent.Language language) {
        airCalendarLanguage = language;
    }
}
