package production.kossem.csa_build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import production.kossem.csa_build.databinding.ItemViewBinding

class MyAdapter(val dataList: List<MyData>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.binding.textViewSpec.text = data.text1
        holder.binding.textViewCount.text = data.text2
        holder.binding.buttonAdd.setOnClickListener {
            // Обработка нажатия кнопки
        }
    }

    override fun getItemCount(): Int = dataList.size
}

data class MyData(
    val text1: String,
    val text2: String
)

