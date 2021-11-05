package ru.laptseu.libararyapp.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TextTrimmingUtility {

    private final int descriptionLength;

    public TextTrimmingUtility(@Value("${app.pages.description-length}") int descriptionLength) {
        this.descriptionLength = descriptionLength;
    }

    public String trimToSize(String source) {
        if (source.length() > descriptionLength) {//case: too long description
            for (int i = descriptionLength - 3; i > 0; i--) {
                if (Character.isWhitespace(source.charAt(i))) {
                    return source.substring(0, i) + "...";
                }
            }
        } else {
            return source;//case short enough description
        }
        return source.substring(0, descriptionLength - 3) + "...";//case too long and has no spaces in range
    }
}
