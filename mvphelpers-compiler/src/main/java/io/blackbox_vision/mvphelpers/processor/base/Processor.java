package io.blackbox_vision.mvphelpers.processor.base;

import com.sun.istack.internal.NotNull;

import javax.lang.model.element.Element;


public interface Processor {

    void process(@NotNull Element annotatedElement);
}
