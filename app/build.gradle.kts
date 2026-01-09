plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.dokka") version "1.9.10"
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.tpl0part2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tpl0part2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    
    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES}"
        }
    }
    
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment.ktx)
    
    // UI Components
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.swiperefreshlayout)
    
    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.common.java8)
    
    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // Room Database (pour le stockage)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    
    // Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    
    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // JSON Parsing
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Preferences
    implementation(libs.androidx.preference.ktx)
    
    // Splash Screen API
    implementation(libs.androidx.core.splashscreen)
    
    // WorkManager
    implementation(libs.androidx.work.ktx)
    
    // Paging Library
    implementation(libs.androidx.paging.runtime.ktx)
    
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    
    // Debugging
    debugImplementation(libs.androidx.fragment.testing)
    
    // Material 3 (DayNight theme support)
    implementation("androidx.compose.material3:material3:1.2.0-alpha12")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha12")
    
    // ViewBinding Property Delegate
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.9")
    
    // Timber for logging
    implementation("com.jakewharton.timber:timber:5.0.1")
    
    // Lottie Animations
    implementation("com.airbnb.android:lottie:6.1.0")
    
    // Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    
    // Swipe actions for RecyclerView
    implementation("com.github.zerobranch:SwipeLayout:1.3.1")
    
    // PhotoView for zoomable images
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    
    // Circular ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
}

// Configuration pour Dokka
tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
    moduleName.set("FilmApp")
    dokkaSourceSets {
        named("main") {
            includes.from("README.md", "CHANGELOG.md")
            samples.from("src/test/kotlin/com/example/tpl0part2/samples")
            
            // Documented packages
            perPackageOption {
                matchingRegex.set("com\\.example\\.tpl0part2\\.Activities.*")
                suppress.set(false)
                reportUndocumented.set(true)
                skipDeprecated.set(false)
            }
            
            perPackageOption {
                matchingRegex.set("com\\.example\\.tpl0part2\\.Adapters.*")
                suppress.set(false)
                reportUndocumented.set(true)
            }
            
            perPackageOption {
                matchingRegex.set("com\\.example\\.tpl0part2\\.Models.*")
                suppress.set(false)
                reportUndocumented.set(true)
            }
        }
    }
}

// Task pour générer la documentation
tasks.register("generateDocumentation") {
    dependsOn("dokkaHtml")
    doLast {
        println("Documentation générée dans: ${buildDir}/dokka")
    }
}

// Configuration pour les tests
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

// Configuration pour éviter les conflits de ressources
configurations.all {
    resolutionStrategy {
        // Force utiliser la même version de Kotlin
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
        
        // Éviter les conflits de versions AndroidX
        force("androidx.annotation:annotation:1.7.0")
        force("androidx.core:core-ktx:1.12.0")
        force("androidx.appcompat:appcompat:1.6.1")
        
        // Préférences pour les dépendances Material
        preferProjectModules()
        
        // Stratégie de résolution des conflits
        eachDependency {
            when (requested.group) {
                "org.jetbrains.kotlin" -> useVersion("1.9.0")
                "androidx.lifecycle" -> useVersion("2.6.2")
                "androidx.navigation" -> useVersion("2.7.6")
                "androidx.room" -> useVersion("2.6.0")
            }
        }
    }
}
