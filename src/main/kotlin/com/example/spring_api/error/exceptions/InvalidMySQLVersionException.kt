package com.example.spring_api.error.exceptions

class InvalidMySQLVersionException(mysqlVersion: Int) : InternalServerErrorException("The MySQL version $mysqlVersion is invalid")
