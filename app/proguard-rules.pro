# Keep coroutines and Flow
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Keep Room components
-keep class * extends androidx.room.RoomDatabase {
    public static <methods>;
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding {
    public static ** bind(android.view.View);
    public static ** inflate(android.view.LayoutInflater);
}

# Keep Glide annotations and generated classes
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Keep Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep GSON
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep navigation safe args
-keep class androidx.navigation.fragment.NavHostFragment
-keep class * extends androidx.navigation.NavDirections
-keep class * implements androidx.navigation.NavDirections

# Keep Material Components
-keep class com.google.android.material.** { *; }
-keep interface com.google.android.material.** { *; }

# Keep Lifecycle components
-keep class * implements androidx.lifecycle.LifecycleObserver
-keep class * extends androidx.lifecycle.ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Keep WorkManager
-keep class androidx.work.** { *; }
-keep interface androidx.work.** { *; }
-keepclassmembers class * extends androidx.work.Worker {
    public <init>(...);
}

# Keep Lottie animations
-keep class com.airbnb.lottie.** { *; }

# Keep Timber logs in debug
-assumenosideeffects class timber.log.Timber {
    public static void d(...);
    public static void i(...);
    public static void w(...);
    public static void e(...);
    public static void v(...);
}
