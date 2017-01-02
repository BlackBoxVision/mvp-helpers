package io.blackbox_vision.mvphelpers.processor;


import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;

import io.blackbox_vision.mvphelpers.annotations.UIThread;
import io.blackbox_vision.mvphelpers.processor.base.Processor;

public final class UIThreadProcessor implements Processor {

    @Override
    public void process(@NotNull Element annotatedElement) {
        final UIThread uiThread = annotatedElement.getAnnotation(UIThread.class);

        if (uiThread != null) {

        }
    }
}
