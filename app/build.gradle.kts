import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.application")
    id("org.mozilla.rust-android-gradle.rust-android")
    kotlin("android")
}

android {
    val javaVersion = JavaVersion.VERSION_1_8
    compileSdk = 35
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions.jvmTarget = javaVersion.toString()
    namespace = "com.github.shadowsocks.plugin.qtun"
    defaultConfig {
        minSdk = 23
        targetSdk = 35
        versionCode = 1000000
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    splits {
        abi {
            isEnable = true
            isUniversalApk = true
        }
    }
    ndkVersion = "27.2.12479018"
    packagingOptions.jniLibs.useLegacyPackaging = true
}

cargo {
    module = "src/main/rust/qtun"
    libname = "qtun"
    targets = listOf("arm", "arm64", "x86", "x86_64")
    profile = "release"
    extraCargoBuildArguments = listOf("--bin", "qtun-client")
    exec = { spec, toolchain ->
        spec.environment("RUST_ANDROID_GRADLE_CC_LINK_ARG", "-Wl,-z,max-page-size=16384,-soname,lib${libname}.so")
    }
}

val copyRustBinaries by tasks.registering {
    dependsOn("cargoBuild")
    val rustTargets = mapOf(
        "armv7-linux-androideabi" to "armeabi-v7a",
        "aarch64-linux-android" to "arm64-v8a",
        "i686-linux-android" to "x86",
        "x86_64-linux-android" to "x86_64",
    )
    val cargoTargetDir = file("src/main/rust/qtun/target")
    val outDir = layout.buildDirectory.dir("rustJniLibs/android")
    inputs.dir(cargoTargetDir)
    outputs.dir(outDir)
    doLast {
        rustTargets.forEach { (triple, abi) ->
            val src = cargoTargetDir.resolve("$triple/release/qtun-client")
            if (src.exists()) {
                val dst = outDir.get().asFile.resolve("$abi/libqtun.so")
                dst.parentFile.mkdirs()
                src.copyTo(dst, overwrite = true)
            }
        }
    }
}

tasks.whenTaskAdded {
    when (name) {
        "mergeDebugJniLibFolders", "mergeReleaseJniLibFolders" -> {
            dependsOn(copyRustBinaries)
            inputs.dir(layout.buildDirectory.dir("rustJniLibs/android"))
        }
    }
}

tasks.register<Exec>("cargoClean") {
    executable("cargo")
    args("clean")
    workingDir("$projectDir/${cargo.module}")
}
tasks.clean.dependsOn("cargoClean")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("androidx.preference:preference:1.2.1")
    implementation("com.github.shadowsocks:plugin:2.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:core-ktx:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.fragment:fragment-testing:1.8.6")
    debugImplementation("androidx.fragment:fragment-testing-manifest:1.8.6")
}
