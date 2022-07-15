package ramizbek.aliyev.dictionaryapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tanlangan.view.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.activity.MalumotKorish
import ramizbek.aliyev.dictionaryapp.adapter.RvAdapterLike
import ramizbek.aliyev.dictionaryapp.db.MyDBLugat
import ramizbek.aliyev.dictionaryapp.entity.Lugat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tanlangan.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tanlangan : Fragment() {
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
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tanlangan, container, false)


        return root
    }

    override fun onResume() {
        super.onResume()

        val myDBLugat = MyDBLugat.getInstance(root.context)
        val lugat = myDBLugat.categoryDaoLugat().getLugat()
        val newList = ArrayList<Lugat>()

        for (lugat in lugat) {
            if (lugat.like == "1") {
                newList.add(lugat)
            }
        }

        val rvAdapterLike = RvAdapterLike(
            root.context,
            newList,
            object : RvAdapterLike.RvClick {
                override fun onClick(lugat: Lugat) {

                    val intent = Intent(context, MalumotKorish::class.java)
                    intent.putExtra("lugatId",lugat)
                    startActivity(intent, null)
                }


            })

        root.rv_like.adapter = rvAdapterLike

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tanlangan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tanlangan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}