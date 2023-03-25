package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import util.ComponentConstants

@Composable
fun SortComponent(
    appViewModel: AppViewModel
) {

    Card(
        modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            .width(400.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
                .width(IntrinsicSize.Max)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Сортировка",
                    modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
                )
            }

            OutlinedTextField(
                value = appViewModel.homeFolder,
                onValueChange = { appViewModel.homeFolder = it },
                label = { Text(text = "Путь к домашнему каталогу") },
                isError = appViewModel.isError,
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
                    .fillMaxWidth()
                    .height(60.dp)
            )

            Text(
                text = "Файло в домашнем каталоге: ${appViewModel.countFileInHomeDirectory}",
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            )

            Text(
                text = "Файлов почты в домашнем каталоге: ${appViewModel.countEmlInHomeDirectory}",
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            )

            Text(
                text = "Отсортированных файлов: ${appViewModel.countFileInOutputDirectory}",
                modifier = Modifier.padding(ComponentConstants.DEFAULT_PADDING)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(ComponentConstants.DEFAULT_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = appViewModel::sortEml
                ) {
                    Text(text = "Отсортировать eml")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Время затраченное на сортировку: ${appViewModel.sortTime} ms")
            }
        }
    }
}