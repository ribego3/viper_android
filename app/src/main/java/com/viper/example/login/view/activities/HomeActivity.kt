package com.viper.example.login.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.viper.example.R
import com.viper.example.login.BaseApplication
import com.viper.example.login.contract.HomeContract
import com.viper.example.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.login_activity.*
import com.viper.example.login.entity.User
import com.viper.example.login.presenter.HomePresenter
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.android.synthetic.main.login_activity.*

class HomeActivity : BaseActivity(), HomeContract.View {

    companion object {
        val TAG: String = "HomeActivity"
    }

    private var presenter: HomeContract.Presenter? = null
    private val toolbar: Toolbar by lazy { homeToolBar }
    private val nameTextView: TextView by lazy { nameLabel }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)
        setSupportActionBar(findViewById(R.id.homeToolBar))

        presenter = HomePresenter(this)
        nameTextView.text = ""
    }

    override fun showWelcome(user: User) {
        nameTextView.text= user.name
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }
}