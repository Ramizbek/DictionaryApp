package ramizbek.aliyev.dictionaryapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_asosiy.view.*
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.adapter.ViewPagerAdapter
import ramizbek.aliyev.dictionaryapp.db.MyDB
import ramizbek.aliyev.dictionaryapp.entity.Categoriya

private const val CATEGORIYA = "categoriya"

class Asosiy : Fragment() {

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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_asosiy, container, false)

        return root

    }

    override fun onResume() {
        super.onResume()
        val myDB = MyDB.getInstance(root.context)
        val categoryList = myDB.categoryDao().getCategory()

        val viewPagerAdapter = ViewPagerAdapter(categoryList, childFragmentManager)
        root.viewPager.adapter = viewPagerAdapter

        root.tabLayout.setupWithViewPager(root.viewPager)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Categoriya) =
            Asosiy().apply {
                arguments = Bundle().apply {
                    putSerializable(CATEGORIYA, param1)
                }
            }
    }
}