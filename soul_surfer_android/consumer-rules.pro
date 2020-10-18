-keep class com.soulsurfer.android.** { *; }

-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

-keep public class org.jsoup.** { public *; }

-keeppackagenames org.jsoup.nodes