import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm") version "1.4.0"
    id("com.google.protobuf") version "0.8.12"
    application
    id("com.github.johnrengelman.shadow") version "6.0.0"

    // For testing
    id("com.adarshr.test-logger") version "2.0.0"
    id("build-time-tracker") version "0.11.1"
}

repositories {
    google()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // HTTP
    implementation("io.ktor:ktor-client-cio:1.4.0")

    // HTML parsing
    implementation("org.jsoup:jsoup:1.13.1")

    // gRPC
    implementation("com.google.protobuf:protobuf-java-util:3.12.2")
    implementation("io.grpc:grpc-protobuf:1.30.0")
    implementation("io.grpc:grpc-services:1.30.0")
    implementation("io.grpc:grpc-stub:1.30.0")
    implementation("io.grpc:grpc-kotlin-stub:0.1.5")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    runtimeOnly("io.grpc:grpc-netty-shaded:1.30.0")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.8.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // testing
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    implementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.12.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.30.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(buildDir.resolve("generated/source/proto/main/grpc"))
            srcDirs(buildDir.resolve("generated/source/proto/main/java"))
        }
    }
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir(buildDir.resolve("generated/source/proto/main/grpckt"))
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "fetcher.MainKt"
}

tasks.shadowJar {
    manifest {
        attributes("Main-Class" to application.mainClassName)
    }
}

tasks.register<JavaExec>("server") {
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath
    main = application.mainClassName
}

val serverStartScripts = tasks.register<CreateStartScripts>("serverStartScripts") {
    mainClassName = application.mainClassName
    applicationName = "fetcher-server"
    outputDir = tasks.named<CreateStartScripts>("startScripts").get().outputDir
    classpath = tasks.named<CreateStartScripts>("startScripts").get().classpath
}

tasks.named("startScripts") {
    dependsOn(serverStartScripts)
}

// testing
buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["barstyle"] = "ascii"
            options["shortenTaskNames"] = "false"
        }
    }
}

testlogger {
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
