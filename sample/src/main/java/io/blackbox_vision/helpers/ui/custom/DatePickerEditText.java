package io.blackbox_vision.helpers.ui.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;

import io.blackbox_vision.helpers.ui.fragment.DatePickerFragment;


public final class DatePickerEditText extends TextInputEditText {
    private static final String TAG = DatePickerEditText.class.getSimpleName();

    private OnFocusChangeListener focusChangeListener;
    private FragmentManager manager;
    private Calendar date;

    public DatePickerEditText(Context context) {
        super(context);
        init();
    }

    public DatePickerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DatePickerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this::onFocusChange);
    }

    private void initDatePickerFragment() {
        final DatePickerFragment datePickerFragment = new DatePickerFragment();

        if (null != date) {
            datePickerFragment.setDate(date);
        }

        datePickerFragment.setOnDateSetListener(this::onDateSet);
        datePickerFragment.show(manager, TAG);
    }

    public void onFocusChange(View view, boolean isFocused) {
        if (isFocused) {
            initDatePickerFragment();
        }

        if (null != focusChangeListener) {
            focusChangeListener.onFocusChange(view, isFocused);
        }
    }

    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        final Calendar date = Calendar.getInstance(Locale.getDefault());

        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        this.date = date;
    }

    public DatePickerEditText setManager(@NonNull FragmentManager manager) {
        this.manager = manager;
        return this;
    }

    public DatePickerEditText setDate(@NonNull Calendar date) {
        this.date = date;
        return this;
    }

    public DatePickerEditText setFocusChangeListener(OnFocusChangeListener focusChangeListener) {
        this.focusChangeListener = focusChangeListener;
        return this;
    }
}
