package com.mborodin.javacourse.processor;

import com.mborodin.javacourse.annotation.Info;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.mborodin.javacourse.annotation.Info")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class InfoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Info.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }

            TypeElement typeElement = (TypeElement) element;
            String originalClassName = typeElement.getSimpleName().toString();
            String generatedClassName = originalClassName + "Info";

            PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
            String packageName = packageElement.getQualifiedName().toString();

            List<String> fields = new ArrayList<>();
            List<String> methods = new ArrayList<>();

            for (Element enclosed : typeElement.getEnclosedElements()) {
                if (enclosed.getKind() == ElementKind.FIELD) {
                    fields.add(enclosed.asType().toString() + " " + enclosed.getSimpleName());
                } else if (enclosed.getKind() == ElementKind.METHOD) {
                    methods.add(enclosed.asType().toString() + " " + enclosed.getSimpleName() + "()");
                }
            }

            try {
                String fullGeneratedName = packageName.isEmpty()
                        ? generatedClassName
                        : packageName + "." + generatedClassName;

                JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fullGeneratedName);
                try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                    if (!packageName.isEmpty()) {
                        out.println("package " + packageName + ";");
                        out.println();
                    }

                    out.println("public class " + generatedClassName + " {");
                    out.println();
                    out.println("    public void printInfo() {");
                    out.println("        System.out.println(\"=== Інформація про клас: " + originalClassName + " ===\");");
                    out.println("        System.out.println(\"Поля:\");");
                    for (String field : fields) {
                        out.println("        System.out.println(\"  - " + field + "\");");
                    }
                    out.println("        System.out.println(\"Методи:\");");
                    for (String method : methods) {
                        out.println("        System.out.println(\"  - " + method + "\");");
                    }
                    out.println("    }");
                    out.println("}");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}