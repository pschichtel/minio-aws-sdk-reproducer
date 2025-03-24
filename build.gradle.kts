plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "ai.vier.cvg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "ReproKt"
}

dependencies {
    constraints {
        implementation("org.apache.httpcomponents:httpclient:4.5.14") {
            because("httpclient 4.5.13 fails to verify *.s3.amazonaws.com certificates, see https://github.com/burrunan/gradle-s3-build-cache/issues/23")
        }
    }
    implementation(platform("software.amazon.awssdk:bom:2.30.0"))
    implementation("software.amazon.awssdk:sso") {
        because("Needed to automatically enable AWS SSO login, see https://stackoverflow.com/a/67824174")
    }
    implementation ("software.amazon.awssdk:ssooidc") {
        because("Needed to automatically enable AWS SSO login, see https://stackoverflow.com/a/67824174")
    }
    implementation("software.amazon.awssdk:s3") {
        // We do not use netty client so far
        exclude("software.amazon.awssdk", "netty-nio-client")
    }
    runtimeOnly("software.amazon.awssdk:sts")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}