package production.kossem.csa_build.UI.Adapders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import production.kossem.csa_build.UI.MaterialInCard
import production.kossem.csa_build.databinding.ItemCartBinding

class CartAdapter(private val dataList: List<Map<String, Any>>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.binding.textViewSpec.text = data["specification"] as String
        holder.binding.textViewCount.text = (data["count"]).toString()
        holder.binding.textViewPrice.text = (data["price"]).toString()
    }

    override fun getItemCount(): Int = dataList.size
}