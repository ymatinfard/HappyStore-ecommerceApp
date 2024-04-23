import com.matin.happystore.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

import org.gradle.kotlin.dsl.named

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            val extension = extensions.getByType(DetektExtension::class.java)
            configureDetekt(extension)
        }
    }
}

fun Project.configureDetekt(extension: DetektExtension) =
    extension.apply {
        tasks.named<Detekt>("detekt") {
            buildUponDefaultConfig = true
            config.setFrom(files(project.rootDir.resolve("config/detekt_custom_config.yml")))
        }
        dependencies {
            "detektPlugins"(libs.findLibrary("detekt-formatting").get())
        }
    }
