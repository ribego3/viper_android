package com.viper.example.login.contract

import com.viper.example.login.entity.User

interface HomeContract {

    interface View {
        fun showWelcome(user: User)
    }

    interface Presenter {
        fun onViewCreated(user: User)
        fun onDestroy()
    }
}