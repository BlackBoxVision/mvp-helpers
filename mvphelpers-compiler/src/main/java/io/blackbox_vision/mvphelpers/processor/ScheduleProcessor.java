package io.blackbox_vision.mvphelpers.processor;


import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;

import io.blackbox_vision.mvphelpers.annotations.Schedule;
import io.blackbox_vision.mvphelpers.processor.base.Processor;


public final class ScheduleProcessor implements Processor {

    @Override
    public void process(@NotNull Element annotatedElement) {
        final Schedule schedule = annotatedElement.getAnnotation(Schedule.class);

        if (schedule != null) {

        }
    }
}
