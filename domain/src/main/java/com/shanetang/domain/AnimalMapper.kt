package com.shanetang.domain

import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.Years
import org.joda.time.format.DateTimeFormat
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AnimalMapper {
    private val date = Date()
    private val today = date

    /**
     * GIVEN: a birthday of format MM/dd/yyyy
     * RETURN: current age in years. if under a year old, current age in months. can be null
     * I really don't like how this works and it feels kinda hacky. Maybe there's a better
     * way to go about this using joda?
     */
    fun birthdayToAge(birthday: String?): String? {
        if (birthday.isNullOrEmpty()) {
            return null
        }
        val now = LocalDate.now()
        val date = LocalDate.parse(birthday, DateTimeFormat.forPattern("MM/dd/yyyy"))
        if (Years.yearsBetween(date, now).years > 0) {
            return Years.yearsBetween(date, now).years.toString() + " years old"
        }
        return Months.monthsBetween(date, now).months.toString() + " months old"
    }

    fun responseToState(response: Response<Result>): SearchState {
        // HTTP errors
        if (!response.isSuccessful) {
            return SearchState.Error("${response.code()} ${response.message()}")
        }

        val body = response.body()!!

        // API returning errors
        if (body.status != "ok") {
            return SearchState.Error(body.message ?: "An API error occurred")
        }

        val animals = ArrayList<Animal>()
        body.data?.map {
            animals.add(
                Animal(
                    id = it.value.animalID,
                    name = it.value.animalName,
                    pictures = it.value.animalPictures,
                    distance = it.value.animalLocationDistance,
                    age = birthdayToAge(it.value.animalBirthdate),
                )
            )
        }
        return SearchState.Successful(animals, body.foundRows)
    }
}

