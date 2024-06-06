package production.kossem.csa_build.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import production.kossem.csa_build.R
import production.kossem.csa_build.databinding.FragmentKatalogBinding


class KatalogFragment : Fragment() {
    private var _binding: FragmentKatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allCategories.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }

        binding.textBrick.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }
        binding.imageBrick.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }


        binding.textGravel.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }
        binding.imageGravel.setOnClickListener{
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }


        binding.textCement.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }
        binding.imageCement.setOnClickListener{
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }


        binding.textMetal.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }
        binding.imageMetal.setOnClickListener{
            findNavController().navigate(R.id.action_catalogFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}