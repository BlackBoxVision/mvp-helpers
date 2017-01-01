package io.blackbox_vision.mvphelpers.compiler;

import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import io.blackbox_vision.mvphelpers.annotations.Background;
import io.blackbox_vision.mvphelpers.annotations.Schedule;
import io.blackbox_vision.mvphelpers.annotations.UIThread;


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
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(UIThread.class);
        annotations.add(Schedule.class);
        annotations.add(Background.class);

        return annotations;
    }
}
