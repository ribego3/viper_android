package com.viper.example.login.interactor


import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.FuelJson
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.viper.example.login.contract.LoginContract

class LoginInteractor : LoginContract.Interactor{
    companion object {
        val urlRest = "https://5fea5e498ede8b0017ff19ce.mockapi.io/api/v1/authenticate"
    }

    override fun authenticate(user: String, password: String, interactorOutput: (result: Result<FuelJson, FuelError>) -> Unit) {
        urlRest.httpPost().responseJson { _, _, result ->
            interactorOutput(result)
        }
    }
}