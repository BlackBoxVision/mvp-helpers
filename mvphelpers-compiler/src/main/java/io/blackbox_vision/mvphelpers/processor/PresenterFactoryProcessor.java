package io.blackbox_vision.mvphelpers.processor;


import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;

import io.blackbox_vision.mvphelpers.annotations.PresenterFactory;
import io.blackbox_vision.mvphelpers.processor.base.Processor;


public final class PresenterFactoryProcessor implements Processor {

    @Override
    public void process(@NotNull Element annotatedElement) {
        final PresenterFactory presenterFactory = annotatedElement.getAnnotation(PresenterFactory.class);

        if (presenterFactory != null) {

        }
    }
}
