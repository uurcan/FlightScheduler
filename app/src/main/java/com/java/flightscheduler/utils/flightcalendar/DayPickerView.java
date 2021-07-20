package com.java.flightscheduler.utils.flightcalendar;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DayPickerView extends RecyclerView {
    private DatePickerController mController;

    protected Context mContext;
    protected AirMonthAdapter mAdapter;
    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    protected int mMaxActiveMonth = -1;
    protected boolean isBooking = false;
    protected boolean isMonthDayLabels = false;
    protected boolean isSingleSelect = false;
    protected int mSetStartYear = -1;
    protected int mFirstDayOfWeek;
    protected ArrayList<String> mBookingDates;

    private SelectModel mSelectModel = null;


    public DayPickerView(Context context) {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            init(context);
        }
    }

    public void setController(DatePickerController mController) {
        this.mController = mController;
        setUpAdapter();
        setAdapter(mAdapter);
    }

    public void setShowBooking(boolean isbooking) {
        this.isBooking = isbooking;
    }

    public void setBookingDateArray(ArrayList<String> dates) {
        this.mBookingDates = dates;
    }

    public void setSelected(SelectModel date) {
        this.mSelectModel = date;
    }

    public void setIsMonthDayLabel(boolean isLabel) {
        this.isMonthDayLabels = isLabel;
    }

    public void setIsSingleSelect(boolean isSingle) {
        this.isSingleSelect = isSingle;
    }

    public void setMaxActiveMonth(int maxActiveMonth) {
        this.mMaxActiveMonth = maxActiveMonth;
    }


    public void setStartYear(int startYear) {
        this.mSetStartYear = startYear;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.mFirstDayOfWeek = firstDayOfWeek;
    }

    public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;
        setUpListView();
    }

    protected void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new AirMonthAdapter(getContext(), mController, isBooking, isMonthDayLabels, isSingleSelect, mBookingDates, mSelectModel, mMaxActiveMonth, mSetStartYear, mFirstDayOfWeek);
        }
        mAdapter.notifyDataSetChanged();
    }


    protected void setUpListView() {
        setVerticalScrollBarEnabled(false);

        OnScrollListener onScrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final AirMonthView child = (AirMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };

        addOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }
}