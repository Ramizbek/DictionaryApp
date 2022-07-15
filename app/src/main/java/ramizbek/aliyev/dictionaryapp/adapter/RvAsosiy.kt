package ramizbek.aliyev.dictionaryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ramizbek.aliyev.dictionaryapp.databinding.ItemAsosiyBinding
import ramizbek.aliyev.dictionaryapp.entity.Lugat


class RvAsosiy(val context: Context, val list: List<Lugat>, var rvClick: RvClick) :
    RecyclerView.Adapter<RvAsosiy.VH>() {
    inner class VH(var itemAsosiyBinding: ItemAsosiyBinding) :
        RecyclerView.ViewHolder(itemAsosiyBinding.root) {
        fun onBind(lugat: Lugat) {

            itemAsosiyBinding.sozItemAsosiy.text = lugat.soz
            itemAsosiyBinding.tarjimasiItemAsosiy.text = lugat.kichikMalumot

            itemAsosiyBinding.shungaBoskandayamIshlabQoy.setOnClickListener {
                rvClick.onClick(lugat)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAsosiyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun onClick(lugat: Lugat)
    }

}