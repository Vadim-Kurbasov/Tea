package com.example.tea.presentation.ui.showproductdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.tea.R
import com.example.tea.presentation.ui.Adapters.AdapterImageProduct
import com.example.tea.presentation.ui.MainActivity.MainActivity

class ShowProductDialogFragment : DialogFragment(){

    companion object {
        const val TAG = "ShowProductDialogFragment"
        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_PRISE = "KEY_PRISE"
        private const val KEY_PARENTFOLDER = "KEY_PARENTFOLDER"
        private const val KEY_TOKEN = "KEY_TOKEN"

        //take the title and subtitle form the Activity
        fun newInstance(title: String, prise: Int, parentFolder: String, token: String): ShowProductDialogFragment {
            val args = Bundle()
            args.putString(KEY_NAME, title)
            args.putString(KEY_NAME, title)
            args.putInt(KEY_PRISE, prise)
            args.putString(KEY_PARENTFOLDER, parentFolder)
            args.putString(KEY_TOKEN, token)
            val fragment = ShowProductDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var showProductViewModel: ShowProductViewModel
    lateinit var pager1: ViewPager
    lateinit var token: String
    lateinit var choiseFolder: String
    lateinit var nameFolder: String
    lateinit var tvNameProd:TextView
    lateinit var tvDescribe: TextView
    lateinit var tvPrise : TextView
    lateinit var buttonClose : Button
    lateinit private var adapterImageProduct: AdapterImageProduct

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_product_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = arguments?.getString(KEY_TOKEN).toString()
        choiseFolder = arguments?.getString(KEY_PARENTFOLDER).toString()
        nameFolder = arguments?.getString(KEY_NAME).toString()
        val cena = arguments?.getInt(KEY_PRISE)!!

        showProductViewModel = ViewModelProvider(this, ShowProductViewModelFactory(token = token, choisenFolder = choiseFolder, nameFolder = nameFolder, cena = cena, requireContext())).get(
            ShowProductViewModel::class.java)

        tvNameProd = view.findViewById(R.id.textViewNameProduct)
        tvDescribe = view.findViewById(R.id.textViewDescribeProduct)
        tvPrise = view.findViewById(R.id.textViewPriseProduct)
        buttonClose = view.findViewById(R.id.buttonClose)
        buttonClose.setOnClickListener(){
            dismiss()
        }

        pager1 = view.findViewById(R.id.imProduct)
        val hrefImageList = mutableListOf<String>()
        adapterImageProduct = context?.let { AdapterImageProduct(hrefImageList, it) }!!
        showProductViewModel.setNameFolder(nameFolder)
        showProductViewModel.initAdapter(pager1, adapterImageProduct, context)
        getObserves()
    }

    fun getObserves(){
        showProductViewModel.pager1LiveData.observe(viewLifecycleOwner) {
            pager1.adapter = it.adapter
        }
        showProductViewModel.nameFolderLiveData.observe(viewLifecycleOwner){
            tvNameProd.text = it
        }
        showProductViewModel.describeLiveData.observe(viewLifecycleOwner){
            tvDescribe.text = it
        }
        showProductViewModel.summaLiveData.observe(viewLifecycleOwner){
            if(choiseFolder == "Чай"|| choiseFolder == "Кофе") {
                tvPrise.text = "Цена: " + "$it" + " р за 100 гр"
            }
            else{
                tvPrise.text = "Цена: " + "$it" + " р"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.chekKorsinaColor()
    }
}
