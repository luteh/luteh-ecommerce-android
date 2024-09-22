package com.luteh.ecommerce.ui.screen.productdetail.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteh.ecommerce.R
import com.luteh.ecommerce.domain.model.ProductDetailModel

@Composable
internal fun SellerSection(data: ProductDetailModel) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Text(text = "Seller", fontWeight = FontWeight.Medium)
        Spacer(Modifier.padding(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Store, contentDescription = null, modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.padding(4.dp))
            Column {
                Text(text = data.shopName, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.padding(2.dp))
                Text(
                    text = "Store Location",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = Color.DarkGray
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.coming_soon), Toast.LENGTH_SHORT
                    ).show()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.LightGray.copy(
                        0.5f
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Chat,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CompanySectionPreview() {
    SellerSection(data = ProductDetailModel.dummy)
}