package com.viper.example.login.presenter


import android.content.Intent
import android.os.Parcelable
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.viper.example.login.BaseApplication
import com.viper.example.login.contract.LoginContract
import com.viper.example.login.entity.User
import com.viper.example.login.interactor.LoginInteractor
import com.viper.example.login.view.activities.HomeActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

public  class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter, LoginContract.InteractorOutput {

    private var interactor: LoginContract.Interactor? = LoginInteractor()
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }


    override fun authenticateClick(user: String, password: String) {
        view?.showLoading()
        interactor?.authenticate(user, password) { result ->
            when (result) {
                is Result.Failure -> {
                    this.onError()
                }
                is Result.Success -> {
                    val usersJsonObject = result.get().obj()

                    val type = object : TypeToken<User>() {}.type
                    val objUser: User =
                        Gson().fromJson(usersJsonObject.toString(), type)

                    this.onAuthenticated(objUser)
                }
            }
        }
    }

    override fun onViewCreated() {
        view?.hideLoading()
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onAuthenticated(user: User) {
        view?.hideLoading()
        router?.navigateTo(HomeActivity.TAG, user)
    }

    override fun onError() {
        view?.hideLoading()
    }

}