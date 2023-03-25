package util

import java.io.File

fun combinePath(fileName: String = "", vararg directories: String): String {
    var path = directories.joinToString(File.separator).plus(File.separator)

    if (!path.startsWith(File.separator)) {
        path = File.separator + path
    }

    return "${path}${fileName}"
}