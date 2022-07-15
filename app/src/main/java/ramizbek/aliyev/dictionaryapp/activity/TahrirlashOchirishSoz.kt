package ramizbek.aliyev.dictionaryapp.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_soz_qoshish_yoki_tahrirlash.*
import kotlinx.android.synthetic.main.activity_tahrirlash_ochirish_soz.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.adapter.CustomAdapter
import ramizbek.aliyev.dictionaryapp.classs.ImageCallBack
import ramizbek.aliyev.dictionaryapp.classs.MyLifecycleObserver
import ramizbek.aliyev.dictionaryapp.db.MyDB
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Lugat
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class TahrirlashOchirishSoz : AppCompatActivity(), ImageCallBack {
    var a: Int? = null
    var text: String? = null
    lateinit var observer: MyLifecycleObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahrirlash_ochirish_soz)


        observer = MyLifecycleObserver(activityResultRegistry, this, this)
        lifecycle.addObserver(observer)




        orqaga_soz_qoshish_tahrirlash.setOnClickListener {
            finish()
        }

        val serializable = intent.getIntExtra("lugat", 0)
        val myDBLugat = MyDBLugat.getInstance(this)
        val lugat = myDBLugat.categoryDaoLugat().getLugat()
        var LUGAT = Lugat(null, null, null, null, null, null, null)
        for (lugat in lugat) {
            if (lugat.id == serializable) {
                LUGAT = lugat
            }

        }


        val file = File("${LUGAT.rasim.toString()}")
        val uri = Uri.fromFile(file)
        rasim_qoyish_tahrirlash.setImageURI(uri)
        val findViewById = findViewById<EditText>(R.id.soz_tahrirlash)
        val findViewById1 = findViewById<EditText>(R.id.tarjimasi_tahrirlash)
        findViewById.setText(LUGAT.soz)
        findViewById1.setText(LUGAT.tarjima)

        val myDB = MyDB.getInstance(this)
        val category = myDB.categoryDao().getCategory() as ArrayList
        val customAdapter = CustomAdapter(this, category)

        spinner_tahrirlash.adapter = customAdapter


        val spinners = spinner_tahrirlash

        spinners.onItemSelectedListener =
            object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    a = position

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        val category1 = myDB.categoryDao().getCategory()




        rasim_qoyish_tahrirlash.setOnClickListener {
            val popupMenu =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    PopupMenu(
                        this,
                        it,
                        Gravity.NO_GRAVITY,
                        R.style.popupBGStyle,
                        R.style.popupBGStyle1
                    )
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP_MR1")
                }

            popupMenu.inflate(R.menu.my_menu_rasimi_qosish)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            }

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.kameradan -> {

                        cameraPermission()

                    }
                    R.id.galereyadan -> {


                        startActivityForResult(
                            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "image/*"
                            }, 1
                        )


                    }
                }
                true
            }



            popupMenu.show()

        }

        bekorQilishSozKoshishni_tahrirlash.setOnClickListener {
            finish()
        }

        saqlashSozQoshish_tahrirlash.setOnClickListener {
            val text1 = soz_tahrirlash.text
            val text2 = tarjimasi_tahrirlash.text
            text
            var joyi = ""

            val filesDir = filesDir
            if (filesDir.isDirectory) {
                val listFiles = filesDir.listFiles()
                for (listFile1 in listFiles) {

                    joyi = listFile1.toString()
                }
            }
            for (categoriya in category1) {
                if ((categoriya.id) == (a?.plus(1)))
                    text = categoriya.kategoriyaNomi
            }



            if (text!!.isEmpty())
                text = category[0].kategoriyaNomi


            LUGAT.soz = text1.toString()
            LUGAT.tarjima = text2.toString()

            var kichikMalumot = ""
            if (text2.length >= 60) {
                kichikMalumot = text2.substring(0, 50)
            } else {
                kichikMalumot = text2.toString()
            }

            LUGAT.kichikMalumot = kichikMalumot


            val file = File(filesDir, "${LUGAT.rasim.toString()}")
            for (i in filesDir.listFiles().indices) {
                if (filesDir.listFiles()[i] == file) {
                    filesDir.listFiles()[i].delete()
                    break
                }
            }

            LUGAT.rasim = joyi

            LUGAT.tanlanganKategoriya = text






            if (text1.isEmpty() || text2.isEmpty() || text!!.isEmpty() || joyi.isEmpty()) {
                Toast.makeText(this, "The information is incomplete", Toast.LENGTH_SHORT).show()
            }
            if (text1.isNotEmpty() || text2.isNotEmpty() || text!!.isNotEmpty() || joyi.isNotEmpty()) {

                Toast.makeText(this, "The information is edited", Toast.LENGTH_SHORT).show()

                myDBLugat.categoryDaoLugat().updateLugat(LUGAT)

                finish()
            }


        }


    }

    private fun cameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {

            requestPermissions()

        } else {

            observer.selectImage()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
    }


    override fun imageSelected(photoPath: String?) {
        rasim_qoyish.setImageURI(photoPath!!.toUri())
/*

        val myDB = MyDB.getInstance(this)
        val category = myDB.categoryDao().getCategory() as ArrayList
        val customAdapter = CustomAdapter(this, category)

        val spinners = spinner
        spinner.adapter = customAdapter

        saqlashSozQoshish.setOnClickListener {
            var joyi = ""
            val filesDir = filesDir
            if (filesDir.isDirectory) {
                val listFiles = filesDir.listFiles()
                for (listFile1 in listFiles) {

                    joyi = listFile1.toString()
                }
            }

            spinners.onItemSelectedListener =
                object : AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        a = position

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

            val category1 = myDB.categoryDao().getCategory()

            for (categoriya in category1) {
                if ((categoriya.id?.minus(1)) == a)
                    text = categoriya.kategoriyaNomi
            }



            if (text == null)
                text = category[0].kategoriyaNomi



            if (photoPath.isEmpty() || text!!.isEmpty() || soz.text.isEmpty() || tarjimasi.text.isEmpty())
                Toast.makeText(
                    this,
                    "Malumot To'liq emas",
                    Toast.LENGTH_SHORT
                ).show()
            else {


                var saqla = true

                //  val rasim = joyi
                val text = text
                val soz = soz.text.toString()
                val tarjima = tarjimasi.text.toString()


                val myDBLugat = MyDBLugat.getInstance(this)


                val index = myDBLugat.categoryDaoLugat().getLugat()

                for (index1 in index) {
                    if (index1.soz == soz && index1.tarjima == tarjima)
                        saqla = false
                }

                if (saqla) {

                    var kichikMalumot = ""
                    if (tarjima.length >= 60) {
                        kichikMalumot = tarjima.substring(0, 50)
                    } else {
                        kichikMalumot = tarjima
                    }
                    myDBLugat.categoryDaoLugat()
                        .addLugat(
                            Lugat(
                                index.size + 1,
                                photoPath,
                                text,
                                soz,
                                tarjima,
                                kichikMalumot,
                                "0"
                            )
                        )
                    Toast.makeText(
                        this,
                        "Malumot Saqlandi",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "Bunday Malumot Saqlagansiz",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }


            }
        }


        bekorQilishSozKoshishni.setOnClickListener {
            finish()
        }*/

        val serializable = intent.getIntExtra("lugat", 0)
        val myDBLugat = MyDBLugat.getInstance(this)
        val myDB = MyDB.getInstance(this)
        val lugat = myDBLugat.categoryDaoLugat().getLugat()
        var LUGAT = Lugat(null, null, null, null, null, null, null)
        for (lugat in lugat) {
            if (lugat.id == serializable) {
                LUGAT = lugat
            }

        }
        val category1 = myDB.categoryDao().getCategory()
        val category = myDB.categoryDao().getCategory() as ArrayList



        saqlashSozQoshish_tahrirlash.setOnClickListener {
            val text1 = soz_tahrirlash.text
            val text2 = tarjimasi_tahrirlash.text
            text

            for (categoriya in category1) {
                if ((categoriya.id) == (a?.plus(1)))
                    text = categoriya.kategoriyaNomi
            }



            if (text!!.isEmpty())
                text = category[0].kategoriyaNomi


            LUGAT.soz = text1.toString()
            LUGAT.tarjima = text2.toString()

            var kichikMalumot = ""
            if (text2.length >= 60) {
                kichikMalumot = text2.substring(0, 50)
            } else {
                kichikMalumot = text2.toString()
            }

            LUGAT.kichikMalumot = kichikMalumot


            val file = File(filesDir, "${LUGAT.rasim.toString()}")
            for (i in filesDir.listFiles().indices) {
                if (filesDir.listFiles()[i] == file) {
                    filesDir.listFiles()[i].delete()
                    break
                }
            }


            LUGAT.rasim = photoPath

            LUGAT.tanlanganKategoriya = text


            if (text1.isEmpty() || text2.isEmpty() || text!!.isEmpty() || photoPath!!.isEmpty()) {
                Toast.makeText(this, "The information is incomplete", Toast.LENGTH_SHORT).show()
            }
            if (text1.isNotEmpty() || text2.isNotEmpty() || text!!.isNotEmpty() || photoPath!!.isNotEmpty()) {

                Toast.makeText(this, "The information edited", Toast.LENGTH_SHORT).show()

                myDBLugat.categoryDaoLugat().updateLugat(LUGAT)

                finish()
            }


        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            rasim_qoyish_tahrirlash.setImageURI(uri)

            val inputStream = contentResolver?.openInputStream(uri)
            val localDateTime = LocalDateTime.now()
            val file = File(filesDir, "$localDateTime images.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream?.close()

        }
    }

}