package com.java.flightscheduler.utils.flightcalendar;

public interface DatePickerController {
	int getMaxYear();

	void onDayOfMonthSelected(int year, int month, int day);

    void onDateRangeSelected(final AirMonthAdapter.SelectedDays<AirMonthAdapter.CalendarDay> selectedDays);
}