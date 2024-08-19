package com.luteh.ecommerce.ui.screen.productdetail.component

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.luteh.ecommerce.R
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.ui.component.ErrorImageView
import com.luteh.ecommerce.ui.component.LoadingView

@Composable
internal fun CompanySection(data: ProductDetailModel) {
    val context = LocalContext.current
    Column {
        Text(text = "Company", fontWeight = FontWeight.Medium)
        Spacer(Modifier.padding(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                SubcomposeAsyncImage(
                    model = data.thumbnailImageUrl,
                    loading = {
                        LoadingView()
                    },
                    error = {
                        ErrorImageView(iconSize = 50.dp)
                    },
                    contentDescription = "movieItemView",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.large)
                )
                Spacer(Modifier.padding(4.dp))
                Column {
                    Text(text = data.name, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.padding(2.dp))
                    Text(text = data.shopName)
                    Spacer(Modifier.padding(2.dp))
                    Text(
                        text = stringResource(R.string.go_to_website),
                        modifier = Modifier.clickable {
                            try {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(data.imageUrls.firstOrNull())
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.something_went_wrong),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CompanySectionPreview() {
    CompanySection(data = ProductDetailModel.dummy)
}