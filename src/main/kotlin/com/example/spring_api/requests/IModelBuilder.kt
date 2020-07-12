package com.example.spring_api.requests

interface IModelBuilder<out T> {
    fun buildModel(): T
}
