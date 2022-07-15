package ramizbek.aliyev.dictionaryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ramizbek.aliyev.dictionaryapp.databinding.ItemCategoriyaBinding
import ramizbek.aliyev.dictionaryapp.entity.Categoriya


class RvAdapter(val context: Context, val list: List<Categoriya>, var rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(var itemCategoriyaBinding: ItemCategoriyaBinding) :
        RecyclerView.ViewHolder(itemCategoriyaBinding.root) {
        fun onBind(categoriya: Categoriya) {

            itemCategoriyaBinding.kategoriyNomiRv.text = categoriya.kategoriyaNomi

            itemCategoriyaBinding.popUpMenuKategoriya.setOnClickListener {
                rvClick.onClick(
                    categoriya,
                    itemCategoriyaBinding.popUpMenuKategoriya,
                    itemCategoriyaBinding.menuKategoriya
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCategoriyaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun onClick(categoriya: Categoriya, linearLayout: LinearLayout, imageView: ImageView)
    }
}