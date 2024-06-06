package production.kossem.csa_build.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import production.kossem.csa_build.R
import production.kossem.csa_build.UI.Adapders.MyAdapter
import production.kossem.csa_build.databinding.FragmentCardBinding
import production.kossem.csa_build.databinding.FragmentListBinding


class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()
    private val userId = 1 // Идентификатор пользователя, возможно, нужно будет получить его динамически

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        fetchMaterials()

    }

    private fun fetchMaterials() {
        GlobalScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.103:4567/materials")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                val gson = Gson()
                val itemType = object : TypeToken<List<Material>>() {}.type
                val dataList: List<Material> = gson.fromJson(responseData, itemType)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = MyAdapter(dataList, userId)
                }
            } else {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    println("Failed to fetch materials: ${response.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}