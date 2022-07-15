package ramizbek.aliyev.dictionaryapp.fragment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_lugatlar.view.*
import kotlinx.android.synthetic.main.soz_ochirish.view.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.activity.SozQoshishYokiTahrirlash
import ramizbek.aliyev.dictionaryapp.activity.TahrirlashOchirishSoz
import ramizbek.aliyev.dictionaryapp.adapter.RvAdapterLugat
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Lugat
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Lugatlar.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lugatlar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View

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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_lugatlar, container, false)


        root.orqaga_lugat.setOnClickListener {
            requireActivity().finish()
        }

        root.qoshish_lugat.setOnClickListener {
            val intent = Intent(requireActivity(), SozQoshishYokiTahrirlash::class.java)
            startActivity(intent)
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        val myDBLugat = MyDBLugat.getInstance(root.context)
        val lugat = myDBLugat.categoryDaoLugat().getLugat()
        val rvAdapterLugat = RvAdapterLugat(root.context, lugat, object : RvAdapterLugat.RvClick {
            override fun onClick(lugat: Lugat, linearLayout: LinearLayout, imageView: ImageView) {
                val popupMenu =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        PopupMenu(
                            root.context,
                            imageView,
                            Gravity.NO_GRAVITY,
                            R.style.popupBGStyle1,
                            R.style.popupBGStyle
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
                        //ozgartirish lugat
                        R.id.ozgartirishKategoriya -> {
                            val intent = Intent(context, TahrirlashOchirishSoz::class.java)
                            intent.putExtra("lugat", lugat.id)
                            startActivity(intent, null)
                        }
                        //ochirish lugat
                        R.id.ochirishKategoriya -> {

                            val dialog = AlertDialog.Builder(root.context).create()

                            val dialogView = layoutInflater.inflate(
                                R.layout.soz_ochirish,
                                null,
                                false
                            )
                            dialog.setView(dialogView)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            dialog.setCancelable(true)

                            dialogView.ochirishLugatYoq.setOnClickListener {
                                Toast.makeText(
                                    root.context,
                                    "Word not deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }

                            dialogView.ochirishLugatHa.setOnClickListener {
                                Toast.makeText(
                                    root.context,
                                    "Word Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val myDB = MyDBLugat.getInstance(root.context)

                                val file = File(requireActivity().filesDir, "${lugat.rasim.toString()}")
                                for (i in requireActivity().filesDir.listFiles().indices) {
                                    if (requireActivity().filesDir.listFiles()[i] == file) {
                                        requireActivity().filesDir.listFiles()[i].delete()
                                        break
                                    }
                                }


                                myDB.categoryDaoLugat().deleteLugat(lugat)


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

        root.rvSozlar.adapter = rvAdapterLugat

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Lugatlar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Lugatlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}