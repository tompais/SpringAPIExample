package com.example.spring_api.validators.annotations

import com.example.spring_api.validators.classes.OverEighteenValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [OverEighteenValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OverEighteen(
    val message: String = "Should be 18 years or older",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
