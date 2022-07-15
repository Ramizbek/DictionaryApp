package ramizbek.aliyev.dictionaryapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ramizbek.aliyev.dictionaryapp.R
import ramizbek.aliyev.dictionaryapp.entity.Categoriya


class CustomAdapter(private var context1: Context, private var fruit: ArrayList<Categoriya>) :
    BaseAdapter() {
    private var inflter: LayoutInflater = LayoutInflater.from(context1)

    override fun getCount(): Int {
        return fruit.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val v = inflter.inflate(R.layout.custom_spinner, null)
        //val icon = v.findViewById<View>(R.id.imageView) as ImageView?
        val names = v!!.findViewById<View>(R.id.tv) as TextView?

        names!!.text = fruit[i].kategoriyaNomi


        return v
    }
}