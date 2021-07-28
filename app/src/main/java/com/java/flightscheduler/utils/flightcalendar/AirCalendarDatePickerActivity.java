package com.java.flightscheduler.utils.flightcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.flightscheduler.R;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import timber.log.Timber;


public class AirCalendarDatePickerActivity extends AppCompatActivity implements DatePickerController {

    public final static String EXTRA_FLAG = "FLAG";
    public final static String EXTRA_IS_BOOKING = "IS_BOOING";
    public final static String EXTRA_IS_SELECT = "IS_SELECT";
    public final static String EXTRA_BOOKING_DATES = "BOOKING_DATES";
    public final static String EXTRA_SELECT_DATE_SY = "SELECT_START_DATE_Y";
    public final static String EXTRA_SELECT_DATE_SM = "SELECT_START_DATE_M";
    public final static String EXTRA_SELECT_DATE_SD = "SELECT_START_DATE_D";
    public final static String EXTRA_SELECT_DATE_EY = "SELECT_END_DATE_Y";
    public final static String EXTRA_SELECT_DATE_EM = "SELECT_END_DATE_M";
    public final static String EXTRA_SELECT_DATE_ED = "SELECT_END_DATE_D";
    public final static String EXTRA_IS_MONTH_LABEL = "IS_MONTH_LABEL";
    public final static String EXTRA_IS_SINGLE_SELECT = "IS_SINGLE_SELECT";
    public final static String EXTRA_ACTIVE_MONTH_NUM = "ACTIVE_MONTH_NUMBER";
    public final static String EXTRA_MAX_YEAR = "MAX_YEAR";
    public final static String EXTRA_START_YEAR = "START_YEAR";

    public final static String SELECT_TEXT = "SELECT_TEXT";
    public final static String RESET_TEXT = "RESET_TEXT";
    public final static String CUSTOM_WEEK_ABREVIATIONS = "CUSTOM_WEEK_ABREVIATIONS";
    public final static String WEEK_LANGUAGE = "WEEK_LANGUAGE";
    public final static String WEEK_START = "WEEK_START";

    public final static String RESULT_SELECT_START_DATE = "start_date";
    public final static String RESULT_SELECT_END_DATE = "end_date";
    public final static String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public final static String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public final static String RESULT_FLAG = "flag";
    public final static String RESULT_TYPE = "result_type";
    public final static String RESULT_STATE = "result_state";

    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_popup_msg;

    private RelativeLayout rl_checkout_select_info_popup;

    private String SELECT_START_DATE = "";
    private String SELECT_END_DATE = "";
    private int BASE_YEAR = 2018;
    private int firstDayOfWeek = Calendar.SUNDAY;

    private String FLAG = "all";
    private boolean isSelect = false;
    private boolean isBooking = false;
    private boolean isMonthLabel = false;
    private boolean isSingleSelect = false;
    private List<String> weekDays = new ArrayList<>();
    private ArrayList<String> dates;
    private SelectModel selectDate;

    private int sYear = 0;
    private int sMonth = 0;
    private int sDay = 0;
    private int eYear = 0;
    private int eMonth = 0;
    private int eDay = 0;

    private AirCalendarIntent.Language language = AirCalendarIntent.Language.EN;

    private int maxActivityMonth = -1;
    private int maxYear = -1;
    private int mSetStartYear = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_air_calendar);

        Intent getData = getIntent();
        FLAG = getData.getStringExtra(EXTRA_FLAG) != null ? getData.getStringExtra(EXTRA_FLAG) : "all";
        isBooking = getData.getBooleanExtra(EXTRA_IS_BOOKING, false);
        isSelect = getData.getBooleanExtra(EXTRA_IS_SELECT, false);
        isMonthLabel = getData.getBooleanExtra(EXTRA_IS_MONTH_LABEL, false);
        isSingleSelect = getData.getBooleanExtra(EXTRA_IS_SINGLE_SELECT, false);
        dates = getData.getStringArrayListExtra(EXTRA_BOOKING_DATES);
        maxActivityMonth = getData.getIntExtra(EXTRA_ACTIVE_MONTH_NUM, -1);
        maxYear = getData.getIntExtra(EXTRA_MAX_YEAR, -1);
        mSetStartYear = getData.getIntExtra(EXTRA_START_YEAR, -1);

        sYear = getData.getIntExtra(EXTRA_SELECT_DATE_SY, 0);
        sMonth = getData.getIntExtra(EXTRA_SELECT_DATE_SM, 0);
        sDay = getData.getIntExtra(EXTRA_SELECT_DATE_SD, 0);

        eYear = getData.getIntExtra(EXTRA_SELECT_DATE_EY, 0);
        eMonth = getData.getIntExtra(EXTRA_SELECT_DATE_EM, 0);
        eDay = getData.getIntExtra(EXTRA_SELECT_DATE_ED, 0);

        if (sYear == 0 || sMonth == 0 || sDay == 0
                || eYear == 0 || eMonth == 0 || eDay == 0) {
            selectDate = new SelectModel();
            isSelect = false;
        }

        if (getIntent().hasExtra(CUSTOM_WEEK_ABREVIATIONS)) {
            weekDays.clear();
            weekDays.addAll(Objects.requireNonNull(getIntent().getStringArrayListExtra(CUSTOM_WEEK_ABREVIATIONS)));
            AirCalendarUtils.setWeekdays(weekDays);
        }

        if (getIntent().hasExtra(WEEK_LANGUAGE)) {
            language = AirCalendarIntent.Language.valueOf(getIntent().getStringExtra(WEEK_LANGUAGE));
            AirCalendarUtils.setLanguage(language);
        }

        init();

    }

    private void init() {
        TextView tv_done_btn = findViewById(R.id.rl_done_btn);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);
        tv_popup_msg = findViewById(R.id.tv_popup_msg);
        rl_checkout_select_info_popup = findViewById(R.id.rl_checkout_select_info_popup);
        TextView tv_reset_btn = findViewById(R.id.rl_reset_btn);
        RelativeLayout rl_popup_select_checkout_info_ok = findViewById(R.id.rl_popup_select_checkout_info_ok);
        rl_checkout_select_info_popup = findViewById(R.id.rl_checkout_select_info_popup);
        RelativeLayout rl_iv_back_btn_bg = findViewById(R.id.rl_iv_back_btn_bg);

        TextView tv_day_one = findViewById(R.id.tv_day_one);
        TextView tv_day_two = findViewById(R.id.tv_day_two);
        TextView tv_day_three = findViewById(R.id.tv_day_three);
        TextView tv_day_four = findViewById(R.id.tv_day_four);
        TextView tv_day_five = findViewById(R.id.tv_day_five);
        TextView tv_day_six = findViewById(R.id.tv_day_six);
        TextView tv_day_seven = findViewById(R.id.tv_day_seven);

        if (getIntent().hasExtra(SELECT_TEXT)) {
            tv_done_btn.setText(getIntent().getStringExtra(SELECT_TEXT));
        }
        if (getIntent().hasExtra(RESET_TEXT)) {
            tv_reset_btn.setText(getIntent().getStringExtra(RESET_TEXT));
        }

        if (weekDays.isEmpty()) {
            if (language == AirCalendarIntent.Language.EN) {
                List<String> enList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.label_calendar_en)));
                weekDays.addAll(enList);
            }
        }

        firstDayOfWeek = getIntent().getIntExtra(WEEK_START, Calendar.SUNDAY);

        if (weekDays.isEmpty()) {
            throw new RuntimeException("Language not supported or non existent");
        } else {
            int weekStart = firstDayOfWeek - 2;

            for (int week = 0; week < 7; week++) {
                int weekDay = weekStart + week;
                if (weekDay < 0) {
                    weekDay += 7;
                }
                if (weekDay > 6) {
                    weekDay -= 7;
                }
                Timber.d(week + "/" + weekDay + ":" + weekDays.get(weekDay));
                switch (week) {
                    case 0:
                        tv_day_one.setText(weekDays.get(weekDay));
                        break;
                    case 1:
                        tv_day_two.setText(weekDays.get(weekDay));
                        break;
                    case 2:
                        tv_day_three.setText(weekDays.get(weekDay));
                        break;
                    case 3:
                        tv_day_four.setText(weekDays.get(weekDay));
                        break;
                    case 4:
                        tv_day_five.setText(weekDays.get(weekDay));
                        break;
                    case 5:
                        tv_day_six.setText(weekDays.get(weekDay));
                        break;
                    case 6:
                        tv_day_seven.setText(weekDays.get(weekDay));
                        break;

                }
            }

        }

        DayPickerView pickerView = findViewById(R.id.pickerView);
        pickerView.setIsMonthDayLabel(isMonthLabel);
        pickerView.setIsSingleSelect(isSingleSelect);
        pickerView.setMaxActiveMonth(maxActivityMonth);
        pickerView.setStartYear(mSetStartYear);

        pickerView.setFirstDayOfWeek(firstDayOfWeek);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);

        if (maxYear != -1 && maxYear > Integer.parseInt(new DateTime().toString("yyyy"))) {
            BASE_YEAR = maxYear;
        } else {
            BASE_YEAR = Integer.parseInt(dTime) + 2;
        }

        if (dates != null && dates.size() != 0 && isBooking) {
            pickerView.setShowBooking(true);
            pickerView.setBookingDateArray(dates);
        }

        if (isSelect) {
            selectDate = new SelectModel();
            selectDate.setSelected();
            selectDate.setFirstYear(sYear);
            selectDate.setFirstMonth(sMonth);
            selectDate.setFirstDay(sDay);
            selectDate.setLastYear(eYear);
            selectDate.setLastMonth(eMonth);
            selectDate.setLastDay(eDay);
            pickerView.setSelected(selectDate);
        }

        pickerView.setController(this);


        tv_done_btn.setOnClickListener(v -> {
            if ((SELECT_START_DATE == null || SELECT_START_DATE.equals("")) && (SELECT_END_DATE == null || SELECT_END_DATE.equals(""))) {
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";
            } else {
                if (!isSingleSelect) {
                    if (SELECT_START_DATE == null || SELECT_START_DATE.equals("")) {
                        tv_popup_msg.setText(R.string.text_missing_date);
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    } else if (SELECT_END_DATE == null || SELECT_END_DATE.equals("")) {
                        tv_popup_msg.setText(R.string.text_missing_date);
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    }
                }
            }


            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_SELECT_START_DATE, SELECT_START_DATE);
            resultIntent.putExtra(RESULT_SELECT_END_DATE, SELECT_END_DATE);
            resultIntent.putExtra(RESULT_SELECT_START_VIEW_DATE, tv_start_date.getText().toString());
            resultIntent.putExtra(RESULT_SELECT_END_VIEW_DATE, tv_end_date.getText().toString());
            resultIntent.putExtra(RESULT_FLAG, FLAG);
            resultIntent.putExtra(RESULT_TYPE, FLAG);
            resultIntent.putExtra(RESULT_STATE, "done");
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        tv_reset_btn.setOnClickListener(v -> {
            SELECT_START_DATE = "";
            SELECT_END_DATE = "";
            setContentView(R.layout.layout_air_calendar);
            init();
        });

        rl_popup_select_checkout_info_ok.setOnClickListener(v -> rl_checkout_select_info_popup.setVisibility(View.GONE));

        rl_iv_back_btn_bg.setOnClickListener(v -> finish());
    }

    @Override
    public int getMaxYear() {
        return BASE_YEAR;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        try {
            String start_month_str = String.format("%02d", (month + 1));
            String start_day_str = String.format("%02d", day);
            String startSetDate = year + start_month_str + start_day_str;

            String startDateDay = AirCalendarUtils.getDateDay(this, startSetDate, "yyyyMMdd", firstDayOfWeek);
            String startDate = year + "-" + start_month_str + "-" + start_day_str;
            String formattedStartDate = year + "-" + start_month_str + "-" + start_day_str + " " + startDateDay;
            tv_start_date.setText(formattedStartDate);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText("-");
            tv_end_date.setTextColor(0xff1abc9c);
            SELECT_START_DATE = startDate;
            SELECT_END_DATE = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateRangeSelected(AirMonthAdapter.SelectedDays<AirMonthAdapter.CalendarDay> selectedDays) {

        try {
            Calendar cl = Calendar.getInstance();

            cl.setTimeInMillis(selectedDays.getFirst().getDate().getTime());

            int start_month_int = (cl.get(Calendar.MONTH) + 1);
            String start_month_str = String.format("%02d", start_month_int);

            // 일
            int start_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String start_day_str = String.format("%02d", start_day_int);

            String startSetDate = cl.get(Calendar.YEAR) + start_month_str + start_day_str;
            String startDateDay = AirCalendarUtils.getDateDay(this, startSetDate, "yyyyMMdd", firstDayOfWeek);
            String startDate = cl.get(Calendar.YEAR) + "-" + start_month_str + "-" + start_day_str;

            cl.setTimeInMillis(selectedDays.getLast().getDate().getTime());

            // 월
            int end_month_int = (cl.get(Calendar.MONTH) + 1);
            String end_month_str = String.format("%02d", end_month_int);

            // 일
            int end_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String end_day_str = String.format("%02d", end_day_int);

            String endSetDate = cl.get(Calendar.YEAR) + end_month_str + end_day_str;
            String endDateDay = AirCalendarUtils.getDateDay(this, endSetDate, "yyyyMMdd", firstDayOfWeek);
            String endDate = cl.get(Calendar.YEAR) + "-" + end_month_str + "-" + end_day_str;

            tv_start_date.setText(startDate + " " + startDateDay);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText(endDate + " " + endDateDay);
            tv_end_date.setTextColor(0xff4a4a4a);

            SELECT_START_DATE = startDate;
            SELECT_END_DATE = endDate;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
