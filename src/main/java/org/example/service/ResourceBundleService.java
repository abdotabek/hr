package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResourceBundleService {
    private static ResourceBundleMessageSource bundleMessage;

    @Autowired
    ResourceBundleService(ResourceBundleMessageSource messageSource) {
        ResourceBundleService.bundleMessage = messageSource;
    }

    public String getMessage(String msCode, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return bundleMessage.getMessage(msCode, args, locale);
    }


   /* private final MessageSource messageSource;

    public ResourceBundleService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public String getMessage(String code, AppLanguage language) {
        return messageSource.getMessage(code, null, new Locale(language.name()));
    }*/
}
