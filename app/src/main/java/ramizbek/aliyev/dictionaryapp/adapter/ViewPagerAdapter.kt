package ramizbek.aliyev.dictionaryapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ramizbek.aliyev.dictionaryapp.entity.Categoriya
import ramizbek.aliyev.dictionaryapp.fragment.TestFragment

class ViewPagerAdapter(var list: List<Categoriya>, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {

        return TestFragment.newInstance(list[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].kategoriyaNomi
    }
}