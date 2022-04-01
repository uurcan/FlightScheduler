package com.java.flightscheduler.utils.flightcalendar;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AirMonthAdapter extends RecyclerView.Adapter<AirMonthAdapter.ViewHolder> implements AirMonthView.OnDayClickListener {
    private static final int MONTHS_IN_YEAR = 12;
    private final Context mContext;
    private final DatePickerController mController;
    private final Calendar calendar;
    private final SelectedDays<CalendarDay> selectedDays;
    private final Integer firstMonth;
    private final boolean mCanSelectBeforeDay;
    private final boolean mIsSingleSelect;
    private boolean isShowBooking;
    private boolean isSelected = false;
    private boolean isMonthDayLabels;
    private boolean isSingleSelect;
    private int mMaxActiveMonth;
    private int mStartYear;
    private SelectModel mSelectModel;
    private ArrayList<String> mBookingDates;

    AirMonthAdapter(Context context,
                           DatePickerController datePickerController,
                           boolean showBooking,
                           boolean monthDayLabels,
                           boolean isSingle, ArrayList<String> bookingDates,
                           SelectModel selectedDay,
                           int maxActiveMonth,
                           int startYear,
                           int firstDayOfWeek
    ) {

        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        firstMonth = calendar.get(Calendar.MONTH);
        mCanSelectBeforeDay = false;
        mIsSingleSelect =  false;
        selectedDays = new SelectedDays<>();
        mContext = context;
        mController = datePickerController;
        isShowBooking = showBooking;
        mSelectModel = selectedDay;
        mBookingDates = bookingDates;
        isSingleSelect = isSingle;
        mMaxActiveMonth = maxActiveMonth;
        mStartYear = startYear;

        isMonthDayLabels = monthDayLabels;

        if (mSelectModel != null) {
            isSelected = mSelectModel.isSelected();
        }

        //init();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final AirMonthView airMonthView = new AirMonthView(mContext,isShowBooking, isMonthDayLabels, mBookingDates, mMaxActiveMonth, mStartYear);
        return new ViewHolder(airMonthView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final AirMonthView v = viewHolder.airMonthView;
        final HashMap<String, Integer> drawingParams = new HashMap<>();
        int month;
        int year;
        int startYear = calendar.get(Calendar.YEAR);
        if (mStartYear != -1) {
            startYear = mStartYear;
        }
        month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        year = position / MONTHS_IN_YEAR + startYear + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

        int selectedFirstDay = -1;
        int selectedLastDay = -1;
        int selectedFirstMonth = -1;
        int selectedLastMonth = -1;
        int selectedFirstYear = -1;
        int selectedLastYear = -1;

        if (selectedDays.getFirst() != null) {
            isSelected = false;
            selectedFirstDay = selectedDays.getFirst().day;
            selectedFirstMonth = selectedDays.getFirst().month;
            selectedFirstYear = selectedDays.getFirst().year;
        }

        if (selectedDays.getLast() != null) {
            isSelected = false;
            selectedLastDay = selectedDays.getLast().day;
            selectedLastMonth = selectedDays.getLast().month;
            selectedLastYear = selectedDays.getLast().year;
        }

        v.reuse();

        if (isSelected) {
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, mSelectModel.getFirstYear());
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, mSelectModel.getLastYear());
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, (mSelectModel.getFirstMonth() - 1));
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, (mSelectModel.getLastMonth() - 1));
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, mSelectModel.getFirstDay());
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_DAY, mSelectModel.getLastDay());
        } else {
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
            drawingParams.put(AirMonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        }

        drawingParams.put(AirMonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(AirMonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(AirMonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        v.setMonthParams(drawingParams);
        v.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (((mController.getMaxYear() - calendar.get(Calendar.YEAR))) * MONTHS_IN_YEAR);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final AirMonthView airMonthView;

        private ViewHolder(View itemView, AirMonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            airMonthView = (AirMonthView) itemView;
            airMonthView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            airMonthView.setClickable(true);
            airMonthView.setOnDayClickListener(onDayClickListener);
        }
    }

    private void init() {
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
    }

    public void onDayClick(AirMonthView airMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    private void onDayTapped(CalendarDay calendarDay) {
        mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }

    private void setSelectedDay(CalendarDay calendarDay) {

        if (isSingleSelect) {
            selectedDays.setFirst(calendarDay);
            selectedDays.setLast(null);
        } else {
            if (!mIsSingleSelect && selectedDays.getFirst() != null && selectedDays.getLast() == null) {
                selectedDays.setLast(calendarDay);

                CalendarDay firstDays = selectedDays.getFirst();
                int selectedFirstDay = firstDays.day;
                int selectedFirstMonth = firstDays.month;
                int selectedFirstYear = firstDays.year;

                CalendarDay lastDays = selectedDays.getLast();
                int selectedLastDay = lastDays.day;
                int selectedLastMonth = lastDays.month;
                int selectedLastYear = lastDays.year;

                if ((selectedFirstDay != -1 && selectedLastDay != -1
                        && selectedFirstYear == selectedLastYear &&
                        selectedFirstMonth == selectedLastMonth &&
                        selectedFirstDay > selectedLastDay)) {
                    int tempSelectDay = selectedFirstDay;
                    selectedFirstDay = selectedLastDay;
                    selectedLastDay = tempSelectDay;

                    firstDays.day = selectedFirstDay;

                    lastDays.day = selectedLastDay;

                    selectedDays.setFirst(firstDays);
                    selectedDays.setLast(lastDays);

                    if (!mCanSelectBeforeDay) {
                        selectedDays.setLast(null);
                        notifyDataSetChanged();
                        return;
                    }
                }
                if ((selectedFirstDay != -1 && selectedLastDay != -1
                        && selectedFirstYear == selectedLastYear &&
                        selectedFirstMonth > selectedLastMonth)) {
                    int tempSelectMonth = selectedFirstMonth;
                    selectedFirstMonth = selectedLastMonth;
                    selectedLastMonth = tempSelectMonth;
                    int tempSelectDay = selectedFirstDay;
                    selectedFirstDay = selectedLastDay;
                    selectedLastDay = tempSelectDay;

                    firstDays.day = selectedFirstDay;
                    firstDays.month = selectedFirstMonth;

                    lastDays.day = selectedLastDay;
                    lastDays.month = selectedLastMonth;

                    selectedDays.setFirst(firstDays);
                    selectedDays.setLast(lastDays);

                    if (!mCanSelectBeforeDay) {
                        selectedDays.setLast(null);
                        notifyDataSetChanged();
                        return;
                    }
                }

                if ((selectedFirstDay != -1 && selectedLastDay != -1
                        && selectedFirstYear > selectedLastYear)) {
                    int tempSelectYear = selectedFirstYear;
                    selectedFirstYear = selectedLastYear;
                    selectedLastYear = tempSelectYear;
                    int tempSelectMonth = selectedFirstMonth;
                    selectedFirstMonth = selectedLastMonth;
                    selectedLastMonth = tempSelectMonth;
                    int tempSelectDay = selectedFirstDay;
                    selectedFirstDay = selectedLastDay;
                    selectedLastDay = tempSelectDay;

                    firstDays.day = selectedFirstDay;
                    firstDays.month = selectedFirstMonth;
                    firstDays.year = selectedFirstYear;

                    lastDays.day = selectedLastDay;
                    lastDays.month = selectedLastMonth;
                    lastDays.year = selectedLastYear;

                    selectedDays.setFirst(firstDays);
                    selectedDays.setLast(lastDays);

                    if (!mCanSelectBeforeDay) {
                        selectedDays.setLast(null);
                        notifyDataSetChanged();
                        return;
                    }
                }

                if (selectedDays.getFirst().month < calendarDay.month) {
                    for (int i = 0; i < selectedDays.getFirst().month - calendarDay.month - 1; ++i)
                        mController.onDayOfMonthSelected(selectedDays.getFirst().year, selectedDays.getFirst().month + i, selectedDays.getFirst().day);
                }

                mController.onDateRangeSelected(selectedDays);
            } else if (selectedDays.getLast() != null) {
                selectedDays.setFirst(calendarDay);
                selectedDays.setLast(null);
            } else {
                selectedDays.setFirst(calendarDay);
            }
        }

        notifyDataSetChanged();
    }

    public static class CalendarDay implements Serializable {
        private static final long serialVersionUID = -5456695978688356202L;
        private Calendar calendar;

        int day;
        int month;
        int year;

        CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        Date getDate() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day);
            return calendar.getTime();
        }

        @NotNull
        @Override
        public String toString() {
            return "{ year: " +
                    year +
                    ", month: " +
                    month +
                    ", day: " +
                    day +
                    " }";
        }
    }

    public static class SelectedDays<K> implements Serializable {
        private static final long serialVersionUID = 3942549765282708376L;
        private K first;
        private K last;

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        K getLast() {
            return last;
        }

        void setLast(K last) {
            this.last = last;
        }
    }
}