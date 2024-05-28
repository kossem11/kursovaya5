package production.kossem.csa_build

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import production.kossem.csa_build.databinding.FragmentListBinding
import production.kossem.csa_build.placeholder.PlaceholderContent


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = listOf(
            MyData("Item 1", "Description 1"),
            MyData("Item 2", "Description 2"),
            MyData("Item 3", "Description 3"),
            MyData("Item 4", "Description 4"),
            MyData("Item 5", "Description 5"),
            MyData("Item 6", "Description 6"),
            MyData("Item 7", "Description 7"),
            MyData("Item 8", "Description 8"),
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = MyAdapter(dataList)

        binding.imageViewCard.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_cardFragment)
        }
    }

    fun onButtonClick(position: Int) {
        // Обработка нажатия кнопки
        val data = (binding.recyclerView.adapter as MyAdapter).dataList[position]
        // Например, выводим текст в лог
        println("Button clicked in item: ${data.text1}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}