package com.luteh.ecommerce.ui.screen.productdetail.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteh.ecommerce.R
import com.luteh.ecommerce.domain.model.ProductDetailModel

@Composable
fun ProductDetailFooter(data: ProductDetailModel) {
    val context = LocalContext.current
    OutlinedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = "Total Price", color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$${data.price}",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(onClick = {
                Toast.makeText(context, context.getString(R.string.coming_soon), Toast.LENGTH_SHORT)
                    .show()
            }, contentPadding = PaddingValues(horizontal = 40.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.AddShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Add to Cart")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProductDetailFooterPreview() {
    ProductDetailFooter(ProductDetailModel.dummy)
}