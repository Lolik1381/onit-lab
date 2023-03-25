package service

import javax.mail.internet.MimeMultipart

interface EmlParserService {

    fun getTextFromMimeMultipart(mimeMultipart: MimeMultipart): String
    fun getHtmlFromMimeMultipart(mimeMultipart: MimeMultipart): String
}