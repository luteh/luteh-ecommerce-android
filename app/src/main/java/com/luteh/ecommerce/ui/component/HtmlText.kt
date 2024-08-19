package com.luteh.ecommerce.ui.component

import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            TextView(context).apply {
                text =
                    Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    )
}