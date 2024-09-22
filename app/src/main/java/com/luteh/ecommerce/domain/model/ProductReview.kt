package com.luteh.ecommerce.domain.model

import com.luteh.ecommerce.common.DummyGenerator
import java.text.DecimalFormat
import kotlin.random.Random

data class ProductReview(
    val id: String,
    val userProfileUrl: String,
    val name: String,
    val comment: String,
    val rating: Double,
    val createdAt: String,
    val reviewImageUrls: List<String>,
) {
    val formattedRating: Double
        get() = DecimalFormat("#.#").format(rating).toDouble()

    companion object {
        val dummy
            get() = ProductReview(
                id = "1",
                userProfileUrl = "https://placehold.co/50.jpg",
                name = DummyGenerator.generateLoremIpsum(2),
                comment = DummyGenerator.generateLoremIpsum(Random.nextInt(10, 30)),
                rating = Random.nextDouble(1.0, 5.0),
                createdAt = DummyGenerator.generateRandomDate(),
                reviewImageUrls = List(Random.nextInt(1, 10)) {
                    "https://placehold.co/100.jpg"
                }
            )
    }
}