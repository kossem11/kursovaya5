package production.kossem.csa_build.UI

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import okhttp3.OkHttpClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import production.kossem.csa_build.UI.Adapders.MyAdapter
import production.kossem.csa_build.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)

        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            val searchQuery = binding.etSearch.text.toString().trim()
            if (searchQuery.isNotEmpty()) {
                performSearch(searchQuery)
            } else {
                Toast.makeText(context, "Введите текст для поиска", Toast.LENGTH_SHORT).show()
            }
            true
        }

        binding.btnClear.setOnClickListener {
            binding.etSearch.text.clear()
            binding.recyclerViewSearch.adapter = MyAdapter(emptyList(), getUserId())
            binding.btnClear.visibility = View.GONE
        }
    }

    private fun performSearch(query: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.103:4567/materials/type/$query")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                val gson = Gson()
                val itemType = object : TypeToken<List<Material>>() {}.type
                val dataList: List<Material> = gson.fromJson(responseData, itemType)
                withContext(Dispatchers.Main) {
                    binding.recyclerViewSearch.adapter = MyAdapter(dataList, getUserId())
                    binding.btnClear.visibility = View.VISIBLE
                }
            } else {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Не удалось выполнить поиск: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUserId(): Int {
        val sharedPreferences = activity?.getSharedPreferences("MyApp", android.content.Context.MODE_PRIVATE)
        return sharedPreferences?.getInt("userid", 0) ?: 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}