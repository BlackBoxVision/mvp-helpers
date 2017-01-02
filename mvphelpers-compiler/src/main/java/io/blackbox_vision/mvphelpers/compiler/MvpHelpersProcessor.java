package io.blackbox_vision.mvphelpers.compiler;

import com.google.auto.service.AutoService;
import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import io.blackbox_vision.mvphelpers.annotations.Background;
import io.blackbox_vision.mvphelpers.annotations.Interactor;
import io.blackbox_vision.mvphelpers.annotations.PresenterFactory;
import io.blackbox_vision.mvphelpers.annotations.Schedule;
import io.blackbox_vision.mvphelpers.annotations.UIThread;

import io.blackbox_vision.mvphelpers.processor.BackgroundProcessor;
import io.blackbox_vision.mvphelpers.processor.InteractorProcessor;
import io.blackbox_vision.mvphelpers.processor.PresenterFactoryProcessor;
import io.blackbox_vision.mvphelpers.processor.ScheduleProcessor;
import io.blackbox_vision.mvphelpers.processor.UIThreadProcessor;
import io.blackbox_vision.mvphelpers.processor.base.Processor;


@SuppressWarnings("all")
@AutoService(Processor.class)
public final class MvpHelpersProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Messager messager;
    private Filer filer;
    private Types types;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        elementUtils = env.getElementUtils();
        messager = env.getMessager();
        types = env.getTypeUtils();
        filer = env.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> classNames = new LinkedHashSet<>();

        for (Class<? extends Annotation> cls: getSupportedAnnotations()) {
            classNames.add(cls.getCanonicalName());
        }

        return classNames;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        //TODO Make processors classes use java poet to generate new classes that extends
        processByType(roundEnvironment, new PresenterFactoryProcessor(), PresenterFactory.class);
        processByType(roundEnvironment, new InteractorProcessor(), Interactor.class);

        //TODO Check this
        processByType(roundEnvironment, new BackgroundProcessor(), Background.class);
        processByType(roundEnvironment, new UIThreadProcessor(), UIThread.class);
        processByType(roundEnvironment, new ScheduleProcessor(), Schedule.class);

        return false;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(Interactor.class);
        annotations.add(UIThread.class);
        annotations.add(PresenterFactory.class);
        annotations.add(Schedule.class);
        annotations.add(Background.class);

        return annotations;
    }

    private void processByType(@NotNull RoundEnvironment env,
                               @NotNull Processor processor,
                               @NotNull Class<? extends Annotation> annotation) {
        for (Element annotatedElement: env.getElementsAnnotatedWith(annotation)) {
            processor.process(annotatedElement);
        }
    }
}
