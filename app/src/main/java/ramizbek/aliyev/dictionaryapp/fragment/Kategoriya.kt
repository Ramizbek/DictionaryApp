package ramizbek.aliyev.dictionaryapp.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_kategoriya.view.*
import kotlinx.android.synthetic.main.kategoriya_ochirish.view.*
import kotlinx.android.synthetic.main.kategoriya_qoshish.*
import kotlinx.android.synthetic.main.kategoriya_qoshish.view.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.adapter.RvAdapter
import ramizbek.aliyev.dictionaryapp.db.MyDB
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Categoriya
import java.io.File


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Kategoriya : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    lateinit var myDB: MyDB
    lateinit var rvAdapter: RvAdapter
    lateinit var arrayList: ArrayList<Categoriya>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_kategoriya, container, false)

        root.orqaga.setOnClickListener {

            requireActivity().finish()

        }


        root.qoshish.setOnClickListener {
            val dialog = AlertDialog.Builder(root.context).create()

            val dialogView = layoutInflater.inflate(
                R.layout.kategoriya_qoshish,
                null,
                false
            )
            dialog.setView(dialogView)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setCancelable(true)

            dialog.show()

            dialogView.bekorQilishKategoriyaQoshishni.setOnClickListener {
                dialog.dismiss()
            }

            dialogView.saqlashKategoriyaQoshishni.setOnClickListener {


                val kategoriya = dialogView.findViewById<EditText>(R.id.kategoriy_nomi)

                if (kategoriya.text.isEmpty()) {
                    Toast.makeText(root.context, "Malumot Kiritmadiz", Toast.LENGTH_SHORT)
                        .show()

                    dialog.dismiss()

                } else {

                    val kategoriyaNomi = kategoriya.text.toString()


                    myDB = MyDB.getInstance(root.context)


                    val categorySize = myDB.categoryDao().getCategory()

                    val index = categorySize.size


                    Toast.makeText(
                        root.context,
                        "Malumot Saqlandi:${kategoriya.text}",
                        Toast.LENGTH_SHORT
                    ).show()



                    myDB.categoryDao()
                        .addCategory(Categoriya(index + 1, kategoriyaNomi))

                    val category = myDB.categoryDao().getCategory()



                    rvAdapter = RvAdapter(root.context, category, object : RvAdapter.RvClick {

                        override fun onClick(
                            categoriya: Categoriya,
                            linearLayout: LinearLayout,
                            imageView: ImageView
                        ) {

                        }

                    })

                    root.rv_kategoriya.adapter = rvAdapter



                    dialog.dismiss()
                }

            }

        }

        return root
    }

    override fun onResume() {
        super.onResume()
        myDB = MyDB.getInstance(root.context)
        val category = myDB.categoryDao().getCategory()

        rvAdapter = RvAdapter(root.context, category, object : RvAdapter.RvClick {
            override fun onClick(
                categoriya: Categoriya,
                linearLayout: LinearLayout,
                imageView: ImageView
            ) {

                val popupMenu =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        PopupMenu(
                            root.context,
                            imageView,
                            Gravity.NO_GRAVITY,
                            R.style.popupBGStyle,
                            R.style.popupBGStyle1
                        )
                    } else {
                        TODO("VERSION.SDK_INT < LOLLIPOP_MR1")
                    }

                popupMenu.inflate(R.menu.my_menu_kategoriya)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true)
                }

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.ozgartirishKategoriya -> {
                            val dialog = AlertDialog.Builder(root.context).create()

                            val dialogView = layoutInflater.inflate(
                                R.layout.kategoriya_qoshish,
                                null,
                                false
                            )
                            dialog.setView(dialogView)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            dialog.setCancelable(true)

                            val kategoriyaNomi = dialogView.kategoriy_nomi
                            kategoriyaNomi.setText(categoriya.kategoriyaNomi)
                            dialog.show()

                            dialog.bekorQilishKategoriyaQoshishni.setOnClickListener {
                                dialog.dismiss()
                                Toast.makeText(
                                    root.context,
                                    "Malumot Saqlanmadi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            dialog.saqlashKategoriyaQoshishni.setOnClickListener {
                                if (categoriya.kategoriyaNomi == dialogView.kategoriy_nomi.text.toString()) {
                                    Toast.makeText(
                                        root.context,
                                        "Malumot O'zgartirilmadi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {

                                    if (dialogView.kategoriy_nomi.text.toString().isEmpty())
                                        Toast.makeText(
                                            root.context,
                                            "Bo'sh Malumot Saqlay Olmayman",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    else {
                                        val toString = dialogView.kategoriy_nomi.text.toString()
                                        myDB = MyDB.getInstance(root.context)

                                        val myDBLugat = MyDBLugat.getInstance(root.context)
                                        val list = myDBLugat.categoryDaoLugat().getLugat()
                                        for (lugat in list) {
                                            if (lugat.tanlanganKategoriya == toString) {
                                                lugat.tanlanganKategoriya = toString
                                                println("Kirdi")
                                                myDBLugat.categoryDaoLugat().updateLugat(lugat)
                                            }
                                        }
                                        categoriya.kategoriyaNomi = toString




                                        myDB.categoryDao().updateCategory(categoriya)

                                        Toast.makeText(
                                            root.context,
                                            "Malumot O'zgartirildi",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        onResume()

                                        dialog.dismiss()
                                        popupMenu.dismiss()

                                    }


                                }
                            }
                        }

                        R.id.ochirishKategoriya -> {

                            val dialog = AlertDialog.Builder(root.context).create()

                            val dialogView = layoutInflater.inflate(
                                R.layout.kategoriya_ochirish,
                                null,
                                false
                            )
                            dialog.setView(dialogView)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            dialog.setCancelable(true)

                            dialogView.ochirishKategoriyaYoq.setOnClickListener {
                                Toast.makeText(
                                    root.context,
                                    "Kategoriya O'chirilmadi",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }

                            dialogView.ochirishKategoriyaHa.setOnClickListener {
                                Toast.makeText(
                                    root.context,
                                    "Kategoriya O'chirildi",
                                    Toast.LENGTH_SHORT
                                ).show()

                                myDB = MyDB.getInstance(root.context)

                                val mydbLugat = MyDBLugat.getInstance(root.context)
                                val lugat = mydbLugat.categoryDaoLugat().getLugat()


                                for (lugat1 in lugat) {
                                    if (lugat1.tanlanganKategoriya == categoriya.kategoriyaNomi) {

                                        val file = File(
                                            requireActivity().filesDir,
                                            "${lugat1.rasim.toString()}"
                                        )
                                        for (i in requireActivity().filesDir.listFiles().indices) {
                                            if (requireActivity().filesDir.listFiles()[i] == file) {
                                                requireActivity().filesDir.listFiles()[i].delete()
                                                break
                                            }
                                        }

                                        mydbLugat.categoryDaoLugat().deleteLugat(lugat1)
                                    }
                                }

                                myDB.categoryDao().deleteCategory(categoriya)


                                onResume()

                                dialog.dismiss()

                            }


                            dialog.show()


                        }
                    }

                    true
                }

                popupMenu.show()
            }

        })

        root.rv_kategoriya.adapter = rvAdapter
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Kategoriya().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}