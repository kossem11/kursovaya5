package production.kossem.csa_build.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import production.kossem.csa_build.R
import production.kossem.csa_build.UI.Adapders.MyAdapter
import production.kossem.csa_build.databinding.FragmentListBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getUserId()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        fetchMaterials()

        binding.imageViewCard.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_cardFragment)
        }
        binding.imageViewSearch.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        }
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

    private fun getUserId(): Int {
        val sharedPreferences = activity?.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        return sharedPreferences?.getInt("userid", 0) ?: 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}