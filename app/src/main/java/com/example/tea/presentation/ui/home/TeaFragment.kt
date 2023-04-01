package com.example.tea.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tea.data.models.Product
import com.example.tea.databinding.FragmentTeaBinding
import com.example.tea.presentation.ui.Adapters.PlantAdapter
import com.example.tea.presentation.ui.MainActivity.TokenViewModel
import com.example.tea.presentation.ui.showproductdialog.ShowProductDialogFragment
import com.example.tea.presentation.ui.home.TeaViewModelFactory

class TeaFragment : Fragment(), PlantAdapter.Listener {
    private lateinit var token:String
    private var adapter = PlantAdapter(this, "Чай")
    private var _binding: FragmentTeaBinding? = null
    private val tokenViewModel: TokenViewModel by activityViewModels() //// Для обмена даннами между активити и фрагментами
    private lateinit var teaViewModel: TeaViewModel
    private val binding get() = _binding!!

    companion object{ // Cистема флагов - вынужденная мера из-за поворотов экрана
        private  var f = false
        private  var compare = ""



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        f = false
        _binding = FragmentTeaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rcView1.layoutManager = GridLayoutManager(context, 2)
        binding.rcView1.adapter = adapter

        tokenViewModel.messageFromactivityToken.observe(viewLifecycleOwner) {
            token = it
            teaViewModel = ViewModelProvider(this, TeaViewModelFactory(
                token = it,
                tokenViewModel= tokenViewModel,
                context = requireContext())).get(TeaViewModel::class.java)
            teaViewModel.initAdapter(binding.rcView1,adapter)
            teaViewModel.setKorzObj()
          getObserves()
        }

        // Refresh function for the layout
        binding.refreshLayout.setOnRefreshListener{
            //Обязательно R.id.nav_home чтобы обновить свой фрагмент
          //  val navOptions = NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_content_main, true).build()
          //  findNavController().navigate(R.id.nav_home, null, navOptions)
            teaViewModel.reloadPlantAdapter()
            binding.refreshLayout.isRefreshing = false
        }
        return root
    }

    fun getObserves() {
        teaViewModel.progressBarVisibility.observe(viewLifecycleOwner){
            binding.progressBar.isVisible  = it
        }
        teaViewModel.recyclerLiveData.observe(viewLifecycleOwner) {
            binding.rcView1.adapter = it.adapter
        }
        tokenViewModel.productLiveData.observe(viewLifecycleOwner){
            if(f ==true && compare != it.title) {
                ShowProductDialogFragment.newInstance(
                    it.title,
                    it.prise,
                    "Чай",
                    "$token"
                ).show(parentFragmentManager, ShowProductDialogFragment.TAG)
            }
            compare = it.title
        }
        tokenViewModel.booleanCloseDialogFragmentsLiveData.observe(viewLifecycleOwner){
            teaViewModel.reloadPlantAdapter()
        }
   }

    override fun onClick(product: Product) {
        super.onClick(product)
        f = true
        compare = ""
        tokenViewModel.productLiveData.value = product
    }

    override fun onClickButtonAddKorzina(product: Product){
        teaViewModel.putItemVkorsinu(product)
    }

    override fun setCountPlus(product: Product, pos: Int, count: Int){
        val plusMinus = "plus"
        teaViewModel.setCount(count, plusMinus, product)
    }

    override fun setCountMinus(product: Product, pos: Int, count: Int){
        val plusMinus = "minus"
        teaViewModel.setCount(count, plusMinus, product)
        if(count == 50){ // 50 тольк если чай ил кофе. в другом случае 0
            teaViewModel.deleteItemKorsina(product, pos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//
//@JvmStatic
//fun newInstance(): TeaFragment {
//    return TeaFragment().apply {
//        arguments = Bundle().apply {
//
//        }
//    }
//}
