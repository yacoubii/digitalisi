package com.example.digitalisi.model

data class UserIdResponse (
    val copyright : String,
    val is_guest_user : String,
    val branding_version : String,
    val user_id : String,
    val user_name : String,
    val session_id : String,
    val conf : Any,
    val is_technical_user : Boolean,
    val version : String
    )