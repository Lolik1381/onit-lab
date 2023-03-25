package service.impl

import javax.mail.internet.MimeMultipart
import service.EmlParserService


class EmlParserServiceImpl : EmlParserService {

    companion object {
        private const val TEXT_PLAIN = "text/plain"
        private const val TEXT_HTML = "text/html"
    }

    override fun getTextFromMimeMultipart(mimeMultipart: MimeMultipart): String {
        var result = ""

        (0 until mimeMultipart.count).forEach { index ->
            val bodyPart = mimeMultipart.getBodyPart(index)

            if (bodyPart.isMimeType(TEXT_PLAIN)) {
                return result + bodyPart.content
            }

            if (bodyPart.content is MimeMultipart) {
                result += getTextFromMimeMultipart(bodyPart.content as MimeMultipart)
            }
        }

        return result
    }

    override fun getHtmlFromMimeMultipart(mimeMultipart: MimeMultipart): String {
        var result = ""

        (0 until mimeMultipart.count).forEach { index ->
            val bodyPart = mimeMultipart.getBodyPart(index)

            if (bodyPart.isMimeType(TEXT_HTML)) {
                return result + bodyPart.content
            }

            if (bodyPart.content is MimeMultipart) {
                result += getHtmlFromMimeMultipart(bodyPart.content as MimeMultipart)
            }
        }

        return result
    }
}