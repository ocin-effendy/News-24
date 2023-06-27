##---------------Begin: proguard configuration for SQLCipher  ----------
-keep class net.sqlcipher.** { *; }
-keep interface net.sqlcipher.** { *; }

##---------------Begin: proguard configuration for Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
}

##---------------Begin: proguard configuration for Retrofit ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepclassmembers class * {
    *** lambda$*;
}

-keepclassmembers class * {
    @kotlin.Metadata <methods>;
}

##---------------Begin: proguard configuration for Dagger Hilt ----------
-keep class dagger.hilt.** { *; }
-keepclassmembers class * {
    @dagger.hilt.* <fields>;
}

##---------------Begin: proguard configuration for Room ----------
-keep class androidx.room.** { *; }
-keepclassmembers class * {
    @androidx.room.* <fields>;
}

##---------------Begin: proguard configuration for Coroutines ----------
-dontwarn kotlinx.coroutines.**