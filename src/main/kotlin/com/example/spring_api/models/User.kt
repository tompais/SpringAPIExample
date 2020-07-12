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
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past
import javax.validation.constraints.Positive

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Positive
    @Column(unique = true, nullable = false)
    val id: Long,

    @NotBlank
    @Column(nullable = false)
    val firstName: String,

    @NotBlank
    @Column(nullable = false)
    val lastName: String,

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    val email: String,

    @Past
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

    @Column
    var lastUpdate: Date = Date.from(Instant.now())
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
