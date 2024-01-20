plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.revolut.jooq-docker") version "0.3.9"
    id("io.freefair.lombok") version "8.4"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

jooq {
    image {
        repository = "postgres"
        tag = "14"
        containerName = "jooq-postgres"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.flywaydb:flyway-core")
    implementation("com.github.scribejava:scribejava:8.3.3")
    implementation("com.github.scribejava:scribejava-apis:8.3.3")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    jdbc("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.19.0"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.wiremock:wiremock:3.3.1")

}

tasks {
    generateJooqClasses {
        basePackageName = "org.example.canvassync.db"
        outputSchemaToDefault = setOf("public")
        customizeGenerator {
            database
                .withExcludes("public\\.flyway_schema_history")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent != null) return
                println("\nTest result: ${result.resultType}")
                println(
                    """Test summary: ${result.testCount} total, ${result.successfulTestCount} succeeded, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped"""
                )
            }
        })
    }


    clean {
        dependsOn("cleanGenerateJooqClasses")
    }
}
