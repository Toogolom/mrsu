package com.example.example

import com.google.gson.annotations.SerializedName


data class Teacher (

  @SerializedName("Id"       ) var Id       : String? = null,
  @SerializedName("UserName" ) var UserName : String? = null,
  @SerializedName("FIO"      ) var FIO      : String? = null,
  @SerializedName("Photo"    ) var Photo    : Photo?  = Photo()

)