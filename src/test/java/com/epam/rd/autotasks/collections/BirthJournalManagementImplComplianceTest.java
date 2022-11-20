package com.epam.rd.autotasks.collections;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AnalyzeClasses(packages = "com.epam.rd.autotasks", importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeArchives.class,
        ImportOption.DoNotIncludeJars.class
})
class BirthJournalManagementImplComplianceTest {

    static CtModel ctModel;

    @BeforeAll
    static void getCtModel() {
        SpoonAPI spoon;
        spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        ctModel = spoon.buildModel();
    }

    @ArchTest
    ArchRule ruleStreams = noClasses()
            .should()
            .callMethodWhere(target(describe("Methods Collection#stream() should not be used",
                    target -> "stream".equals(target.getName()) &&
                            target.getOwner().isAssignableTo(Collection.class) &&
                            target.getParameterTypes().isEmpty()
            )));

    @ArchTest
    ArchRule ruleCollection = noClasses().should().implement(Collection.class);

    @ArchTest
    ArchRule ruleMap = noClasses().should().implement(Map.class);

    @Test
    void testComplianceLambda() {
        List<CtLambda<?>> list = ctModel.filterChildren((Filter<CtLambda<?>>) el -> true).list();
        assertTrue(list.isEmpty(),
                "Lambdas are not allowed in this project but was: " +
                        list);
    }

    @Test
    void testNoMorePublicMethods() {
        List<CtMethod<?>> methods = ctModel.filterChildren((Filter<CtType<?>>) el ->
                        el.getQualifiedName().equals(BirthJournalManagementImpl.class.getName()))
                .filterChildren((CtMethod<?> m) -> m.getParent(new TypeFilter<>(CtType.class))
                        .getQualifiedName().equals(BirthJournalManagementImpl.class.getName()))
                .list();
        List<String> actual = methods.stream()
                .filter(CtMethod::isPublic)
                .map(CtMethod::getSimpleName)
                .filter(n -> !n.equals("toString"))
                .sorted()
                .toList();
        assertEquals(6, actual.size(),
                "You can add only private methods but found: " + actual);
        // existence of default methods
        actual = methods.stream()
                .filter(m -> !(m.isPublic() || m.isPrivate() || m.isProtected()))
                .map(CtMethod::getSimpleName)
                .toList();
        assertEquals(0, actual.size(),
                "You can add only private methods but found: " + actual);
        actual = methods.stream()
                .filter(CtMethod::isProtected)
                .map(CtMethod::getSimpleName)
                .toList();
        assertEquals(0, actual.size(),
                "You can add only private methods but found: " + actual);
    }
}
