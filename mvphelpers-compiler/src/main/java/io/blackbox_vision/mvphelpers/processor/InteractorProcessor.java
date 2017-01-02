package io.blackbox_vision.mvphelpers.processor;

import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;

import io.blackbox_vision.mvphelpers.annotations.Interactor;
import io.blackbox_vision.mvphelpers.processor.base.Processor;


public final class InteractorProcessor implements Processor {

    @Override
    public void process(@NotNull Element annotatedElement) {
        final Interactor interactor = annotatedElement.getAnnotation(Interactor.class);

        if (interactor != null) {

        }
    }
}
