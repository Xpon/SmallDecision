package com.hj

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.6.0"
    const val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:1.0.2"
    const val playPublisherPlugin = "com.github.triplet.gradle:play-publisher:2.7.1"

    const val threeTenBp = "org.threeten:threetenbp:1.4.1"
    const val threeTenBpNoTzdb = "$threeTenBp:no-tzdb"
    const val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:1.2.2"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val ktlint = "com.pinterest:ktlint:0.37.0"

    const val appauth = "net.openid:appauth:0.7.1"

    const val junit = "junit:junit:4.13"
    const val robolectric = "org.robolectric:robolectric:4.3.1"
    const val mockK = "io.mockk:mockk:1.9.3"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.2"

    const val fillableloaders = "com.github.jorgecastilloprz:fillableloaders:1.03@aar"
    const val elasticviews = "com.github.skydoves:elasticviews:2.0.3"
    const val appintro = "com.github.paolorotolo:appintro:4.1.0"

    const val calendarView = "com.github.kizitonwose:CalendarView:0.3.2"

    object Google {
        const val material = "com.google.android.material:material:1.1.0"
        const val firebaseCore = "com.google.firebase:firebase-core:17.2.2"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        const val gmsGoogleServices = "com.google.gms:google-services:4.3.3"
        const val fabricPlugin = "io.fabric.tools:gradle:1.31.2"

        const val openSourceLicensesPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.1"
        const val openSourceLicensesLibrary = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Kotlin {
        private const val version = "1.3.70"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val browser = "androidx.browser:browser:1.0.0"
        const val collection = "androidx.collection:collection-ktx:1.1.0"
        const val ktx = "androidx.core:core-ktx:1.2.0"
        const val palette = "androidx.palette:palette:1.0.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val emoji = "androidx.emoji:emoji:1.0.0"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"

        object Navigation {
            private const val version = "2.2.1"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.2.2"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        object Paging {
            private const val version = "2.1.1"
            const val common = "androidx.paging:paging-common-ktx:$version"
            const val runtime = "androidx.paging:paging-runtime-ktx:$version"
        }

        const val preference = "androidx.preference:preference:1.1.0"

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"

        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.2.4"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Work {
            private const val version = "2.3.2"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }

        object UI {
            const val version = "0.1.0-dev06"

            const val composeRuntime = "androidx.compose:compose-runtime:$version"
            const val framework = "androidx.ui:ui-framework:$version"
            const val layout = "androidx.ui:ui-layout:$version"
            const val material = "androidx.ui:ui-material:$version"
            const val foundation = "androidx.ui:ui-foundation:$version"
            const val animation = "androidx.ui:ui-animation:$version"
            const val tooling = "androidx.ui:ui-tooling:$version"
        }
    }

    object Dagger {
        private const val version = "2.26"
        const val dagger = "com.google.dagger:dagger:$version"
        const val android = "com.google.dagger:dagger-android:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Retrofit {
        private const val version = "2.7.1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitCoroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object OkHttp {
        private const val version = "4.4.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Coil {
        private const val version = "0.9.5"
        const val coil = "io.coil-kt:coil:$version"
    }

    object Facebook {
        object Stetho {
            private const val version = "1.5.1"
            const val stetho = "com.facebook.stetho:stetho:$version"
            const val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:$version"
        }
    }
}
