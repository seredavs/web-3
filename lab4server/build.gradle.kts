plugins {
	java
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jooq.jooq-codegen-gradle") version "3.19.30"
}

repositories {
	mavenCentral()
}

dependencies {
    // Api
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    implementation("io.jsonwebtoken:jjwt:0.13.0")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.password4j:password4j:1.8.4")

    // Database
    implementation("org.jooq:jooq:3.19.30")
    implementation("org.postgresql:postgresql")

    // Jooq
    jooqCodegen("org.postgresql:postgresql")
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/studs"
            user = "s467432"
            password = "jvvDb36xf6BWSK5h"
        }

        generator {
            name = "org.jooq.codegen.JavaGenerator"

            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "s467432"
            }

            target {
                packageName = "lab4.database.jooq"
                directory = "src/main/java"
            }
        }
    }
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}
