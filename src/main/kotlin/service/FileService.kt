package service

import java.io.File

interface FileService {

    /**
     * Поиск кол-ва файлов в домашнем каталоге
     */
    fun countFileInHomeDirectory(homeDirectoryPath: String): Int

    /**
     * Поиск кол-ва eml файлов в домашнем каталоге
     */
    fun countEmlFileInHomeDirectory(homeDirectoryPath: String): Int

    /**
     * Поиск кол-ва файлов в /output каталоге
     */
    fun countFileInOutputDirectory(homeDirectoryPath: String): Int

    /**
     * Поиск всех файлов в /output каталоге
     */
    fun openEmlDirectory(fileDirectoryPath: String, extensionFiles: List<String> = emptyList()): Sequence<File>

    /**
     * Удалить все файлы в директории
     */
    fun removeFilesInDirectory(path: String)
}