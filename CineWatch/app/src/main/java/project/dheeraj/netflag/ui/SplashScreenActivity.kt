package project.dheeraj.netflag.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import project.dheeraj.netflag.R
import project.dheeraj.netflag.ui.auth.AuthActivity
import project.dheeraj.netflag.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        GlobalScope.launch(Dispatchers.IO) {
//            delay(2500)
//            AuthActivity.start(this@SplashScreenActivity)
//            finish()
//        }
        lifecycleScope.launch {
            delay(2500)
            // Chuyển ngay sang AuthActivity (login)
            AuthActivity.start(this@SplashScreenActivity)
            finish()
        }

    }
}