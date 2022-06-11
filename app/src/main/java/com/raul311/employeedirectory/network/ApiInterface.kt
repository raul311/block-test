package com.raul311.employeedirectory.network

import com.raul311.employeedirectory.models.EmployeeResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("/sq-mobile-interview/employees.json")
    suspend fun getEmployeeList(): EmployeeResponse

}