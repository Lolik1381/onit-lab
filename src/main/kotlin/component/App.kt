package component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import service.FileService

@Composable
@Preview
fun App(
    fileService: FileService = FileService()
) {
    val coroutineScope = rememberCoroutineScope()

    var homeFolder by remember { mutableStateOf<String?>(null) }
    var countFileInHomeDirectory by remember { mutableStateOf(0) }
    var countEmlInHomeDirectory by remember { mutableStateOf(0) }
    var countFileInOutputDirectory by remember { mutableStateOf(0) }

    var isFindLink by remember { mutableStateOf(false) }
    var isFindText by remember { mutableStateOf(false) }

    var sortTime by remember { mutableStateOf(0L) }

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = homeFolder) {
        this.launch {
            countFileInHomeDirectory = fileService.countFileInHomeDirectory(homeDirectoryPath = homeFolder.orEmpty())
            countEmlInHomeDirectory = fileService.countEmlFileInHomeDirectory(homeDirectoryPath = homeFolder.orEmpty())
            countFileInOutputDirectory = fileService.countFileInOutputDirectory(homeDirectoryPath = homeFolder.orEmpty())
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = homeFolder.orEmpty(),
            onValueChange = { homeFolder = it },
            label = { Text(text = "URL домашнего каталога") },
            isError = isError
        )

        Text(
            text = "В домашнем каталоге найдено $countFileInHomeDirectory файлов",
            modifier = Modifier.padding(2.dp)
        )

        Text(
            text = "$countEmlInHomeDirectory с расширением .eml",
            modifier = Modifier.padding(2.dp)
        )

        Text(
            text = "Файлов отсортировано: $countFileInOutputDirectory",
            modifier = Modifier.padding(2.dp)
        )

        OutlinedButton(
            onClick = {
                if (homeFolder.isNullOrBlank()) {
                    isError = true
                    return@OutlinedButton
                }

                coroutineScope.launch {
                    sortTime = measureTimeMillis {
                        fileService.sortEmlInHomeDirectory(homeFolder.orEmpty())
                    }

                    countFileInOutputDirectory = fileService.countFileInOutputDirectory(homeDirectoryPath = homeFolder.orEmpty())
                }
            }
        ) {
            Text(text = "Отсортировать eml")
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isFindLink,
                    onCheckedChange = { isFindLink = it }
                )
                Text(
                    text = "Поиск по ссылкам в тексте",
                    modifier = Modifier.clickable { isFindLink = !isFindLink }
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isFindText,
                    onCheckedChange = { isFindText = it }
                )
                Text(
                    text = "Поиск по тексту",
                    modifier = Modifier.clickable { isFindText = !isFindText }
                )
            }
        }

        Row {
            OutlinedTextField(
                value = "",
                onValueChange = {  },
                modifier = Modifier.height(20.dp)
            )

            OutlinedButton(
                onClick = {},
                modifier = Modifier.width(105.dp)
                    .height(40.dp)
            ) {
                Text(text = "Поиск")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Время затраченное на сортировку: $sortTime ms")
        }
    }
}