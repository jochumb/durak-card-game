plugins {
    kotlin("jvm") version "1.3.71"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

group = "nl.jochumborger.durak"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.9")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.9")
    testImplementation("org.assertj:assertj-core:3.15.0")
}

tasks {
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}
