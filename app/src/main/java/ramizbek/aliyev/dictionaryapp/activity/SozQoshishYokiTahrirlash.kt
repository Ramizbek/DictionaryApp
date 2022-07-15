package ramizbek.aliyev.dictionaryapp.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_soz_qoshish_yoki_tahrirlash.*
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

class SozQoshishYokiTahrirlash : AppCompatActivity(), ImageCallBack {

    var a: Int? = null
    var text: String? = null
    lateinit var observer: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soz_qoshish_yoki_tahrirlash)



        observer = MyLifecycleObserver(activityResultRegistry, this, this)
        lifecycle.addObserver(observer)


        //back
        orqaga_soz_qoshish.setOnClickListener {
            finish()
        }
        val spinners = spinner


        val myDB = MyDB.getInstance(this)
        val category = myDB.categoryDao().getCategory() as ArrayList
        val customAdapter = CustomAdapter(this, category)

        spinner.adapter = customAdapter

        rasim_qoyish.setOnClickListener {
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

            //Popupmenu details
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



                            if (joyi.isEmpty() || text!!.isEmpty() || soz.text.isEmpty() || tarjimasi.text.isEmpty())
                                Toast.makeText(
                                    this,
                                    "The information is incomplete",
                                    Toast.LENGTH_SHORT
                                ).show()
                            else {


                                var saqla = true

                                val rasim = joyi
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
                                                rasim,
                                                text,
                                                soz,
                                                tarjima,
                                                kichikMalumot,
                                                "0"
                                            )
                                        )
                                    Toast.makeText(
                                        this,
                                        "The information is saved",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "You saved such information",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }


                            }
                        }


                    }
                }
                true
            }

            popupMenu.show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            rasim_qoyish.setImageURI(uri)

            val inputStream = contentResolver?.openInputStream(uri)
            val localDateTime = LocalDateTime.now()
            val file = File(filesDir, "$localDateTime images.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream?.close()

        }
    }

    override fun imageSelected(photoPath: String?) {
        rasim_qoyish.setImageURI(photoPath!!.toUri())

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
                    "The information is incomplete",
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
                        "The information is saved successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "You saved such information",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }


            }
        }


        bekorQilishSozKoshishni.setOnClickListener {
            finish()
        }

    }

    /*fun addLugatCamera(photoPath: String?, id: Int?, rasim: String?, tanlanganKategoriya: String?, soz: String?, tarjima: String?, like: String?){

    }*/


}
