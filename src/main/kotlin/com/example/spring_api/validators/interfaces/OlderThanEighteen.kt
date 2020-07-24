package com.example.spring_api.validators.interfaces

import com.example.spring_api.validators.classes.OlderThanEighteenValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [OlderThanEighteenValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OlderThanEighteen(
    val message: String = "Should be 18 years or older",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
