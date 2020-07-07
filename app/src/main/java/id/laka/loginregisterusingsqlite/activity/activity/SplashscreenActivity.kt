package id.laka.loginregisterusingsqlite.activity.activity


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import id.laka.loginregisterusingsqlite.R
import kotlinx.android.synthetic.main.activity_splashscreen.*


class SplashscreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Fullscreen For SplashScreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //OpacityAnimation The Logo will look after 3seconds
        val animation = AnimationUtils.loadAnimation(this,
            R.anim.opacity_animation
        )
        logo_splash_id.startAnimation(animation)
        name_splash_id.startAnimation(animation)
        slogan_splash_id.startAnimation(animation)


        //Move to LoginActivity after 6seconds
        Handler().postDelayed({
            val intent = Intent(this@SplashscreenActivity, LoginActivity::class.java)

            //Make Transition with more element in Kotlin
            val logoTransiton = Pair.create(logo_splash_id as View, "logo_transition")
            val nameTransiton = Pair.create(name_splash_id as View, "name_transition")
            val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashscreenActivity,
                logoTransiton, nameTransiton)

            //Execute
            ActivityCompat.startActivity(this@SplashscreenActivity, intent, options.toBundle())
            finish()
        }, 6000)
    }


}
