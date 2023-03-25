package service.search

interface SearchStrategy {

    fun search(text: String, homeDirectory: String)
}