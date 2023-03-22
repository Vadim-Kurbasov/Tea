package com.example.tea.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.R
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
    lateinit var recycler: RecyclerView

    // Cистема флагов - вынужденная мера из-за поворотов экрана
    companion object{
        private  var f = false
        private  var compare = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        f=false
        _binding = FragmentTeaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recycler = binding.rcView1
        binding.rcView1.layoutManager = GridLayoutManager(context, 3)
        binding.rcView1.adapter = adapter

        tokenViewModel.messageFromactivityToken.observe(viewLifecycleOwner) {
            token = it
            teaViewModel = ViewModelProvider(this, TeaViewModelFactory(token = it)).get(TeaViewModel::class.java)
            teaViewModel.initAdapter(binding.rcView1,adapter)
          getObserves()
        }

        // Refresh function for the layout
        binding.refreshLayout.setOnRefreshListener{
            //Обязательно R.id.nav_home чтобы обновить свой фрагмент
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_content_main, true).build()
            findNavController().navigate(R.id.nav_home, null, navOptions)
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
   }

    override fun onClick(product: Product) {
        super.onClick(product)
        f = true
        compare = ""
        tokenViewModel.productLiveData.value = product
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
