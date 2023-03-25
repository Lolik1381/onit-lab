package service.search

import java.io.File
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import service.EmlParserService
import service.FileService
import util.SEARCH_OUTPUT_DIR
import util.combinePath

class TextSearchStrategy(
    private val fileService: FileService,
    private val emlParserService: EmlParserService
) : SearchStrategy {

    override fun search(text: String, homeDirectory: String, isRegexSearch: Boolean) {
        val directoryFiles = fileService.openEmlDirectory(homeDirectory, listOf("eml"))

        val session = Session.getDefaultInstance(Properties(), null)

        directoryFiles.forEach { file ->
            val mimeMessage = MimeMessage(session, file.inputStream())

            val emailText = when (val content = mimeMessage.content) {
                is MimeMultipart -> emlParserService.getTextFromMimeMultipart(content)
                is String -> content
                else -> ""
            }

            if ((isRegexSearch && text.toRegex().find(emailText)?.value != null) || (!isRegexSearch && emailText.contains(text, ignoreCase = true))) {
                file.copyTo(File(combinePath(file.name, homeDirectory, SEARCH_OUTPUT_DIR, "textSearch")), overwrite = true)
            }
        }
    }
}