package com.luteh.ecommerce.ui.screen.productdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteh.ecommerce.R
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.ui.component.ReadMoreText

@Composable
internal fun ProductDetailsSection(data: ProductDetailModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = stringResource(R.string.product_details), fontWeight = FontWeight.Medium)
        Spacer(Modifier.padding(4.dp))
        ReadMoreText(text = data.description, contentTextColor = Color.Gray)
    }
}

@Composable
@Preview(showBackground = true)
private fun JobSpecificationSectionPreview() {
    ProductDetailsSection(
        data = ProductDetailModel.dummy
    )
}