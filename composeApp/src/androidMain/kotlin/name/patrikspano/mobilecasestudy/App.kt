package name.patrikspano.mobilecasestudy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // Material theme for consistent styling
    MaterialTheme {
        // State to toggle the display of text
        var showText by remember { mutableStateOf(false) }

        // Main column layout with content centered horizontally
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button to toggle text visibility
            Button(onClick = { showText = !showText }) {
                Text("Toggle Text")
            }
            // Conditionally display text based on the button state
            if (showText) {
                Text("Hello, Android!")
            }
        }
    }
}