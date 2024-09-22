package com.luteh.ecommerce.ui.screen.productdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.luteh.ecommerce.R
import com.luteh.ecommerce.common.reformatDate
import com.luteh.ecommerce.domain.model.ProductReview
import com.luteh.ecommerce.ui.component.ErrorImageView
import com.luteh.ecommerce.ui.component.LoadingView
import com.luteh.ecommerce.ui.theme.Orange

@Composable
internal fun ProductReviewSection(reviews: List<ProductReview>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = stringResource(R.string.reviews), fontWeight = FontWeight.Medium)
        Spacer(Modifier.padding(4.dp))
        reviews.forEachIndexed { index, review ->
            ProductReviewItem(review = review)
            // Add divider between items, but not after the last item
            if (index < reviews.size - 1) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
private fun ProductReviewItem(review: ProductReview) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = review.userProfileUrl,
                loading = {
                    LoadingView()
                },
                error = {
                    ErrorImageView(iconSize = 50.dp)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = review.name,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = review.createdAt.reformatDate(outputFormat = "dd MMM yyyy"),
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = review.comment, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            StarRating(rating = review.rating)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = review.formattedRating.toString(), color = Color.Gray)
        }
    }
}

@Composable
private fun StarRating(rating: Double, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            when {
                i <= rating -> {
                    // Full star
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Full Star",
                        tint = Orange
                    )
                }

                i - 0.5 <= rating -> {
                    // Half star
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.StarHalf,
                        contentDescription = "Half Star",
                        tint = Orange
                    )
                }

                else -> {
                    // Empty star
                    Icon(
                        imageVector = Icons.Rounded.StarBorder,
                        contentDescription = "Empty Star",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ProductReviewSectionPreview() {
    ProductReviewSection(
        reviews = List(3) { ProductReview.dummy })
}
