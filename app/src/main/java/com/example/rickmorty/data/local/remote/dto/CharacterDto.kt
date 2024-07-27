package com.example.rickmorty.data.local.remote.dto

data class CharacterDto(
	val id: Int = 0,
	val name: String = "",
	val status: String = "",
	val species: String = "",
	val type: String = "",
	val gender: String = "",
	val origin: Origin = Origin("", ""),
	val location: LocationNameUrl = LocationNameUrl("",""),
	val image: String = "",
	val episode: List<String> = emptyList(),
	val url: String = "",
	val created: String = ""
)

data class Origin(
	val name: String,
	val url: String
)

data class LocationNameUrl(
	val name: String,
	val url: String
)
