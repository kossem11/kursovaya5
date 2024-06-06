package production.kossem.csa_build.UI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import okhttp3.OkHttpClient
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import production.kossem.csa_build.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        setAppTheme(isDarkTheme)

        binding.buttonTheme.setOnClickListener {
            val newThemeIsDark = !isDarkTheme
            setAppTheme(newThemeIsDark)
            sharedPreferences.edit().putBoolean("isDarkTheme", newThemeIsDark).apply()
        }

        fetchUserData()

        binding.buttonChange.setOnClickListener {
            enableEditTexts(true)
        }

        binding.buttonSave.setOnClickListener {
            saveUserData()
            enableEditTexts(false)
        }
    }

    private fun fetchUserData() {
        val userId = getUserId()
        GlobalScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://192.168.0.103:4567/user?userid=$userId")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                val gson = Gson()
                val user = gson.fromJson(responseData, User::class.java)
                withContext(Dispatchers.Main) {
                    binding.editTextUsername.setText(user.username)
                    binding.editTextEmail.setText(user.mail)
                    binding.editTextPassword.setText(user.password)
                }
            } else {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    println("Failed to fetch user data: ${response.message}")
                }
            }
        }
    }

    private fun saveUserData() {
        val userId = getUserId()
        val userName = binding.editTextUsername.text.toString()
        val mail = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        GlobalScope.launch(Dispatchers.IO) {
            val requestBody = FormBody.Builder()
                .add("userid", userId.toString())
                .add("username", userName)
                .add("mail", mail)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url("http://192.168.0.103:4567/updateUser")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                // Обработка ошибки
                withContext(Dispatchers.Main) {
                    println("Failed to save user data: ${response.message}")
                }
            }
        }
    }

    private fun enableEditTexts(enable: Boolean) {
        binding.editTextUsername.isEnabled = enable
        binding.editTextEmail.isEnabled = enable
        binding.editTextPassword.isEnabled = enable
    }

    private fun getUserId(): Int {
        val sharedPreferences = activity?.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        return sharedPreferences?.getInt("userid", 0) ?: 0
    }

    private fun setAppTheme(isDark: Boolean) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.textViewTheme.text = "вкл"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.textViewTheme.text = "выкл"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}