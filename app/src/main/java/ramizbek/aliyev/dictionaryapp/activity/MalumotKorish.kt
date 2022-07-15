package ramizbek.aliyev.dictionaryapp.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_malumot_korish.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Lugat
import java.io.File

class MalumotKorish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_malumot_korish)

        orqagaMalumotKorish.setOnClickListener {
            finish()
        }


        val stringExtra = intent.getSerializableExtra("lugatId") as Lugat

        soz.text = stringExtra.soz
        sozIchki.text = stringExtra.soz
        tarjimasiMalumotKorish.text = stringExtra.tarjima


        if (stringExtra.like == "1") {
            like.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.group_1))
        } else {
            like.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.group))

        }

        val file = File("${stringExtra.rasim}")
        val uri = Uri.fromFile(file)
        imageView.setImageURI(uri)

        like.setOnClickListener {
            if (stringExtra.like == "1") {
                like.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.group))
                val myDBLugat = MyDBLugat.getInstance(this)
                stringExtra.like = "0"
                myDBLugat.categoryDaoLugat().updateLugat(stringExtra)

            } else {

                val myDBLugat = MyDBLugat.getInstance(this)
                stringExtra.like = "1"
                myDBLugat.categoryDaoLugat().updateLugat(stringExtra)


                like.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.group_1))

            }
        }


    }
}