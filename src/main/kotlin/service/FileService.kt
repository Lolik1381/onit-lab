package service

import java.io.File
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.MimeMessage

class FileService {

    companion object {
        private val fileSeparator = File.separator
    }

    fun countFileInHomeDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown().filter { it.isFile }.count()
        }

        return 0
    }

    fun countEmlFileInHomeDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown()
                .filter { it.isFile && it.extension == "eml" && !it.path.contains("$trimmedHomeDirectoryPath${fileSeparator}output") }
                .count()
        }

        return 0
    }

    fun countFileInOutputDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown()
                .filter { it.isFile && it.extension == "eml" && it.path.contains("$trimmedHomeDirectoryPath${fileSeparator}output") }
                .count()
        }

        return 0
    }

    fun sortEmlInHomeDirectory(homeDirectoryPath: String) {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val homeDir = File(trimmedHomeDirectoryPath)

        if (!homeDir.isDirectory) {
            return
        }

        val session = Session.getDefaultInstance(Properties(), null)

        homeDir.walkTopDown()
            .filter { it.isFile && it.extension == "eml" && !it.path.contains("$trimmedHomeDirectoryPath${fileSeparator}output") }
            .forEach {
                try {
                    val mimeMessage = MimeMessage(session, it.inputStream())
                    val contentType = mimeMessage.contentType.split(";")
                        .firstOrNull()?.replace("/", "-")

                    it.copyTo(File("$trimmedHomeDirectoryPath${fileSeparator}output${fileSeparator}$contentType${fileSeparator}${it.name}"))
                    it.delete()
                } catch (e: Exception) {
                    println(e.localizedMessage)
                }
            }
    }
}