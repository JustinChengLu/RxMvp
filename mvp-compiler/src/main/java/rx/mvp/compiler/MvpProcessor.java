package rx.mvp.compiler;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import rx.mvp.annotation.Contract;


/**
 * @author justin on 2019/02/20 09:55
 * @version V1.0
 */
@AutoService(Processor.class)
public class MvpProcessor extends AbstractProcessor {

    private Class[] supportedAnnotationTypes = {Contract.Model.class, Contract.class, Contract.View.class, Contract.Presenter.class};
    private MvpFileGenerator mvpFileGenerator;

    private HashMap<Class<?>, ArrayList<Element>> elements = new HashMap<>();

    {
        Arrays.stream(supportedAnnotationTypes).forEach(it -> elements.put(it, new ArrayList<>()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Arrays.stream(supportedAnnotationTypes).map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        if (mvpFileGenerator == null) {
            mvpFileGenerator = new MvpFileGenerator(processingEnv);
        }
        mvpFileGenerator.process(env);
        return false;
    }

}
