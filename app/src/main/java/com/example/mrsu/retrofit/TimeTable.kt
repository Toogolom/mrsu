package com.example.example

import com.google.gson.annotations.SerializedName
import java.util.Date


data class TimeTable (

  @SerializedName("Date"    ) var Date    : String?            = null,
  @SerializedName("Lessons" ) var Lessons : ArrayList<Lessons> = arrayListOf()

)