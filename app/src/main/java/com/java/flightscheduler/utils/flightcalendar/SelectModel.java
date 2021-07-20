package com.java.flightscheduler.utils.flightcalendar;

class SelectModel {
    private int firstYear;
    private int firstMonth;
    private int firstDay;
    private int lastYear;
    private int lastMonth;
    private int lastDay;
    private boolean isSelected;

    int getFirstYear() {
        return firstYear;
    }

    void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    int getFirstMonth() {
        return firstMonth;
    }

    void setFirstMonth(int firstMonth) {
        this.firstMonth = firstMonth;
    }

    int getFirstDay() {
        return firstDay;
    }

    void setFirstDay(int firstDay) {
        this.firstDay = firstDay;
    }

    int getLastYear() {
        return lastYear;
    }

    void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    int getLastMonth() {
        return lastMonth;
    }

    void setLastMonth(int lastMonth) {
        this.lastMonth = lastMonth;
    }

    int getLastDay() {
        return lastDay;
    }

    void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected() {
        isSelected = true;
    }
}
