package com.alvindev.traverseeid.feature_settings.domain.mapper

object LanguageMapper {
    fun mapLanguageToLocale(language: String): String {
        return when (language) {
            "English" -> "en"
            "Bahasa Indonesia" -> "in"
            else -> "en"
        }
    }

    fun mapLocaleToLanguage(locale: String): String {
        return when (locale) {
            "en" -> "English"
            "in" -> "Bahasa Indonesia"
            else -> "English"
        }
    }
}