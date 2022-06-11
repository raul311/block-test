package com.raul311.employeedirectory.models

import com.google.gson.annotations.SerializedName


data class EmployeeResponse (

  @SerializedName("employees" ) var employees : ArrayList<Employee> = arrayListOf()

)