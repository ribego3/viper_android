package com.viper.example.login.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.viper.example.R
import com.viper.example.login.BaseApplication
import com.viper.example.login.contract.LoginContract
import com.viper.example.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.login_activity.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class LoginActivity : BaseActivity(), LoginContract.View {
    companion object {
        val TAG: String = "LoginActivity"
    }
    override fun getToolbarInstance(): Toolbar? = toolbar

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                    HomeActivity.TAG -> startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }
    private var presenter: LoginContract.Presenter? = null
    private val toolbar: Toolbar by lazy { loginToolBar }
    private val userTextView: TextView by lazy { userText }
    private val passwordTextView: TextView by lazy { passwordText }
    private val loginButton: TextView by lazy { loginBtn }
    private val progressBar: ProgressBar by lazy { loginProgressBar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)
        setSupportActionBar(findViewById(R.id.loginToolBar))

        presenter = LoginPresenter(this)
        userTextView.text = ""
        passwordTextView.text = ""
        loginButton.setOnClickListener {
            presenter?.authenticateClick(userTextView.text.toString(), passwordTextView.text.toString())
        }
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
        presenter?.onViewCreated()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun showLoading() {
        loginButton.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loginButton.isEnabled = true
        progressBar.visibility = View.GONE
    }
}
