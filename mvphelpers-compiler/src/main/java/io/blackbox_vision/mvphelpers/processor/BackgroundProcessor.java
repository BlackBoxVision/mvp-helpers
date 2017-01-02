package io.blackbox_vision.mvphelpers.processor;


import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;

import io.blackbox_vision.mvphelpers.annotations.Background;
import io.blackbox_vision.mvphelpers.processor.base.Processor;


public final class BackgroundProcessor implements Processor {

    @Override
    public void process(@NotNull Element annotatedElement) {
        final Background background = annotatedElement.getAnnotation(Background.class);

        if (background != null) {

        }
    }
}
