package ramizbek.aliyev.dictionaryapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.view.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.activity.MalumotKorish
import ramizbek.aliyev.dictionaryapp.adapter.RvAsosiy
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Categoriya
import ramizbek.aliyev.dictionaryapp.entity.Lugat

private const val CATEGORIYA = "categoriya"

class TestFragment : Fragment() {
    private var category: Categoriya? = null
    lateinit var root: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getSerializable(CATEGORIYA) as Categoriya

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_test, container, false)

        val myDBLugat = MyDBLugat.getInstance(root.context)
        val lugat = myDBLugat.categoryDaoLugat().getLugat()
        val newList = ArrayList<Lugat>()
        for (lugat in lugat) {
            if (lugat.tanlanganKategoriya == category!!.kategoriyaNomi) {
                newList.add(lugat)
            }
        }
        val rvAsosiy = RvAsosiy(root.context, newList, object : RvAsosiy.RvClick {
            override fun onClick(lugat: Lugat) {

                val intent = Intent(context, MalumotKorish::class.java)
                intent.putExtra("lugatId",lugat)
                startActivity(intent, null)
/*

     val intent = Intent(context, MalumotKorish::class.java)
                intent.putExtra("lugatId", lugat.id)
                startActivity(context, intent, null)
                val myDBLugat = MyDBLugat.getInstance(root.context)
                val lugat = myDBLugat.categoryDaoLugat().getLugat()
                val newList = ArrayList<Lugat>()
                for (lugat in lugat) {
                    if (lugat.tanlanganKategoriya == category!!.kategoriyaNomi) {
                        newList.add(lugat)
                    }
                }


* */
/*


                val dialog = AlertDialog.Builder(root.context).create()

                val dialogView = layoutInflater.inflate(
                    R.layout.fragment_malumot_korish,
                    null,
                    false
                )
                dialog.setView(dialogView)
                //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.setCancelable(true)
//
//                dialog.window!!.setLayout(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )

                dialog.show()
*/


            }

        })

        root.rv_asosiy.adapter = rvAsosiy



        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Categoriya) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CATEGORIYA, param1)
                }
            }
    }
}