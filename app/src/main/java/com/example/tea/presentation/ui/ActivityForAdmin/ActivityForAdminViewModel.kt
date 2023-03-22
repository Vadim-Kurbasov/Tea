package com.example.tea.presentation.ui.ActivityForAdmin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.models.LinkToUploadImageModelDomain
import com.example.tea.domain.models.PutImageToDiskModelDomain
import com.example.tea.domain.usecase.*
import com.example.tea.presentation.ui.Adapters.AdapterImageAdmin

class ActivityForAdminViewModel(
    private val contentResolver: android.content.ContentResolver,
    private val getTokenUseCase: getTokenUseCase,
    private val creatFolderProductUseCase:CreatFolderProductUseCase,
    private val creatFolderChosentUseCase: CreatFolderChosenUseCase,
    private val creatFolderNameUseCase: CreatFolderNameUseCase,
    private val creatFolderDescribeUseCase: CreatFolderDescribeUseCase,
    private val creatFolderPriseUseCase: CreatFolderPriseUseCase,
    private val getLinkToUploadImageUseCase: GetLinkToUploadImageUseCase,
    private val putImageToDiskUseCase: PutImageToDiskUseCase,
    private val showTostUseCase:ShowTostUseCase
)
: ViewModel() {
     var tokenLife = MutableLiveData<String>()
     var folderChoising = MutableLiveData<String>()
     var folderName = MutableLiveData<String>()
     var folderDescribe = MutableLiveData<String>()
     var folderPrise = MutableLiveData<String>()

     var imTortVisibility = MutableLiveData<Boolean>()
     var checkBoxImageVisibility = MutableLiveData<Boolean>()
     var progressBarImageVisibility = MutableLiveData<Boolean>()
     var checkBoxDescribeVisibility = MutableLiveData<Boolean>()
     var progressBarNameVisibility = MutableLiveData<Boolean>()
     var progressBarDescribeVisibility = MutableLiveData<Boolean>()
     var checkBoxPriseVisibility = MutableLiveData<Boolean>()
     var checkBoxNameVisibility = MutableLiveData<Boolean>()
     var progressBarPriseVisibility = MutableLiveData<Boolean>()
     var buttonLoadMoreVisibility = MutableLiveData<Boolean>()
     var tvCoiseFolderVisibility = MutableLiveData<Boolean>()
     var tvNameVisibility = MutableLiveData<Boolean>()
     var ratingRadioGroupVisibility = MutableLiveData<Boolean>()
     var buttonConfirmNameFVisibility = MutableLiveData<Boolean>()
     var buttonConfirmDescriptionVisibility = MutableLiveData<Boolean>()
     var buttonConfirmPriseVisibility = MutableLiveData<Boolean>()
     var editTextNameVisibility = MutableLiveData<Boolean>()
     var btLoadPhotoVisibility = MutableLiveData<Boolean>()

     var pathName = MutableLiveData<String>()
     var pathDescribe = MutableLiveData<String>()
     var pathPrise = MutableLiveData<String>()

    var pagerLiveData = MutableLiveData<ViewPager>()

    init {
         getTokenUseCase.execute()

         folderChoising.value = ""
         folderName.value = ""
         folderDescribe .value = ""
         folderPrise.value = ""

         imTortVisibility.value = false
         checkBoxImageVisibility.value = false
         progressBarImageVisibility.value = false
         progressBarNameVisibility.value = false
         checkBoxDescribeVisibility.value = false
         progressBarDescribeVisibility.value = false
         checkBoxPriseVisibility.value = false
         checkBoxNameVisibility.value = false
         progressBarPriseVisibility.value = false
         buttonLoadMoreVisibility.value = false
         tvCoiseFolderVisibility.value = false
         tvNameVisibility.value = false
    }

    fun setToken(token: String) {
        tokenLife.value = token
        creatFolderProduct()
    }

    fun setProgressBarName(boolean: Boolean){
        progressBarNameVisibility.value = boolean
    }

    fun setCheckBoxName(boolean: Boolean){
        checkBoxNameVisibility.value = boolean
    }

    fun setProgressBarDescribe(boolean: Boolean){
        progressBarDescribeVisibility.value = boolean
    }

    fun setCheckBoxDescribe(boolean: Boolean){
        checkBoxDescribeVisibility.value = boolean
    }

    fun setProgressBarPrise(boolean: Boolean){
        progressBarPriseVisibility.value = boolean
    }

    fun setCheckBoxPrise(boolean: Boolean){
        checkBoxPriseVisibility.value = boolean
    }

    fun setProgressBarImage(boolean: Boolean){
        progressBarImageVisibility.value = boolean
    }

    fun setCheckBoxImage(boolean: Boolean){
        checkBoxImageVisibility.value = boolean
    }

    fun showTost(text:String){
        showTostUseCase.execute(text)
    }

    fun creatFolderProduct(){
        val creatFolderModelDomain = CreatFolderModelDomain(token = tokenLife.value.toString(),path = "Продукция")
        creatFolderProductUseCase.execute(creatFolderModelDomain)
    }

    fun creatChosenFolder(chosenFolder:String){
        tvCoiseFolderVisibility.value = true
        ratingRadioGroupVisibility.value = false
        folderChoising.value = chosenFolder

        val creatFolderModelDomain = CreatFolderModelDomain(token = tokenLife.value.toString(),path = "Продукция/" + "$chosenFolder")
        creatFolderChosentUseCase.execute(creatFolderModelDomain)
    }

    fun creatNameFolder(nameFolder:String){
        folderName.value = nameFolder
        folderName.value = folderName.value!!.trim()
        pathName.value = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}"

        val creatFolderModelDomain = CreatFolderModelDomain(token = tokenLife.value.toString(),path = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}")
        creatFolderNameUseCase.execute(creatFolderModelDomain)
        buttonConfirmNameFVisibility.value = false
        progressBarNameVisibility.value =true
    }

    fun creatDescribeFolder(describe: String){
        folderDescribe.value = describe
        folderDescribe.value = folderDescribe.value!!.trim()
        pathDescribe.value = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}" + "/" + "${folderDescribe.value}"

        val creatFolderModelDomain = CreatFolderModelDomain(token = tokenLife.value.toString(),path = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}" + "/" + "${folderDescribe.value}")
        creatFolderDescribeUseCase.execute(creatFolderModelDomain)
            progressBarDescribeVisibility.value = true
            buttonConfirmDescriptionVisibility.value = false
            checkVisibility()
    }

    fun creatPriseFolder(prise: String){
        folderPrise.value = prise
        folderPrise.value = folderPrise.value!!.trim()
        pathPrise.value = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}" + "/" + "${folderPrise.value}"

        val creatFolderModelDomain = CreatFolderModelDomain(token = tokenLife.value.toString(),path = "Продукция/" + "${folderChoising.value}" + "/" +"${folderName.value}" + "/" + "${folderPrise.value}")
        creatFolderPriseUseCase.execute(creatFolderModelDomain)
        creatFolderPriseUseCase.execute(creatFolderModelDomain)
        progressBarPriseVisibility.value = true
        buttonConfirmPriseVisibility.value = false
        checkVisibility()
    }

    fun pickerImage(adapter: AdapterImageAdmin, pager: ViewPager, result: List<Uri>){
        imTortVisibility.value = true
        progressBarImageVisibility.value = true
        pagerLiveData.value = pager
        pagerLiveData.value!!.adapter = adapter

        for (i in result.indices) {
            val randomString = getRandomString(length= 5)
            val linkToUploadImageModelDomain = LinkToUploadImageModelDomain(token = tokenLife.value.toString(), uri = result[i], path = "Продукция/" + "${folderChoising.value}" + "/" + "${folderName.value}" + "/" +"$randomString")
            val href =  getLinkToUploadImageUseCase.execute(linkToUploadImageModelDomain)
            val putImageToDiskModelDomain = PutImageToDiskModelDomain(uri = result[i], href = href.href, contentResolver = contentResolver )
            putImagesToDisk(putImageToDiskModelDomain=putImageToDiskModelDomain)
        }
        checkVisibility()
    }

    fun putImagesToDisk(putImageToDiskModelDomain:PutImageToDiskModelDomain){

        btLoadPhotoVisibility.value = false
        putImageToDiskUseCase.execute(putImageToDiskModelDomain)
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun loadMore(){
        checkBoxNameVisibility.value = false
        checkBoxImageVisibility.value = false
        progressBarImageVisibility.value = false
        checkBoxDescribeVisibility.value = false
        progressBarDescribeVisibility.value = false
        checkBoxPriseVisibility.value = false
        progressBarPriseVisibility.value = false
        buttonLoadMoreVisibility.value =false
        buttonConfirmDescriptionVisibility.value =true
        buttonConfirmPriseVisibility.value = true
        btLoadPhotoVisibility.value = true
        imTortVisibility.value = false
        ratingRadioGroupVisibility.value =true

        tvCoiseFolderVisibility.value =false
        tvNameVisibility.value =false
        editTextNameVisibility.value =true
        buttonConfirmNameFVisibility.value=true

        folderName.value = ""
        folderDescribe.value = ""
        folderPrise.value = ""
        folderChoising.value = ""
    }

    fun checkVisibility(){
        if(buttonConfirmNameFVisibility.value == false && buttonConfirmDescriptionVisibility.value == false && buttonConfirmPriseVisibility.value == false){
            buttonLoadMoreVisibility.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}


