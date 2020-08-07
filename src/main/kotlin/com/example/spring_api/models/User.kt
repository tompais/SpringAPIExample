package com.example.spring_api.models

import com.example.spring_api.enums.Genre
import com.example.spring_api.enums.Genre.OTHER
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.validators.annotations.OverEighteen
import com.fasterxml.jackson.annotation.JsonValue
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.PositiveOrZero

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @field:PositiveOrZero
    @Column(unique = true, nullable = false, updatable = false)
    val id: Long,

    @field:NotBlank
    @Column(nullable = false, length = 20)
    val firstName: String,

    @field:NotBlank
    @Column(nullable = false, length = 20)
    val lastName: String,

    @field:NotBlank
    @field:Email
    @Column(unique = true, nullable = false, length = 30)
    val email: String,

    @Column(nullable = false)
    @Basic
    @field:Past
    @field:OverEighteen
    val birthday: LocalDate,

    @Enumerated(STRING)
    @Column(nullable = false)
    val genre: Genre = OTHER,

    @Enumerated(STRING)
    @Column(nullable = false)
    var status: Status = ACTIVE,

    @Column(nullable = false)
    @Basic
    @UpdateTimestamp
    @field:PastOrPresent
    private val lastUpdate: LocalDateTime = LocalDateTime.now()
) {
    enum class Status {
        ACTIVE,
        INACTIVE;

        @JsonValue
        override fun toString(): String {
            return name.toLowerCase()
        }
    }
}
