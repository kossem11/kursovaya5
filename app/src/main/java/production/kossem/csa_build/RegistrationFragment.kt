package production.kossem.csa_build

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import production.kossem.csa_build.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            // Здесь должен быть код для регистрации нового пользователя
        }

        //binding.buttonCancel.setOnClickListener {
            // Переход обратно к фрагменту авторизации
        //    findNavController().popBackStack()
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
