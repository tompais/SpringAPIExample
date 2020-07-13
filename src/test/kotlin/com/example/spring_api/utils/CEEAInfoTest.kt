package com.example.spring_api.utils

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("test")
@Component("CEEAInfo")
class CEEAInfoTest : H2Info()
