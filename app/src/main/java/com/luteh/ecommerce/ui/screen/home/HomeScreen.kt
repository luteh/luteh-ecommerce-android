package com.luteh.ecommerce.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.luteh.ecommerce.domain.model.ProductModel
import com.luteh.ecommerce.ui.component.ErrorImageView
import com.luteh.ecommerce.ui.component.ErrorView
import com.luteh.ecommerce.ui.component.LoadingView
import com.luteh.ecommerce.ui.component.RoundedTextField

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onNavigateToProductDetailScreen: (id: String) -> Unit
) {
    val state = vm.state.collectAsState()

    val productItems: LazyPagingItems<ProductModel> = vm.productPager.collectAsLazyPagingItems()

    val pullRefreshState = rememberSwipeRefreshState(isRefreshing = false)


    Scaffold(containerColor = Color.White) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SwipeRefresh(state = pullRefreshState, onRefresh = { productItems.refresh() }) {
                Column() {
                    Spacer(modifier = Modifier.padding(16.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        valueText = state.value.productFilterModel.productName,
                        onValueChange = { value ->
                            vm.processEvent(HomeViewModel.Event.OnChangeSearch(value))
                        },
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        placeholderText = "Search Product",
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search"
                            )
                        }
                    )
                    when (productItems.loadState.refresh) {
                        is LoadState.Loading -> {
                            LoadingView(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(250.dp)
                            )
                        }

                        is LoadState.Error -> {
                            val failure = (productItems.loadState.refresh as LoadState.Error).error
                            ErrorView(
                                message = failure.message.toString(),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                productItems.retry()
                            }
                        }

                        is LoadState.NotLoading -> {
                            LazyColumn(contentPadding = PaddingValues(12.dp)) {
                                items(productItems.itemCount) { index ->
                                    productItems[index]?.let { data ->
                                        Card(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth(),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color.White),
                                            onClick = {
                                                onNavigateToProductDetailScreen(data.id)
                                            }
                                        ) {
                                            Row {
                                                SubcomposeAsyncImage(
                                                    model = data.thumbnailImageUrl,
                                                    loading = {
                                                        LoadingView()
                                                    },
                                                    error = {
                                                        ErrorImageView(iconSize = 24.dp)
                                                    },
                                                    contentDescription = "movieItemView",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(MaterialTheme.shapes.large)
                                                )
                                                Column {
                                                    Text(text = data.shopName)
                                                    Text(text = data.name)
                                                    Text(text = "${data.price}")
                                                }
                                            }
                                        }
                                    }
                                }
                                when (productItems.loadState.append) {
                                    is LoadState.Loading -> {
                                        item {
                                            LoadingView(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(vertical = 8.dp)
                                            )
                                        }
                                    }

                                    is LoadState.Error -> {
                                        val failure =
                                            (productItems.loadState.append as LoadState.Error).error
                                        item {
                                            ErrorView(
                                                message = failure.message.toString(),
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                productItems.retry()
                                            }
                                        }
                                    }

                                    is LoadState.NotLoading -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}