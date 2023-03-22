package com.example.tea.presentation.ui.korsinadialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.R
import com.example.tea.data.repository.WhatsAppRepository
import com.example.tea.data.storage.whatsapp.WhatsAppImp
import com.example.tea.databinding.KorsinaItemBinding
import com.example.tea.domain.models.CreatFolderModelDomain
import com.example.tea.domain.models.WhatsAppModelDomain
import com.example.tea.domain.usecase.WhatsAppUseCase
import com.example.tea.presentation.ui.Adapters.KorsinaAdapter
import com.example.tea.presentation.ui.MainActivity.MainActivity
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina
import java.text.SimpleDateFormat
import java.util.*


class KorsinaDialogFragment: DialogFragment(), KorsinaAdapter.Listener {

    val domainWhatsAppInterface by lazy(LazyThreadSafetyMode.NONE) { WhatsAppRepository(
        WhatsAppImp(this, requireContext())
    ) }
    private val whatsAppUseCase by lazy(LazyThreadSafetyMode.NONE) { WhatsAppUseCase(domainWhatsAppInterface) }

    private lateinit var korsinaDialogFragmentViewModel: KorsinaDialogFragmentViewModel
    private lateinit var rcViewKorsina: RecyclerView
    private val adapter = KorsinaAdapter(this)
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var itog: TextView
    lateinit var clientName: TextView
    lateinit var textForClient: TextView
    lateinit var textViewDate: TextView
    lateinit var textViewTime: TextView
    lateinit var btRegisterOrder: Button
    lateinit var buttonClear: Button
    lateinit var btDostavka: Button
    lateinit var btSamovivoz: Button
    lateinit var buttonZakaz: Button
    lateinit var editTextAdres: EditText
    lateinit var radioKorsinaGroup: RadioGroup
    lateinit var radioButton1: RadioButton
    lateinit var radioButton2: RadioButton
    var dostavkaOrSamovivoz = ""
    var adresdostavkaOrSamovivoz =""
    var checkRadio =""
    var dateTime =""
    var allItog =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dialog_korsina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        korsinaDialogFragmentViewModel = ViewModelProvider(this, KorsinaDialogFragmentViewModelFactory(requireContext(), this)).get(
            KorsinaDialogFragmentViewModel::class.java)

        rcViewKorsina = view.findViewById(R.id.rcViewKorsina)
        itog = view.findViewById(R.id.textViewAllItog)
        clientName = view.findViewById(R.id.editTelef)
        btRegisterOrder = view.findViewById(R.id.buttonRegisterOrder)
        textForClient = view.findViewById(R.id.textForClient)
        btDostavka = view.findViewById(R.id.btDostavka)
        btSamovivoz = view.findViewById(R.id.btSamovivoz)
        editTextAdres = view.findViewById(R.id.editTextAdres)
        radioKorsinaGroup =  view.findViewById(R.id.radioKorsina)
        buttonZakaz = view.findViewById(R.id.buttonZakaz)
        textViewDate = view.findViewById(R.id.textViewDate)
        textViewTime = view.findViewById(R.id.textViewTime)
        buttonClear = view.findViewById(R.id.buttonClearKor)
        radioButton1 = view.findViewById(R.id.radioButtonAdres1)
        radioButton2 = view.findViewById(R.id.radioButtonAdres2)

        rcViewKorsina.layoutManager = GridLayoutManager(context, 1) // 1-количестро столбцов
        rcViewKorsina.adapter = adapter
        korsinaDialogFragmentViewModel.initAdapter(rcViewKorsina, adapter)


//        val boldItalicTypeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
//        val nornalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL)

        btDostavka.setOnClickListener(){
            korsinaDialogFragmentViewModel.setEditTextAdresVisibility(true)
            korsinaDialogFragmentViewModel.setRadioKorsinaGroupVisibility(false)
            korsinaDialogFragmentViewModel.setDostavkaTypeface(Typeface.BOLD_ITALIC)
            korsinaDialogFragmentViewModel.setSamovivizTypeface(Typeface.NORMAL)
        }
        btSamovivoz.setOnClickListener(){
            korsinaDialogFragmentViewModel.setEditTextAdresVisibility(false)
            korsinaDialogFragmentViewModel.setRadioKorsinaGroupVisibility(true)
            korsinaDialogFragmentViewModel.setSamovivizTypeface(Typeface.BOLD_ITALIC)
            korsinaDialogFragmentViewModel.setDostavkaTypeface(Typeface.NORMAL)

        }
        buttonClear.setOnClickListener(){
            korsinaDialogFragmentViewModel.clearKorsina()
            dismiss()
        }
        val buttonClose = view.findViewById<Button>(R.id.buttClos)
        buttonClose.setOnClickListener(){
            dismiss()
        }

        btRegisterOrder.setOnClickListener(){
            editTextAdres.setText(korsinaDialogFragmentViewModel.adresLiveData.value?.text)
            clientName.setText(korsinaDialogFragmentViewModel.clientNameLiveData.value?.text)

            korsinaDialogFragmentViewModel.setBtRegisterOrderVisibility(false)
            korsinaDialogFragmentViewModel.setTextForClientVisibility(true)
            korsinaDialogFragmentViewModel.setClientNameVisibility(true)
            korsinaDialogFragmentViewModel.setBtDostavkaVisibility(true)
            korsinaDialogFragmentViewModel.setBtSamovivozVisibility(true)
            korsinaDialogFragmentViewModel.setButtonZakazVisibility(true)
            korsinaDialogFragmentViewModel.setTextViewDateVisibility(true)
            korsinaDialogFragmentViewModel.setTextViewTimeVisibility(true)
            korsinaDialogFragmentViewModel.setRcViewKorsinaVisibility(false)
            korsinaDialogFragmentViewModel.setButtonClearVisibility(false)
        }

        buttonZakaz.setOnClickListener(){
            korsinaDialogFragmentViewModel.saveClientName(clientName.text.toString())
            korsinaDialogFragmentViewModel.saveAdres(editTextAdres.text.toString())

            dateTime = textViewDate.text.toString() + " " + textViewTime.text.toString()
            if(korsinaDialogFragmentViewModel.editTextAdresVisibility.value == true){
                dostavkaOrSamovivoz = "Доставка"
                adresdostavkaOrSamovivoz = editTextAdres.text.toString()
            }
            else{
                if(checkRadio!="") {
                    dostavkaOrSamovivoz = "Самовывоз"
                    adresdostavkaOrSamovivoz = checkRadio
                }
            }
            sendWhatsApp(dateTime)
            dismiss()
        }

        getObserves()
        setDataAndTimeOrder()
        clickRadioButtons()
    }

    fun sendWhatsApp(dateAndTime: String){
        var strToSend:String = ""

        for(i in korsinaDialogFragmentViewModel.finishedKorsinsList.indices) run {
            strToSend += korsinaDialogFragmentViewModel.finishedKorsinsList[i].name +
                    " - Цена: " + korsinaDialogFragmentViewModel.finishedKorsinsList[i].cena +
                    ", Шт: "+ korsinaDialogFragmentViewModel.finishedKorsinsList[i].count +
                    ", Сумма: " + korsinaDialogFragmentViewModel.finishedKorsinsList[i].itog +"\n"
        }

        strToSend+= "Итог: " + korsinaDialogFragmentViewModel.allItogLiveData.value + " р." + "\n"+
                clientName.text.toString() + "\n"+
                dostavkaOrSamovivoz + ":" + " " + adresdostavkaOrSamovivoz + "\n"+
                dateAndTime

        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date()).replace(":", "-")

        val creatFolderModelDomain = CreatFolderModelDomain(
            "y0_AgAAAAAOOV-mAADLWwAAAADbEsfXQiTanJTcSSy6UjpR8FNxpO14wEs",
            "Заказы/Чай/" + allItog + "р " + date
        )

        korsinaDialogFragmentViewModel.sendToVadim(creatFolderModelDomain)

        val whatsAppModelDomain = WhatsAppModelDomain(strToSend = strToSend)
        whatsAppUseCase.execute(whatsAppModelDomain)
    }

    fun clickRadioButtons(){
        radioKorsinaGroup .setOnCheckedChangeListener { radioGroup, optionId ->
            when (optionId) {
                R.id.radioButtonAdres1 -> {
                    checkRadio = radioButton1.text.toString()
                }
                R.id.radioButtonAdres2 -> {
                    checkRadio = radioButton2.text.toString()
                }
            }
        }
    }

    fun setDataAndTimeOrder(){
        textViewDate.setOnClickListener {
            val cal = Calendar.getInstance()
            dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                 val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                korsinaDialogFragmentViewModel.setDataLiveData(sdf.format(cal.time))
            }
            context?.let { it1 ->
                DatePickerDialog(
                    it1, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        textViewTime.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val hh = c.get(Calendar.HOUR_OF_DAY)
            val mm = c.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                context, { view, hourOfDay, minute ->
                    korsinaDialogFragmentViewModel.setTimeLiveData("" + hourOfDay + ":" + minute)
                }, hh, mm, true)
            timePickerDialog.show()
        }
    }

    fun getObserves() {

        korsinaDialogFragmentViewModel.typefaceDostavkaLiveData.observe(viewLifecycleOwner){
            btDostavka.typeface = it
        }
        korsinaDialogFragmentViewModel.typefaceSamovivozLiveData.observe(viewLifecycleOwner){
            btSamovivoz.typeface = it
        }

        korsinaDialogFragmentViewModel.btRegisterOrderVisibility.observe(viewLifecycleOwner){
            btRegisterOrder.isVisible = it
        }
        korsinaDialogFragmentViewModel.textForClientVisibility.observe(viewLifecycleOwner){
            textForClient.isVisible = it
        }
        korsinaDialogFragmentViewModel.clientNametVisibility.observe(viewLifecycleOwner){
            clientName.isVisible = it
        }
        korsinaDialogFragmentViewModel.btDostavkaVisibility.observe(viewLifecycleOwner){
            btDostavka.isVisible = it
        }
        korsinaDialogFragmentViewModel.btSamovivozVisibility.observe(viewLifecycleOwner){
            btSamovivoz.isVisible = it
        }
        korsinaDialogFragmentViewModel.editTextAdresVisibility.observe(viewLifecycleOwner){
            editTextAdres.isVisible = it
        }
        korsinaDialogFragmentViewModel.radioKorsinaGroupVisibility.observe(viewLifecycleOwner){
            radioKorsinaGroup.isVisible = it
        }
        korsinaDialogFragmentViewModel.buttonZakazVisibility.observe(viewLifecycleOwner){
            buttonZakaz.isVisible = it
        }
        korsinaDialogFragmentViewModel.textViewDateVisibility.observe(viewLifecycleOwner){
            textViewDate.isVisible = it
        }
        korsinaDialogFragmentViewModel.textViewTimeVisibility.observe(viewLifecycleOwner){
            textViewTime.isVisible = it
        }
        korsinaDialogFragmentViewModel.rcViewKorsinaVisibility.observe(viewLifecycleOwner){
            rcViewKorsina.isVisible = it
        }
        korsinaDialogFragmentViewModel.buttonClearVisibility.observe(viewLifecycleOwner){
            buttonClear.isVisible = it
        }

        korsinaDialogFragmentViewModel.dateLiveData.observe(viewLifecycleOwner){
            textViewDate.text = it
        }
        korsinaDialogFragmentViewModel.timeLiveData.observe(viewLifecycleOwner){
            textViewTime.text = it
        }
        korsinaDialogFragmentViewModel.allItogLiveData.observe(viewLifecycleOwner){
            allItog = it.toString()
        }

        korsinaDialogFragmentViewModel.recyclerKorsinaLiveData.observe(viewLifecycleOwner){
            rcViewKorsina.adapter = adapter
            rcViewKorsina.adapter = it.adapter
        }
        korsinaDialogFragmentViewModel.allItogLiveData.observe(viewLifecycleOwner){
            itog.text = "Итог " + it.toString() + " р"
        }
    }

    // Удаляет элемент из корзины
    override fun onClickButtonDelete(kors: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding, korsinaList: ArrayList<ItemKorsina>) {
        super.onClickButtonDelete(kors, it, pos, bind, korsinaList)
        if(korsinaList.size == 1) {
            korsinaDialogFragmentViewModel.deleteItemList(pos)
             dismiss()
        } else{
            korsinaDialogFragmentViewModel.deleteItemList(pos)
        }
    }

    override fun onClickButtonKorPlus(kors: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding) {
        super.onClickButtonKorPlus(kors, it, pos, bind)
        korsinaDialogFragmentViewModel.setCount(pos, kors, "plus")
    }
    override fun onClickButtonKorMinus(kors: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding) {
        super.onClickButtonKorMinus(kors, it, pos, bind)
        korsinaDialogFragmentViewModel.setCount(pos, kors, "minus")
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

    companion object {
        const val TAG = "Dialog"
        fun newInstance(): KorsinaDialogFragment {
            val fragment = KorsinaDialogFragment()
            return fragment
        }
    }
}




//        val radioKorsinaGroup =  view.findViewById<RadioGroup>(R.id.radioKorsina)
//
//        for(i in 0 until adressList.size) {
//            val radioButton = RadioButton(context)
//            radioButton.setText(adressList[i])
//            buttonList.add(radioButton)
//            radioKorsinaGroup.addView(buttonList[i])
//
//            radioButton.setOnClickListener(){
//                Log.d("444","${radioButton.text}")
//            }
////            radioButton.setOnLongClickListener(){
////                Log.d("555","${radioButton.text}")
////                adressList.removeAt(i)
////                return@setOnLongClickListener true
////            }
//
//        }


////////////////////////////////////////////////////////////////////////////////////////////////

//        var buttonList = ArrayList<RadioButton>()
//
//        var adressList = ArrayList<String>()
//        adressList.add("11111")
//        adressList.add("22222")
//        adressList.add("33333")
//        adressList.add("44444")

////////////////////////////////////////////////////////////////////////////////////////////