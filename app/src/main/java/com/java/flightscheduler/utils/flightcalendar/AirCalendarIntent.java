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

    public void setCustomWeekDays(ArrayList<String> weekDay) {
        if (weekDay.size() != 7) {
            throw new RuntimeException("Week days must be exactly seven. Currently you have: " + weekDay.size());
        }
        this.putStringArrayListExtra(AirCalendarDatePickerActivity.CUSTOM_WEEK_ABREVIATIONS, weekDay);
    }

    public void setWeekDaysLanguage(Language language) {
        this.putExtra(AirCalendarDatePickerActivity.WEEK_LANGUAGE, language.toString());
    }

    public void setWeekStart(int weekStart) {
        this.putExtra(AirCalendarDatePickerActivity.WEEK_START, weekStart);
    }

    public void isBooking(boolean isBooking) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_BOOKING, isBooking);
    }

    public void setBookingDateArray(ArrayList<String> arrays) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_BOOKING_DATES, arrays);
    }

    /**
     * Lets the specified date be preselected in the calendar.
     * @param isSelect default false
     */
    public void isSelect(boolean isSelect) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_SELECT, isSelect);
    }

    /**
     * Lets the specified date be preselected in the calendar. ( Start date )
     * (isSelect option should be true)
     * @param year
     * @param month
     * @param day
     */
    public void setStartDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SD, day);

    }

    /**
     * Lets the specified date be preselected in the calendar. ( End date )
     * * (isSelect option should be true)
     * @param year
     * @param month
     * @param day
     */
    public void setEndDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_ED, day);
    }

    /**
     * When setting for 3 months, the date after 3 months is disabled.
     * @param activeMonth e.g 3
     */
    public void setActiveMonth(int activeMonth){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_ACTIVE_MONTH_NUM , activeMonth);
    }

    /**
     * If the current date is November 1, 2018, the calendar will be activated until November 1, 2019.
     * @param maxYear e.g 2020
     */
    public void setMaxYear(int maxYear){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_MAX_YEAR , maxYear);
    }

    public void setStartYear(int startYear){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_START_YEAR , startYear);
    }
}