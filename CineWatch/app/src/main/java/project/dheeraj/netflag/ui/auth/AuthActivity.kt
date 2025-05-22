package project.dheeraj.netflag.ui.auth
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import project.dheeraj.netflag.R
import dagger.hilt.android.AndroidEntryPoint
import project.dheeraj.netflag.ui.main.MainActivity

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cho phép content chạy full‑screen dưới hệ thống bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Khởi tạo controller để ẩn hệ thống bar
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        // Ẩn cả status bar và navigation bar
        controller.hide(
            WindowInsetsCompat.Type.statusBars() or
                WindowInsetsCompat.Type.navigationBars())
        // Cho phép user vuốt vào cạnh màn để tạm hiện lại hệ thống bar
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        setContentView(R.layout.auth_activity)
    }
}