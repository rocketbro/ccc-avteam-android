package com.asherpope.ccc_av_team_android.data

/*
import android.util.Log

// In your Android activity or fragment
class YourActivity : AppCompatActivity() {

    private val TAG = "YourActivity" // Define a tag for your log messages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example usage of different log levels
        Log.d(TAG, "This is a debug message") // For debug logs
        Log.i(TAG, "This is an info message") // For info logs
        Log.w(TAG, "This is a warning message") // For warning logs
        Log.e(TAG, "This is an error message") // For error logs

        // You can also include exception information
        try {
            // Some code that might throw an exception
        } catch (e: Exception) {
            Log.e(TAG, "An error occurred", e)
        }
    }

    // Rest of your activity code
}

Here's a breakdown of how to use Android's logging system:

1. Import: Use 'import android.util.Log' at the top of your Kotlin file.

2. Log Tag: Define a TAG constant for your log messages. This helps identify
   the source of the log in LogCat.

3. Log Methods: Android provides different methods for various log levels:
   - Log.d(): For debug messages
   - Log.i(): For info messages
   - Log.w(): For warning messages
   - Log.e(): For error messages
   - Log.v(): For verbose messages

4. Usage: Each log method takes two parameters: the TAG and the message string.

5. Exception Logging: When catching exceptions, you can include the exception
   object in the log message.

This approach is simple, efficient, and integrates well with Android Studio's LogCat
tool for viewing logs. Remember to manage your logging appropriately, especially in
production builds where you might want to disable or reduce verbose logging.

 */