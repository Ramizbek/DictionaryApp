package ramizbek.aliyev.dictionaryapp.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_asosiy_kategoriya_lugat.*
import ramizbek.aliyev.dictionaryapp.R

class AsosiyKategoriyaLugat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asosiy_kategoriya_lugat)

        
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_fragment_home_ichki) as NavHostFragment

        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_ichki)
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navController
        )



    }
}