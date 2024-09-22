package com.luteh.ecommerce.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    private val possibleInputFormats = listOf(
        "yyyy-MM-dd'T'HH:mm:ssXXX", // ISO 8601 format
        "yyyy-MM-dd'T'HH:mm:ss",    // ISO without time zone
        "yyyy/MM/dd HH:mm:ss",      // Common format
        "dd/MM/yyyy",               // European style
        "MM/dd/yyyy",               // American style
        "EEE MMM dd HH:mm:ss z yyyy", // Common date-time format like in logs
        "yyyy-MM-dd",               // Simple date format
        "dd MMM yyyy"               // Already in output format
    )

    /**
     * Reformats a date string from an unpredictable input format to a given output format.
     *
     * @param dateString The original date string in an unknown format.
     * @param outputFormat The desired format of the output date string (e.g., "dd MMM yyyy").
     * @return The reformatted date string, or an empty string if parsing fails.
     */
    fun reformatDate(dateString: String, outputFormat: String): String {
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

        for (inputFormat in possibleInputFormats) {
            try {
                val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
                val date: Date? = inputDateFormat.parse(dateString)
                if (date != null) {
                    return outputDateFormat.format(date)
                }
            } catch (e: ParseException) {
                // Continue to try the next format if parsing fails
            }
        }

        // Return an empty string if none of the formats match
        return ""
    }
}

/**
 * Extension function to reformat a date string from an unpredictable input format to a given output format.
 *
 * @param outputFormat The desired format of the output date string (e.g., "dd MMM yyyy").
 * @return The reformatted date string, or an empty string if parsing fails.
 */
fun String.reformatDate(outputFormat: String): String {
    return DateUtil.reformatDate(this, outputFormat)
}

