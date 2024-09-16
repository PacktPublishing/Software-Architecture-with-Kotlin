package fcis.fcis

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import kotlin.test.Test

class LayeredArchitectureTest {
    val classes = ClassFileImporter().importPackages("fcis")

    @Test
    fun `layer dependencies are_respected`() {
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Imperative Shell")
            .definedBy("fcis.shell..")
            .layer("Functional Core")
            .definedBy("fcis.core..")
            .whereLayer("Imperative Shell")
            .mayNotBeAccessedByAnyLayer()
            .whereLayer("Functional Core")
            .mayOnlyBeAccessedByLayers("Imperative Shell")
            .check(classes)
    }
}
