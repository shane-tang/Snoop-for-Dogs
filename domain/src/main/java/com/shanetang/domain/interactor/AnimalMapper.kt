package com.shanetang.domain.interactor

import com.shanetang.data.api.Result
import com.shanetang.domain.models.Animal
import com.shanetang.domain.models.AnimalPicture
import com.shanetang.domain.models.SearchResults
import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.Years
import org.joda.time.format.DateTimeFormat
import retrofit2.Response
import java.util.Locale
import kotlin.collections.ArrayList

class AnimalMapper {
    /**
     * GIVEN: a birthday of format MM/dd/yyyy
     * RETURN: current age in years. if under a year old, current age in months. can be null
     * I really don't like how this works and it feels kinda hacky. Maybe there's a better
     * way to go about this using joda?
     */
    private fun birthdayToAge(birthday: String?): String? {
        if (birthday.isNullOrEmpty()) {
            return null
        }
        val now = LocalDate.now()
        val date = LocalDate.parse(birthday, DateTimeFormat.forPattern("MM/dd/yyyy"))
        if (Years.yearsBetween(date, now).years > 0) {
            val years = Years.yearsBetween(date, now).years.toString()
            return "$years year${if (years != "1") "s" else ""} old"
        }
        val months = Months.monthsBetween(date, now).months.toString()
        return "$months month${if (months != "1") "s" else ""} old"
    }

    private fun formatName(name: String) = (
        name.split(" ").joinToString(" ") {
            it.toLowerCase(Locale.getDefault()).capitalize(Locale.getDefault())
        }
    )

    fun responseToResults(response: Response<Result>): SearchResults {
        // HTTP errors
        if (!response.isSuccessful) {
            return SearchResults.Error("${response.code()} ${response.message()}")
        }

        val body = response.body()!!

        // API returning errors
        if (body.status != "ok") {
            return SearchResults.Error(
                body.message ?: "An API error occurred"
            )
        }

        val animals = ArrayList<Animal>()
        body.data?.map {
            animals.add(
                Animal(
                    id = it.value.animalID,
                    name = formatName(it.value.animalName),
                    distance = it.value.animalLocationDistance,
                    age = birthdayToAge(it.value.animalBirthdate),
                    pictures = it.value.animalPictures?.map { pic ->
                        AnimalPicture(
                            urlFullsize = pic.urlSecureFullsize,
                            urlThumbnail = pic.urlSecureThumbnail
                        )
                    }
                )
            )
        }
        if (body.data.isNullOrEmpty()) return SearchResults.Empty
        return SearchResults.Successful(animals, body.foundRows ?: -1)
    }
}

