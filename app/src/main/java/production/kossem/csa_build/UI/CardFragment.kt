package production.kossem.csa_build.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import production.kossem.csa_build.UI.Adapders.CartAdapter
import production.kossem.csa_build.UI.Adapders.MyAdapter
import production.kossem.csa_build.databinding.FragmentCardBinding
import production.kossem.csa_build.databinding.FragmentListBinding


class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        fetchCartItems()

        binding.buttonClear.setOnClickListener {
            clearCart()
        }
    }

    private fun fetchCartItems() {
        val userId = getUserId()
        GlobalScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.103:4567/userMaterials?userid=$userId")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Log.d("Check","soooooood")
                val responseData = response.body?.string()
                val gson = Gson()
                val itemType = object : TypeToken<List<Map<String, Any>>>() {}.type
                val dataList: List<Map<String, Any>> = gson.fromJson(responseData, itemType)
                val totalSum = dataList.sumOf { (it["count"] as Double) * (it["price"] as Double).toInt() }
                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = CartAdapter(dataList)
                    binding.textViewSum.text = totalSum.toString()
                }
            } else {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    println("Failed to fetch cart items: ${response.message}")
                }
            }
        }
    }

    private fun clearCart() {
        val userId = getUserId()
        GlobalScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.103:4567/clearCart?userid=$userId")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = CartAdapter(emptyList())
                    binding.textViewSum.text = "0"
                }
            } else {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    println("Failed to clear cart: ${response.message}")
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