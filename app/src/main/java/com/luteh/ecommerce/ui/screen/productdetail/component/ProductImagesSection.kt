package com.luteh.ecommerce.ui.screen.productdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.luteh.ecommerce.ui.component.ErrorImageView
import com.luteh.ecommerce.ui.component.LoadingView

@Composable
fun ProductImagesSection(imageUrls: List<String>) {
    val pagerState = rememberPagerState(initialPage = 0) { imageUrls.size }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            SubcomposeAsyncImage(
                model = imageUrls[page],
                loading = {
                    LoadingView()
                },
                error = {
                    ErrorImageView(iconSize = 50.dp)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPagerIndicator(
            totalPages = imageUrls.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            activeColor = Color.Blue,
            inactiveColor = Color.Gray
        )
    }
}

@Composable
private fun HorizontalPagerIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier,
    activeColor: Color,
    inactiveColor: Color,
    activeIndicatorSize: Dp = 8.dp,
    inactiveIndicatorSize: Dp = 6.dp,
    spacing: Dp = 2.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until totalPages) {
            Box(modifier = Modifier.padding(horizontal = spacing)) {
                Box(
                    modifier = Modifier
                        .size(if (i == currentPage) activeIndicatorSize else inactiveIndicatorSize)
                        .clip(CircleShape)
                        .background(if (i == currentPage) activeColor else inactiveColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductImagesSectionPreview() {
    ProductImagesSection(
        imageUrls = listOf(
            "https://picsum.photos/200",
            "https://picsum.photos/200"
        )
    )
}
