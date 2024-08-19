package com.luteh.ecommerce.ui.screen.productdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.ui.component.HtmlText

@Composable
internal fun JobSpecificationSection(data: ProductDetailModel) {
    Column {
        Text(text = "Job Specification", fontWeight = FontWeight.Medium)
        Spacer(Modifier.padding(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Title", color = Color.Gray)
                Spacer(Modifier.padding(2.dp))
                Text(text = data.name)
                Spacer(Modifier.padding(4.dp))
                Text(text = "Fulltime", color = Color.Gray)
                Spacer(Modifier.padding(2.dp))
                Text(text = data.shopName)
                Spacer(Modifier.padding(4.dp))
                Text(text = "Description", color = Color.Gray)
                Spacer(Modifier.padding(2.dp))
                HtmlText(html = data.description)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun JobSpecificationSectionPreview() {
    JobSpecificationSection(
        data = ProductDetailModel.dummy
    )
}