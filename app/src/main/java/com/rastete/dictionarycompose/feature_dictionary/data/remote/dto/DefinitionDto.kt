package com.rastete.dictionarycompose.feature_dictionary.data.remote.dto

import com.rastete.dictionarycompose.feature_dictionary.domain.model.Definition

data class DefinitionDto(
    val definition: String,
    val example: String?
) {

    fun toDefinition() = Definition(
        definition = definition,
        example = example,
    )

}