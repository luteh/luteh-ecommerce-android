package com.luteh.ecommerce.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

object DummyGenerator {
    private val loremIpsumWords = listOf(
        "lorem",
        "ipsum",
        "dolor",
        "sit",
        "amet",
        "consectetur",
        "adipiscing",
        "elit",
        "sed",
        "do",
        "eiusmod",
        "tempor",
        "incididunt",
        "ut",
        "labore",
        "et",
        "dolore",
        "magna",
        "aliqua",
        "ut",
        "enim",
        "ad",
        "minim",
        "veniam",
        "quis",
        "nostrud",
        "exercitation",
        "ullamco",
        "laboris",
        "nisi",
        "ut",
        "aliquip",
        "ex",
        "ea",
        "commodo",
        "consequat",
        "duis",
        "aute",
        "irure",
        "dolor",
        "in",
        "reprehenderit",
        "in",
        "voluptate",
        "velit",
        "esse",
        "cillum",
        "dolore",
        "eu",
        "fugiat",
        "nulla",
        "pariatur",
        "excepteur",
        "sint",
        "occaecat",
        "cupidatat",
        "non",
        "proident",
        "sunt",
        "in",
        "culpa",
        "qui",
        "officia",
        "deserunt",
        "mollit",
        "anim",
        "id",
        "est",
        "laborum"
    )

    /**
     * Generates a lorem ipsum text with a specified number of words
     */
    fun generateLoremIpsum(wordsCount: Int): String {
        return (1..wordsCount).joinToString(" ") {
            loremIpsumWords.random()
        }
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + "."
    }

    /**
     * Generates a random date in the format "YYYY-MM-DDThh:mm:ssTZD" (ISO 8601 format).
     *
     * This function generates a random date-time and returns it as a string in UTC format.
     * It handles Android SDK versions both >= 26 (Oreo) and < 26.
     *
     * - For Android SDK versions >= 26, it uses `LocalDateTime` and `YearMonth` from `java.time` API.
     * - For Android SDK versions < 26, it uses the `Calendar` and `SimpleDateFormat` classes.
     *
     * @return A string representing the random date in UTC, formatted as "YYYY-MM-DDThh:mm:ssTZD".
     */
    fun generateRandomDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For SDK >= 26 (Oreo)
            val randomDateTime = generateRandomLocalDateTime()
            formatDateTimeToUTC(randomDateTime)
        } else {
            // For SDK < 26
            generateRandomDateForOldSdk()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateRandomLocalDateTime(): LocalDateTime {
        val randomYear = Random.nextInt(2020, 2025)
        val randomMonth = Random.nextInt(1, 13)

        // Get the correct number of days for the random month/year (handle leap years)
        val yearMonth = YearMonth.of(randomYear, randomMonth)
        val randomDay = Random.nextInt(1, yearMonth.lengthOfMonth() + 1)

        val randomHour = Random.nextInt(0, 24)
        val randomMinute = Random.nextInt(0, 60)
        val randomSecond = Random.nextInt(0, 60)

        return LocalDateTime.of(
            randomYear, randomMonth, randomDay, randomHour, randomMinute, randomSecond
        )
    }

    private fun generateRandomDateForOldSdk(): String {
        val randomYear = Random.nextInt(2020, 2025)
        val randomMonth = Random.nextInt(Calendar.JANUARY, Calendar.DECEMBER + 1)

        // Create a Calendar instance and set the random year and month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, randomYear)
        calendar.set(Calendar.MONTH, randomMonth)

        // Set a random day, ensuring that it is valid for the month
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val randomDay = Random.nextInt(1, maxDay + 1)
        calendar.set(Calendar.DAY_OF_MONTH, randomDay)

        val randomHour = Random.nextInt(0, 24)
        val randomMinute = Random.nextInt(0, 60)
        val randomSecond = Random.nextInt(0, 60)

        calendar.set(Calendar.HOUR_OF_DAY, randomHour)
        calendar.set(Calendar.MINUTE, randomMinute)
        calendar.set(Calendar.SECOND, randomSecond)

        // Format the date in UTC format
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return simpleDateFormat.format(calendar.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateTimeToUTC(dateTime: LocalDateTime): String {
        val utcDateTime = dateTime.atOffset(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        return utcDateTime.format(formatter)
    }
}