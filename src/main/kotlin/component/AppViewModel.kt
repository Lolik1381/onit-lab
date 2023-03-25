package component

import androidx.compose.runtime.mutableStateOf
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import service.EmlParserService
import service.FileService
import service.SortService
import service.impl.EmlParserServiceImpl
import service.impl.FileServiceImpl
import service.impl.SortServiceImpl
import service.search.LinkSearchStrategy
import service.search.SearchStrategy
import service.search.TextSearchStrategy
import util.SEARCH_OUTPUT_DIR
import util.combinePath

class AppViewModel {

    private lateinit var appScope: CoroutineScope

    private val emlParserService: EmlParserService = EmlParserServiceImpl()
    private val fileService: FileService = FileServiceImpl()
    private val sortService: SortService = SortServiceImpl()

    private val textSearchStrategy: SearchStrategy = TextSearchStrategy(fileService, emlParserService)
    private val linkSearchStrategy: SearchStrategy = LinkSearchStrategy(fileService, emlParserService)

    private var _homeFolder = mutableStateOf<String?>(null)
    private var _countFileInHomeDirectory = mutableStateOf(0)
    private var _countEmlInHomeDirectory = mutableStateOf(0)
    private var _countFileInOutputDirectory = mutableStateOf(0)

    private var _isFindLink =  mutableStateOf(false)
    private var _isFindText = mutableStateOf(false)

    private var _sortTime = mutableStateOf(0L)

    private var _isError = mutableStateOf(false)

    private val _searchText = mutableStateOf("")

    var homeFolder: String
        get() = _homeFolder.value.orEmpty()
        set(value) {
            _homeFolder.value = value

            _countFileInHomeDirectory.value = fileService.countFileInHomeDirectory(_homeFolder.value.orEmpty())
            _countEmlInHomeDirectory.value = fileService.countEmlFileInHomeDirectory(_homeFolder.value.orEmpty())
            _countFileInOutputDirectory.value = fileService.countFileInOutputDirectory(_homeFolder.value.orEmpty())
        }

    var isFindLink: Boolean
        get() = _isFindLink.value
        set(value) {
            _isFindLink.value = value
        }

    var isFindText: Boolean
        get() = _isFindText.value
        set(value) {
            _isFindText.value = value
        }

    var searchText: String
        get() = _searchText.value
        set(value) {
            _searchText.value = value
        }

    val countFileInHomeDirectory: Int
        get() = _countFileInHomeDirectory.value

    val countEmlInHomeDirectory: Int
        get() = _countEmlInHomeDirectory.value

    val countFileInOutputDirectory: Int
        get() = _countFileInOutputDirectory.value

    val isError: Boolean
        get() = _isError.value

    val sortTime: Long
        get() = _sortTime.value

    fun init(coroutineScope: CoroutineScope) {
        appScope = coroutineScope
    }

    fun sortEml() {
        if (homeFolder.isBlank()) {
            _isError.value = true
            return
        }

        appScope.launch {
            launch {
                _sortTime.value = measureTimeMillis {
                    sortService.sortEmlFiles(homeFolder)
                }

                _countFileInOutputDirectory.value = fileService.countFileInOutputDirectory(homeFolder)
            }
        }
    }

    fun searchEml() {
        fileService.removeFilesInDirectory(combinePath(directories = arrayOf(homeFolder, SEARCH_OUTPUT_DIR)))

        if (isFindText) {
            textSearchStrategy.search(searchText, homeFolder)
        }

        if (isFindLink) {
            linkSearchStrategy.search(searchText, homeFolder)
        }
    }
}