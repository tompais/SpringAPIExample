package com.example.spring_api.models

import com.example.spring_api.enums.Genre
import com.example.spring_api.enums.Genre.OTHER
import com.example.spring_api.models.User.Status.ACTIVE
import java.time.Instant
import java.util.Date
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType.DATE
import javax.persistence.TemporalType.TIMESTAMP
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @field:PositiveOrZero
    @Column(unique = true, nullable = false)
    val id: Long,

    @field:NotBlank
    @Column(nullable = false)
    val firstName: String,

    @field:NotBlank
    @Column(nullable = false)
    val lastName: String,

    @field:NotBlank
    @field:Email
    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    @Basic
    @Temporal(DATE)
    val birthday: Date,

    @Enumerated(STRING)
    @Column(nullable = false)
    val genre: Genre = OTHER,

    @Enumerated(STRING)
    @Column(nullable = false)
    var status: Status = ACTIVE,

    @Column(nullable = false)
    @Basic
    @Temporal(TIMESTAMP)
    private var lastUpdate: Date = Date.from(Instant.now())
) {
    enum class Status {
        ACTIVE,
        INACTIVE
    }

    @PrePersist
    @PreUpdate
    fun setLastUpdateNow() {
        lastUpdate = Date.from(Instant.now())
    }
}
