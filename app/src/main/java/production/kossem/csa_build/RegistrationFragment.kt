package production.kossem.csa_build

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import production.kossem.csa_build.databinding.FragmentRegistrationBinding
import java.io.IOException

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            registerUser(username, email, password)
        }
    }

    private fun registerUser(username: String, mail: String, password: String) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("username", username)
            .add("mail", mail)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.103:4567/addUser")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Registration failed1: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.d("registration", e.message.toString())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Registration failed2: ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
