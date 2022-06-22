package com.raul311.employeedirectory.models

import com.google.gson.annotations.SerializedName

data class Employee (

  @SerializedName("uuid"            ) var uuid          : String? = null,
  @SerializedName("full_name"       ) var fullName      : String? = null,
  @SerializedName("phone_number"    ) var phoneNumber   : String? = null,
  @SerializedName("email_address"   ) var emailAddress  : String? = null,
  @SerializedName("biography"       ) var biography     : String? = null,
  @SerializedName("photo_url_small" ) var photoUrlSmall : String? = null,
  @SerializedName("photo_url_large" ) var photoUrlLarge : String? = null,
  @SerializedName("team"            ) var team          : String? = null,
  @SerializedName("employee_type"   ) var employeeType  : String? = null

)