package com.project.firebasedemo.model

class Information (private var mEmail: String, private var mName: String) {

    fun setEmail (email: String) {
        mEmail = email
    }

    fun getEmail () = mEmail

    fun setName (name: String) {
        mName = name
    }

    fun getName () = mName
}