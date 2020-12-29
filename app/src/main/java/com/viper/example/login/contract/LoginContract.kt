package com.viper.example.login.contract

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.json.FuelJson
import com.github.kittinunf.result.Result
import com.viper.example.login.entity.User

interface LoginContract{
    interface View {
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun authenticateClick(user: String, password: String)
        fun onViewCreated()
        fun onDestroy()
    }

    interface Interactor {
        fun authenticate(user: String, password: String, interactorOutput: (result: Result<FuelJson, FuelError>) -> Unit)
    }

    interface InteractorOutput {
        fun onAuthenticated(user: User)
        fun onError()
    }
}