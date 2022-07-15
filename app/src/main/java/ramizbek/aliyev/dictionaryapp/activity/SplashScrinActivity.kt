package ramizbek.aliyev.dictionaryapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ramizbek.aliyev.dictionaryapp.MainActivity
import ramizbek.aliyev.dictionaryapp.databinding.ActivitySplashScrinBinding


class SplashScrinActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScrinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScrinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myDictionaryTv.alpha = 0f
        binding.myDictionaryTv.animate().setDuration(5000).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}