package production.kossem.csa_build.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import production.kossem.csa_build.UI.Adapders.MyAdapter
import production.kossem.csa_build.UI.Adapders.MyData
import production.kossem.csa_build.databinding.FragmentCardBinding


class CardFragment : Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = listOf(
            MyData("Item 1", "Description 1"),
            MyData("Item 2", "Description 2"),
            MyData("Item 3", "Description 3"),
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = MyAdapter(dataList)

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