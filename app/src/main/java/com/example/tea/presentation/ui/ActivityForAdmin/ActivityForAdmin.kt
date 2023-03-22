package com.example.tea.presentation.ui.ActivityForAdmin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.tea.R
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModel
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModelFactory
import com.example.tea.data.storage.creatfolder.CreatFolderYaDiskFactory
import com.example.tea.data.storage.creatfolder.CreatFolderYaDiskViewModel
import com.example.tea.data.storage.putimagetodisk.PutImageToDiskViewModel
import com.example.tea.data.storage.putimagetodisk.PutImageToDiskViewModelFactory
import com.example.tea.databinding.ActivityForAdminBinding
import com.example.tea.presentation.ui.Adapters.AdapterImageAdmin

class ActivityForAdmin : AppCompatActivity() {

    private lateinit var vm: ActivityForAdminViewModel

    private lateinit var creatFolderYaDiskViewModel: CreatFolderYaDiskViewModel
    private lateinit var googleSiteTokenViewModel: GoogleSiteTokenViewModel
    private lateinit var putImageToDiskViewModel: PutImageToDiskViewModel
    private lateinit var binding: ActivityForAdminBinding
    private lateinit var pager: ViewPager

     private var pathNameFolder: String ="0"
     private var pathDescribeFolder: String ="0"
     private var pathPriseFolder: String ="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isNetworkConnected){vm.showTost("Нет интернета")}

        creatFolderYaDiskViewModel = ViewModelProvider(this, CreatFolderYaDiskFactory()).get(CreatFolderYaDiskViewModel::class.java)
        googleSiteTokenViewModel = ViewModelProvider(this, GoogleSiteTokenViewModelFactory()).get(GoogleSiteTokenViewModel::class.java)
        putImageToDiskViewModel = ViewModelProvider(this, PutImageToDiskViewModelFactory()).get(PutImageToDiskViewModel::class.java)

        vm = ViewModelProvider(this, ActivityForAdminViewModelFactory(this, creatFolderYaDiskViewModel, googleSiteTokenViewModel, putImageToDiskViewModel)).get(ActivityForAdminViewModel::class.java)

        pickerImageFromGalery()
        googleSiteTokenViewModel.tokenLifeData.observe(this) {

            if(it!="")  {
                vm.setToken(it)
                clickRadioButtons()
                clickButtonName()
                clickButtonDescribe()
                clickButtonPrise()
                loadMore()
            }
        }

        creatFolderYaDiskViewModel.stringVm.observe(this){
            if(pathNameFolder == it){
                vm.setProgressBarName(false)
                vm.setCheckBoxName(true)
            }

            if(pathDescribeFolder == it){
                vm.setProgressBarDescribe(false)
                vm.setCheckBoxDescribe(true)
            }

            if(pathPriseFolder == it){
                vm.setProgressBarPrise(false)
                vm.setCheckBoxPrise(true)
            }
            if(it == ""){
                vm.setProgressBarName(false)
                vm.setProgressBarDescribe(false)
                vm.setProgressBarPrise(false)
            }
        }

        putImageToDiskViewModel.booleanVm.observe(this){
            if(it == true){
                vm.setProgressBarImage(false)
                vm.setCheckBoxImage(true)
            }
        }

        pager = findViewById(R.id.imTort)

        vm.pathName.observe(this){
            pathNameFolder = it
        }

        vm.pathDescribe.observe(this){
            pathDescribeFolder = it
        }

        vm.pathPrise.observe(this){
            pathPriseFolder = it
        }

        vm.imTortVisibility.observe(this){
            binding.imTort.isVisible  = it
        }
        vm.checkBoxImageVisibility.observe(this){
            binding.checkBoxImage.isVisible  = it
        }
        vm.progressBarImageVisibility.observe(this){
            binding.progressBarImage.isVisible  = it
        }
        vm.progressBarNameVisibility.observe(this){
            binding.progressBarName.isVisible  = it
        }
        vm.checkBoxDescribeVisibility.observe(this){
            binding.checkBoxDescribe.isVisible  = it
        }
        vm.checkBoxNameVisibility.observe(this){
            binding.checkBoxName.isVisible  = it
        }
        vm.progressBarDescribeVisibility.observe(this){
            binding.progressBarDescribe.isVisible  = it
        }
        vm.checkBoxPriseVisibility.observe(this){
            binding.checkBoxPrise.isVisible  = it
        }
        vm.progressBarPriseVisibility.observe(this){
            binding.progressBarPrise.isVisible  = it
        }
        vm.buttonLoadMoreVisibility.observe(this){
            binding.buttonLoadMore.isVisible  = it
        }
        vm.tvCoiseFolderVisibility.observe(this){
            binding.tvCoiseFolder.isVisible  = it
        }
        vm.tvNameVisibility.observe(this){
            binding.tvName.isVisible  = it
        }

        vm.ratingRadioGroupVisibility.observe(this){
            binding.ratingRadioGroup.isVisible = it
        }

        vm.folderChoising.observe(this){
            binding.tvCoiseFolder.text = it
        }

        vm.folderName.observe(this){
            binding.tvName.text = it
        }

        vm.tvNameVisibility.observe(this){
            binding.tvName.isVisible = it
        }

        vm.buttonConfirmNameFVisibility.observe(this){
            binding.buttonConfirmNameF.isVisible = it
        }

        vm.buttonConfirmDescriptionVisibility.observe(this){
            binding.btConfirmDescription.isVisible = it
        }

        vm.buttonConfirmPriseVisibility.observe(this){
            binding.btConfirmPrise.isVisible = it
        }

        vm.editTextNameVisibility.observe(this){
            binding.editTextName.isVisible = it
        }

        vm.btLoadPhotoVisibility.observe(this){
            binding.btLoadPhoto.isVisible = it
        }

        vm.pagerLiveData.observe(this){
            pager.adapter = it.adapter
        }
    }

    fun clickRadioButtons(){

        var folderChoising:String
        binding.ratingRadioGroup.setOnCheckedChangeListener { radioGroup, optionId ->

                when (optionId) {
                    R.id.radioButtonTorty -> {
                        folderChoising = binding.radioButtonTorty.text.toString()
                        vm.creatChosenFolder(folderChoising)
                        binding.ratingRadioGroup.clearCheck()
                    }
                    R.id.radioKake -> {
                        folderChoising=binding.radioKake.text.toString()
                        vm.creatChosenFolder(folderChoising)
                        binding.ratingRadioGroup.clearCheck()
                    }
            }
        }
    }

    fun  clickButtonName(){

        binding.buttonConfirmNameF.setOnClickListener(){
            var str = binding.editTextName.text.toString()
            if(vm.folderChoising.value!="" && str!="" && !str.contains('/') && str.length < 249) {
                val  name = str
                vm.creatNameFolder(name)
            }
            else{
                Toast.makeText(this, "Выберите папку или выполните правильный ввод", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clickButtonDescribe(){
        binding.btConfirmDescription.setOnClickListener(){
            var str = binding.edDescriptionTort.text.toString()
            if( vm.folderName.value!="" &&  vm.folderChoising.value!="" && str!="" && !str.contains('/') && str.length < 249) {
                val  describe = str
                vm.creatDescribeFolder(describe)
            }
            else{
                Toast.makeText(this, "Выберите папку или выполните правильный ввод", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clickButtonPrise(){
        binding.btConfirmPrise.setOnClickListener(){
            var str = binding.edPrice.text.toString()

            if( vm.folderName.value!="" &&  vm.folderChoising.value!="" && str!="" && !str.contains('/') && str.length < 249) {
                val  prise = str
                vm.creatPriseFolder(prise)
            }
            else{
                Toast.makeText(this, "Выберите папку или выполните правильный ввод", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun loadMore(){
        binding.buttonLoadMore.setOnClickListener(){
            binding.edDescriptionTort.text.clear()
            binding.edPrice.text.clear()
            binding.editTextName.text.clear()
            vm.loadMore()
        }
    }

    fun pickerImageFromGalery(){
        val imagePickerResultActivityLauncher  = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ result ->
            if(result!=null) {
                val adapter = AdapterImageAdmin(result,this)
                vm.pickerImage(adapter, pager, result)
            }
        }
        binding.btLoadPhoto.setOnClickListener {

            if (vm.folderName.value!="" &&  vm.folderChoising.value!="") {
                imagePickerResultActivityLauncher.launch("image/*") // Запускаем лаунчер для получения Uri фотки
            }
            else{
                Toast.makeText(this, "Выберите папку и введите название", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val Context.isNetworkConnected: Boolean
        get() {
            val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                manager.getNetworkCapabilities(manager.activeNetwork)?.let {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            it.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                            it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                            it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                } ?: false
            else
                @Suppress("DEPRECATION")
                manager.activeNetworkInfo?.isConnectedOrConnecting == true
        }
}

