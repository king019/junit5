plugins {
	`java-library`
	kotlin("jvm")
	groovy
}

apply(from = "$rootDir/gradle/testing.gradle.kts")

description = "JUnit Jupiter Engine"

tasks.jar {
	manifest {
		attributes(
			"Automatic-Module-Name" to "org.junit.jupiter.engine"
		)
	}
}

val testJavaVersion by extra(JavaVersion.VERSION_11)

tasks.compileTestGroovy {
	sourceCompatibility = testJavaVersion.majorVersion
	targetCompatibility = testJavaVersion.majorVersion
}

val testArtifacts by configurations.creating {
	extendsFrom(configurations["testRuntime"])
}

val testJar by tasks.creating(Jar::class) {
	archiveClassifier.set("test")
	from(sourceSets.getByName("test").output)
}

artifacts {
	add(testArtifacts.name, testJar)
}

dependencies {
	api("org.apiguardian:apiguardian-api:${Versions.apiGuardian}")

	api(project(":junit-platform-engine"))
	api(project(":junit-jupiter-api"))

	testImplementation(project(":junit-platform-launcher"))
	testImplementation(project(":junit-platform-runner"))
	testImplementation(project(":junit-platform-testkit"))
	testImplementation("org.jetbrains.kotlin:kotlin-stdlib")
	testImplementation(localGroovy())
}
