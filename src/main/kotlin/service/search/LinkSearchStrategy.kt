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

class LinkSearchStrategy(
    private val fileService: FileService,
    private val emlParserService: EmlParserService
) : SearchStrategy {

    override fun search(text: String, homeDirectory: String) {
        val directoryFiles = fileService.openEmlDirectory(homeDirectory, listOf("eml"))

        val session = Session.getDefaultInstance(Properties(), null)

        directoryFiles.forEach { file ->
            val mimeMessage = MimeMessage(session, file.inputStream())

            val htmlText = when (val content = mimeMessage.content) {
                is MimeMultipart -> emlParserService.getHtmlFromMimeMultipart(content)
                else -> ""
            }

            val match = """\<a href[ ]*=[ ]*\"(.*?${text}.*?)\"""".toRegex().find(htmlText)

            if (match != null) {
                file.copyTo(File(combinePath(file.name, homeDirectory, SEARCH_OUTPUT_DIR, "linkSearch")))
            }
        }
    }
}