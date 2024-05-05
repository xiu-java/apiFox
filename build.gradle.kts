
plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.17.3"
  id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}
dependencies {
  implementation("org.openjfx:javafx-controls:17")
  implementation("org.openjfx:javafx-swing:17")
  implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
  implementation("com.google.code.gson:gson:2.8.9")
  implementation("commons-beanutils:commons-beanutils:1.9.4")
}
javafx {
  version = "22"
  modules("javafx.controls")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2023.2.5")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}


tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }

  patchPluginXml {
    sinceBuild.set("232")
    untilBuild.set("242.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
