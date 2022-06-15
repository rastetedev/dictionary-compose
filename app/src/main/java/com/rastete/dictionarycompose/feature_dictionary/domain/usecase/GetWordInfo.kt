package com.rastete.dictionarycompose.feature_dictionary.domain.usecase

import com.rastete.dictionarycompose.core.util.Resource
import com.rastete.dictionarycompose.feature_dictionary.domain.model.WordInfo
import com.rastete.dictionarycompose.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }
        return repository.getWordInfo(word)
    }
}