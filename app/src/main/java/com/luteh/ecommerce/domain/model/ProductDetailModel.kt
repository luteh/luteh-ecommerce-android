package com.luteh.ecommerce.domain.model

data class ProductDetailModel(
    val id: String,
    val thumbnailImageUrl: String,
    val name: String,
    val price: Double,
    val shopName: String,
    val rating: Double,
    val ratingCount: Int,
    val description: String,
    val imageUrls: List<String>,
    val reviews: List<ProductReview>
) {
    companion object {
        val dummy
            get() = ProductDetailModel(
                id = "habeo",
                thumbnailImageUrl = "https://search.yahoo.com/search?p=melius",
                name = "Verna Murray",
                price = 16.17,
                shopName = "Bobbie Davis",
                rating = 18.19,
                ratingCount = 5025,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                imageUrls = listOf(),
                reviews = listOf()
            )
    }
}