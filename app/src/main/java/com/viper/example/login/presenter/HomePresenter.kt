package com.viper.example.login.presenter

import com.viper.example.login.contract.HomeContract
import com.viper.example.login.contract.LoginContract
import com.viper.example.login.entity.User

public class HomePresenter(private var view: HomeContract.View?) : HomeContract.Presenter {
    override fun onViewCreated(user: User) {
        view?.showWelcome(user)
    }

    override fun onDestroy() {
        view = null
    }
}