package eu.f3rog.blade.compiler.mvp;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import blade.Presenter;
import eu.f3rog.blade.compiler.ProcessorModule;
import eu.f3rog.blade.compiler.builder.ClassManager;
import eu.f3rog.blade.compiler.util.ProcessorError;

/**
 * Class {@link MvpProcessorModule}
 *
 * @author FrantisekGazo
 * @version 2015-12-19
 */
public class MvpProcessorModule implements ProcessorModule {

    @Override
    public void process(ProcessingEnvironment processingEnvironment, TypeElement bladeElement) throws ProcessorError {
        ClassManager.getInstance()
                .getHelper(bladeElement)
                .tryGetModule(PresenterScopeHelperModule.class);
    }

    @Override
    public void process(ProcessingEnvironment processingEnvironment, RoundEnvironment roundEnv) throws ProcessorError {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Presenter.class);
        for (Element e : elements) {
            ClassManager.getInstance()
                    .getHelper((TypeElement) e.getEnclosingElement())
                    .getModule(PresenterHelperModule.class)
                    .add((VariableElement) e);
        }
    }

}
