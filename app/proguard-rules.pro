# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Keep annotations (useful for Retrofit & Gson serialization)
-keepattributes *Annotation*

# Keep classes that use Gson for serialization/deserialization
-keep class com.example.kyc.model.** { *; }

# Keep Retrofit API interfaces
-keep interface com.example.kyc.api.** { *; }

# Keep Retrofit-related generated classes
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn com.google.gson.**
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.google.gson.** { *; }

# Keep Jetpack Compose-related classes
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep ViewModel classes (if using them)
-keep class com.example.kyc.viewmodel.** { *; }

# Keep Room Database Entities & DAOs (if using Room)
-keep class com.example.kyc.database.** { *; }

# Prevent obfuscation of Lambda expressions
-keepattributes InnerClasses
-keepattributes EnclosingMethod


