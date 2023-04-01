package com.example.tea.presentation.gallery

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
import com.example.tea.databinding.FragmentKofeBinding
import com.example.tea.presentation.home.KofeViewModel
import com.example.tea.presentation.ui.Adapters.PlantAdapter
import com.example.tea.presentation.ui.MainActivity.TokenViewModel
import com.example.tea.presentation.ui.showproductdialog.ShowProductDialogFragment
import com.example.tea.presentation.ui.kofe.KofeViewModelFactory

class KofeFragment : Fragment(), PlantAdapter.Listener {
    private lateinit var token:String
    private var adapter = PlantAdapter(this, "Кофе")
    private var _binding: FragmentKofeBinding? = null
    private val tokenViewModel: TokenViewModel by activityViewModels() //// Для обмена даннами между активити и фрагментами
    private lateinit var kofeViewModel: KofeViewModel
    private val binding get() = _binding!!

    companion object {  // Cистема флагов - вынужденная мера из-за поворотов экрана
        private var f = false
        private var compare = ""

//        private const val ARG_SECTION_NUMBER = "section_number"
//        @JvmStatic
         fun newInstance() = KofeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        f =false
        _binding = FragmentKofeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rcView1.layoutManager = GridLayoutManager(context, 2)
        binding.rcView1.adapter = adapter

        tokenViewModel.messageFromactivityToken.observe(viewLifecycleOwner) {
            token = it
            kofeViewModel = ViewModelProvider(this, KofeViewModelFactory(
                token = it,
                tokenViewModel= tokenViewModel,
                context = requireContext())
            ).get(KofeViewModel::class.java)
            kofeViewModel.initAdapter(binding.rcView1,adapter)
            kofeViewModel.setKorzObj()
            getObserves()
        }

        // Refresh function for the layout
        binding.refreshLayout.setOnRefreshListener{
            //Обязательно R.id.nav_home чтобы обновить свой фрагмент
          //  val navOptions = NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_content_main, true).build()
         //   findNavController().navigate(R.id.nav_gallery, null, navOptions)
            kofeViewModel.reloadPlantAdapter()
            binding.refreshLayout.isRefreshing = false
        }
        return root
    }

    fun getObserves() {
        kofeViewModel.progressBarVisibility.observe(viewLifecycleOwner){
            binding.progressBar.isVisible  = it
        }
        kofeViewModel.recyclerLiveData.observe(viewLifecycleOwner) {
            binding.rcView1.adapter = it.adapter
        }
        tokenViewModel.productLiveData.observe(viewLifecycleOwner){
            if(f ==true && compare != it.title) {
                ShowProductDialogFragment.newInstance(
                    it.title,
                    it.prise,
                    "Кофе",
                    "$token"
                ).show(parentFragmentManager, ShowProductDialogFragment.TAG)
            }
            compare = it.title
        }
        tokenViewModel.booleanCloseDialogFragmentsLiveData.observe(viewLifecycleOwner){
            kofeViewModel.reloadPlantAdapter()
        }
    }

    override fun onClick(product: Product) {
        super.onClick(product)
        f = true
        compare = ""
        tokenViewModel.productLiveData.value = product
    }

    override fun onClickButtonAddKorzina(product: Product){
        kofeViewModel.putItemVkorsinu(product)
    }

    override fun setCountPlus(product: Product, pos: Int, count: Int){
        val plusMinus = "plus"
        kofeViewModel.setCount(count, plusMinus, product)
    }

    override fun setCountMinus(product: Product, pos: Int, count: Int){
        val plusMinus = "minus"
        kofeViewModel.setCount(count, plusMinus, product)
        if(count == 50){ // 50 тольк если чай ил кофе. в другом случае 0
            kofeViewModel.deleteItemKorsina(product, pos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//
//const val TAG = "2"
//@JvmStatic
//fun newInstance(): KofeFragment {
//    return KofeFragment().apply {
//        arguments = Bundle().apply {
//
//        }
//    }
//}
