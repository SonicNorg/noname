package my.wiki.crawler;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateExtractorTest {

    @Test
    void testRegex() {
        var content = "<footer id=\"footer\" class=\"mw-footer\" role=\"contentinfo\">\n" +
                "\t<ul id=\"footer-info\">\n" +
                "\t<li id=\"footer-info-lastmod\"> Эта страница в последний раз была отредактирована 3 февраля 2023 в 16:50.</li>\n" +
                "\t<li id=\"footer-info-copyright\">Текст доступен по <a rel=\"license\" href=\"//ru.wikipedia.org/wiki/Википедия:Текст_лицензии_Creative_Commons_Attribution-ShareAlike_3.0_Unported\">лицензии Creative Commons Attribution-ShareAlike</a><a rel=\"license\" href=\"//creativecommons.org/licenses/by-sa/3.0/\" style=\"display:none;\"></a>; в отдельных случаях могут действовать дополнительные условия.\n" +
                "<span class=\"noprint\">Подробнее см. <a href=\"//foundation.wikimedia.org/wiki/Terms_of_Use/ru\">Условия использования</a>.</span><br>\n" +
                "Wikipedia®&nbsp;— зарегистрированный товарный знак некоммерческой организации <a href=\"//wikimediafoundation.org\">Wikimedia Foundation, Inc.</a></li>\n" +
                "</footer>";
        LocalDate localDate = new DateExtractor().extract(content);
        LocalDate expected = LocalDate.of(2023, 2, 3);
        assertEquals(expected, localDate, "Bad date extracted!");
    }
}
