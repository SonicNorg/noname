package my.wiki.crawler;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DateExtractor {
    private final Pattern regexLastChange = Pattern.compile("Эта страница в последний раз была отредактирована (\\d{1,2}\\s+[а-я]+\\s+\\d{4})");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));

    public LocalDate extract(String content) {
        Matcher matcher = regexLastChange.matcher(content);
        if (matcher.find()) {
            return matcher.group(1) != null ? LocalDate.parse(matcher.group(1), dateTimeFormatter) : null;
        } else {
            return LocalDate.now().plusMonths(1);
        }
    }
}
