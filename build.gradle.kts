import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
	application
	jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14

configurations {
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudVersion"] = "Hoxton.SR6"

val ktlint: Configuration by configurations.creating

application {
	mainClass.set("com.example.spring_api.SpringApiExampleApplicationKt")
}

jacoco {
	toolVersion = "0.8.5"
	applyTo(tasks.run.get())
}

dependencies {
	ktlint("com.pinterest:ktlint:0.37.2")

	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
	implementation("org.apache.commons:commons-lang3")
	implementation("org.springdoc:springdoc-openapi-ui:1.4.3")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("mysql:mysql-connector-java")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
	inputs.files(inputFiles)
	outputs.dir(outputDir)

	description = "Check Kotlin code style."
	classpath = ktlint
	main = "com.pinterest.ktlint.Main"
	args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
	inputs.files(inputFiles)
	outputs.dir(outputDir)

	description = "Fix Kotlin code style deviations."
	classpath = ktlint
	main = "com.pinterest.ktlint.Main"
	args = listOf("-F", "src/**/*.kt")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
		sourceCompatibility = JavaVersion.VERSION_1_8.toString()
		targetCompatibility = JavaVersion.VERSION_1_8.toString()
	}
}

tasks.compileKotlin {
	kotlinOptions {
		jvmTarget = "11"
		allWarningsAsErrors = true
	}
	dependsOn(ktlintCheck)
}

tasks.compileTestKotlin {
	kotlinOptions {
		jvmTarget = "11"
		allWarningsAsErrors = true
	}
	dependsOn(ktlintCheck)
}

tasks.check {
	dependsOn(ktlintCheck, tasks.jacocoTestReport)
}

tasks.test {
	project.property("snippetsDir")?.let { outputs.dir(it) }
	dependsOn(ktlintCheck)
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
	extensions.configure(JacocoTaskExtension::class) {
		classDumpDir = file("$buildDir/jacoco/classpathdumps")
	}
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.isEnabled = true
		csv.isEnabled = false
		html.isEnabled = true
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			excludes = listOf(
				"**/error*/**",
				"**/config*/**",
				"**/filter*/**",
				"**/model*/**",
				"**/request*/**",
				"**/database*/**",
				"**/enum*/**"
			)
			limit {
				minimum = "0.8".toBigDecimal()
			}
		}

		rule {
			enabled = false
			element = "CLASS"
			includes = listOf("com.example.spring_api.*")

			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "0.3".toBigDecimal()
			}
		}
	}
}

tasks.register<JacocoReport>("applicationCodeCoverageReport") {
	executionData(tasks.run.get())
	sourceSets(sourceSets.main.get())
}

tasks.asciidoctor {
	project.property("snippetsDir")?.let { inputs.dir(it) }
	dependsOn(tasks.test)
}
