package production.kossem.csa_build.UI.Adapders
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import production.kossem.csa_build.UI.Material
import production.kossem.csa_build.databinding.ItemViewBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class MyAdapter(private val dataList: List<Material>, private val userId: Int) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    private val client = OkHttpClient()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.binding.textViewSpec.text = data.specification
        holder.binding.textViewCount.text = data.type
        holder.binding.buttonAdd.setOnClickListener {
            val count = holder.binding.editTextCount.text.toString().toIntOrNull()
            if (count != null && count > 0) {
                addMaterialToCard(data.materialid, count, holder)
            } else {
                Toast.makeText(holder.binding.root.context, "Please enter a valid count", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMaterialToCard(materialId: Int, count: Int, holder: ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            val formBody = FormBody.Builder()
                .add("userid", userId.toString())
                .add("materialid", materialId.toString())
                .add("count", count.toString())
                .build()

            val request = Request.Builder()
                .url("http://192.168.0.103:4567/addMaterial")
                .post(formBody)
                .build()

            val response = client.newCall(request).execute()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(holder.binding.root.context, "Material added successfully", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(holder.binding.root.context, materialId.toString()+count.toString()+userId.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
}

