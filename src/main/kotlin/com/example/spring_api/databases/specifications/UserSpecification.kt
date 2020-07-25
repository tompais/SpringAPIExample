package com.example.spring_api.databases.specifications

import com.example.spring_api.models.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object UserSpecification {
    fun isOverTwentyOneYearsOld(): Specification<User> = Specification { root, _, criteriaBuilder ->
        criteriaBuilder.lessThan(root.get<LocalDate>("birthday"), LocalDate.now().minusYears(21))
    }
}
