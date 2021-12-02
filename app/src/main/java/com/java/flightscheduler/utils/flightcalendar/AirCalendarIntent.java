package com.java.flightscheduler.utils.flightcalendar;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;

public class AirCalendarIntent  extends Intent implements Parcelable {
    public enum Language {
        EN
    }
    public AirCalendarIntent(Context packageContext) {
        super(packageContext, AirCalendarDatePickerActivity.class);
    }

    public void isMonthLabels(boolean isLabel) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_MONTH_LABEL, isLabel);
    }

    public void isSingleSelect(boolean isSingle) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_SINGLE_SELECT, isSingle);
    }

    public void setSelectButtonText(String selectText) {
        this.putExtra(AirCalendarDatePickerActivity.SELECT_TEXT, selectText);
    }

    public void setResetBtnText(String resetBtnText) {
        this.putExtra(AirCalendarDatePickerActivity.RESET_TEXT, resetBtnText);
    }

    public void setWeekDaysLanguage(Language language) {
        this.putExtra(AirCalendarDatePickerActivity.WEEK_LANGUAGE, language.toString());
    }

}