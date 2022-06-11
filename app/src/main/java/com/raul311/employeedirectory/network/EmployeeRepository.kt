package com.raul311.employeedirectory.network

import com.raul311.employeedirectory.models.EmployeeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class EmployeeRepository {

    fun getEmployeeList(): Flow<EmployeeResponse> = flow {
        val r = RetrofitClient.retrofit.getEmployeeList()
        emit(r)
    }.flowOn(Dispatchers.IO)

}