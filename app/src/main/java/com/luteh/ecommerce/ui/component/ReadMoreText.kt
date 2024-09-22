package com.luteh.ecommerce.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun ReadMoreText(
    text: String,
    collapsedMaxLines: Int = 3,  // Number of lines when collapsed
    fontSize: TextUnit = TextUnit.Unspecified,
    contentTextColor: Color = Color.Unspecified
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 300)) // Add animation
    ) {
        // Main Text content
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            fontSize = fontSize,
            color = contentTextColor
        )

        // "Read More" / "Read Less" toggle
        Text(
            text = if (isExpanded) "Read Less" else "Read More",
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { isExpanded = !isExpanded },
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ReadMoreTextPreview() {
    val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."

    ReadMoreText(text = longText)
}