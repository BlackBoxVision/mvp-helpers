package io.blackbox_vision.helpers.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import io.blackbox_vision.helpers.R;


public final class DrawableUtils {

    private DrawableUtils() { }

    public static Drawable applyColorFilter(@NonNull Context context, @DrawableRes int drawableId, @ColorRes int colorId, @NonNull PorterDuff.Mode mode) {
        Drawable d = ContextCompat.getDrawable(context, drawableId);
        int color = ContextCompat.getColor(context, colorId);

        d.setColorFilter(color, mode);

        return d;
    }

    public static Drawable applyColorFilter(@NonNull Context context, @DrawableRes int drawableId) {
        return applyColorFilter(context, drawableId, R.color.colorAccent, PorterDuff.Mode.SRC_ATOP);
    }
}
