package production.kossem.csa_build.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import production.kossem.csa_build.R
import production.kossem.csa_build.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewLink.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cardFragment2)
        }

        binding.imageViewCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cardFragment2)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}