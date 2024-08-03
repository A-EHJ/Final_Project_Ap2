package com.example.rickmorty.data.network.remote.dto


data class CharacterDto(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin_Id: Int = 0,
    val location_Id: Int = 0,
    val image: String = "",
)
