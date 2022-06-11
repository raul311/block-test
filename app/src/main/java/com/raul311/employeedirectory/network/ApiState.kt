package com.raul311.employeedirectory.network

import com.raul311.employeedirectory.models.EmployeeResponse

sealed class ApiState {

    object Loading : ApiState()
    class Failure(val e: Throwable) : ApiState()
    class Success(val data: EmployeeResponse) : ApiState()
    object Empty : ApiState()

}