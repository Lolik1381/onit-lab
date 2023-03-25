package component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
@Preview
fun AppComponent(
    appViewModel: AppViewModel = AppViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        appViewModel.init(coroutineScope)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White.copy(alpha = 0.9f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SortComponent(appViewModel)
        SearchComponent(appViewModel)
    }
}