package service.impl

import java.io.File
import service.FileService
import util.SEARCH_OUTPUT_DIR
import util.SORT_OUTPUT_DIR
import util.combinePath

class FileServiceImpl : FileService {

    override fun countFileInHomeDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown()
                .filter { it.isFile && !it.path.contains(SEARCH_OUTPUT_DIR) }
                .count()
        }

        return 0
    }

    override fun countEmlFileInHomeDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown()
                .filter {
                    it.isFile
                            && it.extension == "eml"
                            && !it.path.contains(SORT_OUTPUT_DIR)
                            && !it.path.contains(SEARCH_OUTPUT_DIR)
                }
                .count()
        }

        return 0
    }

    override fun countFileInOutputDirectory(homeDirectoryPath: String): Int {
        val trimmedHomeDirectoryPath = homeDirectoryPath.trim()
        val file = File(trimmedHomeDirectoryPath)

        if (file.isDirectory) {
            return file.walkTopDown()
                .filter {
                    it.isFile
                            && it.extension == "eml"
                            && it.path.contains(SORT_OUTPUT_DIR)
                }
                .count()
        }

        return 0
    }

    override fun openEmlDirectory(
        fileDirectoryPath: String,
        extensionFiles: List<String>
    ): Sequence<File> {
        val fileDirectory = File(fileDirectoryPath.trim())

        if (!fileDirectory.isDirectory) {
            return emptySequence()
        }

        return fileDirectory.walkTopDown()
            .filter {
                it.path.contains(combinePath(SORT_OUTPUT_DIR), ignoreCase = true)
                        && it.isFile
                        && (extensionFiles.isEmpty() || it.extension in extensionFiles)
            }
    }

    override fun removeFilesInDirectory(path: String) {
        val directory = File(path)

        if (!directory.isDirectory) {
            return
        }

        directory.deleteRecursively()
    }
}