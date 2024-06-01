package production.kossem.csa_build.UI

import android.os.Bundle
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
import production.kossem.csa_build.R
import production.kossem.csa_build.databinding.FragmentAuthorizationBinding
import java.io.IOException

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val mail = binding.editTextLogin.text.toString()
            val password = binding.editTextPassword.text.toString()
            login(mail, password)
        }

        binding.buttonForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_resetPasswordFragment)
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }
    private fun login(mail: String, password: String) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("mail", mail)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.103:4567/login")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Login failed: ${response.message}", Toast.LENGTH_LONG).show()
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