package service.impl

import java.io.File
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage
import service.SortService
import util.SORT_OUTPUT_DIR
import util.combinePath

class SortServiceImpl : SortService {

    override fun sortEmlFiles(sourcePath: String) {
        val sourceDirectory = File(sourcePath)

        if (!sourceDirectory.isDirectory) {
            return
        }

        val session = Session.getDefaultInstance(Properties(), null)

        sourceDirectory.walkTopDown()
            .filter { it.isFile && it.extension == "eml" && !it.path.contains(combinePath(SORT_OUTPUT_DIR)) }
            .forEach {
                try {
                    val mimeMessage = MimeMessage(session, it.inputStream())
                    val contentType = mimeMessage.contentType.split(";")
                        .firstOrNull()
                        ?.replace("/", "-")

                    it.copyTo(File(combinePath(it.name, sourcePath, SORT_OUTPUT_DIR, contentType.orEmpty())))
                    it.delete()
                } catch (e: Exception) {
                    println(e.localizedMessage)
                }
            }
    }
}