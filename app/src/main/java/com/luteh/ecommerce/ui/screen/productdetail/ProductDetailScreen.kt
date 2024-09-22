package com.luteh.ecommerce.ui.screen.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.ui.component.ErrorView
import com.luteh.ecommerce.ui.component.LoadingView
import com.luteh.ecommerce.ui.screen.productdetail.component.ProductDetailFooter
import com.luteh.ecommerce.ui.screen.productdetail.component.ProductDetailsSection
import com.luteh.ecommerce.ui.screen.productdetail.component.ProductImagesSection
import com.luteh.ecommerce.ui.screen.productdetail.component.ProductReviewSection
import com.luteh.ecommerce.ui.screen.productdetail.component.SellerSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    vm: ProductDetailViewModel = hiltViewModel(),
    id: String,
    onPopBackStack: () -> Unit
) {
    val state = vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.getProductById(id)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Product Detail") }, navigationIcon =
            {
                IconButton(onClick = onPopBackStack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Back",
                        tint = Color.Black,
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
    }, containerColor = Color.White) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            state.value.getProductResult.let { result ->
                when (result) {
                    is ResultState.Error -> ErrorView(message = result.toString()) {
                        vm.getProductById(id)
                    }

                    is ResultState.Success -> Column {
                        Column(
                            modifier = Modifier
                                .verticalScroll(
                                    rememberScrollState()
                                )
                                .weight(1f)
                        ) {
                            val data = result.data
                            ProductImagesSection(imageUrls = data.imageUrls)
                            Spacer(Modifier.padding(8.dp))
                            SellerSection(data = data)
                            Spacer(Modifier.padding(8.dp))
                            ProductDetailsSection(data = data)
                            Spacer(Modifier.padding(8.dp))
                            ProductReviewSection(reviews = data.reviews)
                            Spacer(Modifier.padding(8.dp))
                        }
                        ProductDetailFooter(data = result.data)
                    }

                    else -> LoadingView()
                }
            }
        }

    }
}