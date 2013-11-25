package org.openmrs.module.reporting.template;

import com.github.jknack.handlebars.Options;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.messagesource.MessageSourceService;

/**
 * Any method in this class that returns a String (or CharSequence) will be exposed as a helper for handlebars templates
 */
public class HandlebarsHelpers {

    private MessageSourceService mss;

    private ConceptService conceptService;

    public HandlebarsHelpers(MessageSourceService mss, ConceptService conceptService) {
        this.mss = mss;
        this.conceptService = conceptService;
    }

    /**
     * This will be exposed as a handlebars helper.
     * Usage is like: {{ message location.uuid prefix="ui.i18n.Location.name." }}
     * @param key
     * @param options
     * @return
     */
    public String message(String key, Options options) {
        String fullKey = options.hash("prefix", "") + key + options.hash("suffix", "");
        String localized = mss.getMessage(fullKey);
        return localized;
    }

    /**
     * This will be exposed as a handlebars helper.
     * Usage is like: {{ conceptName "PIH:Lab only" }}
     * @param sourceAndCode
     * @return
     */
    public String conceptName(String sourceAndCode) {
        try {
            String[] split = sourceAndCode.split(":");
            Concept concept = conceptService.getConceptByMapping(split[1], split[0]);
            return concept.getName().getName();
        } catch (Exception ex) {
            return sourceAndCode;
        }
    }

}
