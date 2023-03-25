package component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import util.ComponentConstants

@Composable
fun SearchComponent(
    appViewModel: AppViewModel
) {

    Card(
        modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            .width(400.dp)
    ) {
        Column {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Поиск",
                    modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            ) {
                OutlinedTextField(
                    value = appViewModel.searchText,
                    onValueChange = { appViewModel.searchText = it },
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                        .height(60.dp)
                        .width(300.dp)
                        .padding(5.dp)
                )

                OutlinedButton(
                    onClick = appViewModel::searchEml,
                    modifier = Modifier.height(45.dp)
                        .padding(5.dp)
                ) {
                    Text(text = "Найти")
                }
            }

            Row(
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = appViewModel.isFindLink,
                    onCheckedChange = { appViewModel.isFindLink = it }
                )
                Text(
                    text = "Поиск по ссылкам в тексте",
                    modifier = Modifier.clickable { appViewModel.isFindLink = !appViewModel.isFindLink }
                )
            }

            Row(
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = appViewModel.isFindText,
                    onCheckedChange = { appViewModel.isFindText = it }
                )
                Text(
                    text = "Поиск по тексту",
                    modifier = Modifier.clickable { appViewModel.isFindText = !appViewModel.isFindText }
                )
            }
        }
    }
}