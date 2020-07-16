package com.example.spring_api.controllers

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.requests.UserRequest
import com.example.spring_api.services.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@Validated
@RequestMapping("/users", produces = [APPLICATION_JSON_VALUE])
class UserController @Autowired constructor(
    val userService: IUserService
) {
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(CREATED)
    fun create(@RequestBody @Valid userRequest: UserRequest): User = userService.create(userRequest)

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    fun getById(@PathVariable("id") @Positive id: Long): User = userService.findByIdAndStatus(id, ACTIVE)

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deactivateById(@PathVariable("id") @Positive id: Long) = userService.deactivateById(id)

    @PutMapping("/{id}/reactivate")
    @ResponseStatus(ACCEPTED)
    fun reactivate(@PathVariable("id") @Positive id: Long): User = userService.reactivate(id)

    @GetMapping
    @ResponseStatus(OK)
    fun getAllByFilters(
        @RequestParam(
            name = "status",
            defaultValue = "ACTIVE"
        ) status: Status,
        @RequestParam(
            name = "genre",
            required = false
        ) genre: Genre? = null
    ): List<User> = userService.findAllByFilters(status, genre)
}
