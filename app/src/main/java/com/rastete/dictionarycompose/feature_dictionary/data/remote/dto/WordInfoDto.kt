package com.rastete.dictionarycompose.feature_dictionary.data.remote.dto

import com.rastete.dictionarycompose.feature_dictionary.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val word: String
) {
    fun toWordInfoEntity() = WordInfoEntity(
        meanings = meanings.map { it.toMeaning() },
        phonetic = phonetic,
        word = word
    )
}