package com.example.tea.presentation.ui.MainActivity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chocolate.data.storage.greetingtext.GreetingTextImp
import com.example.tea.R
import com.example.tea.data.repository.GreetingTextRepository
import com.example.tea.data.repository.KorsinaRepository
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModel
import com.example.tea.data.storage.GoogleSiteToken.GoogleSiteTokenViewModelFactory
import com.example.tea.data.storage.korsina.KorsinaImp
import com.example.tea.databinding.ActivityMainBinding
import com.example.tea.domain.models.GreetingTextDomainModel
import com.example.tea.domain.usecase.GetGreetingTextUseCase
import com.example.tea.domain.usecase.SaveGreetingTextUseCase
import com.example.tea.domain.usecase.WorkWithKorzinaUseCase
import com.example.tea.presentation.ui.ActivityForAdmin.ActivityForAdmin
import com.example.tea.presentation.ui.korsinadialog.KorsinaDialogFragment
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory

class MainActivity : AppCompatActivity() {

    lateinit var appUpdateManager: AppUpdateManager // Для уведомления об обновлении Play Market

    val domainKorsinaInterface by lazy(LazyThreadSafetyMode.NONE) { KorsinaRepository(
        KorsinaImp(this@MainActivity)
    ) }

    private val workWithKorzinaUseCase by lazy(LazyThreadSafetyMode.NONE) { WorkWithKorzinaUseCase(domainKorsinaInterface) }
    val domainGreetingInterface by lazy(LazyThreadSafetyMode.NONE) { GreetingTextRepository(
        GreetingTextImp(this@MainActivity)
    ) }
    private val getGreetingTextUseCase by lazy(LazyThreadSafetyMode.NONE) { GetGreetingTextUseCase(domainGreetingInterface) }
    private val saveGreetingTextUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveGreetingTextUseCase(domainGreetingInterface) }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var token:String=""
    private lateinit var googleSiteTokenViewModel: GoogleSiteTokenViewModel
    private val tokenViewModel: TokenViewModel by viewModels()
    private lateinit var vm: MainActivityViewModel
    var finishKorsinsList = ArrayList<ItemKorsina>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Tea_NoActionBar)
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext())

        googleSiteTokenViewModel= ViewModelProvider(this, GoogleSiteTokenViewModelFactory()).get(GoogleSiteTokenViewModel::class.java)

        vm = ViewModelProvider(this, MainActivityFactory(googleSiteTokenViewModel, getGreetingTextUseCase, this@MainActivity, appUpdateManager)).get(MainActivityViewModel::class.java)

        googleSiteTokenViewModel.tokenLifeData.observe(this) {
            if(it!="")  {
                token = it
                sendTokenToFragments()
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            KorsinaDialogFragment.newInstance( // открываем фрагмент корзина
            ).show(supportFragmentManager, KorsinaDialogFragment.TAG)
        }
        binding.appBarMain.fab.setOnLongClickListener {
            alertDialogToAdminActivity()
            false
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getObserves()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id = item.itemId
        if(id == R.id.action_settings){
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setView(inflater.inflate(R.layout.contacts_to_developer, null))
            builder.setPositiveButton("Ok"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun sendTokenToFragments(){
        tokenViewModel.messageFromactivityToken.value = token //Отправляем токен во фрагмент
    }
    
    private fun alertDialogToAdminActivity() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.alert_dialog_parol_for_admin_activity, null))
        val parol = "gips"

        builder.setPositiveButton("Да") { dialogInterface, which ->
            var alertDialog : AlertDialog = dialogInterface as AlertDialog
            var edText : EditText = alertDialog.findViewById(R.id.ed_text_parol)
            if(edText.text.toString()==parol){
                val intent = Intent(this, ActivityForAdmin::class.java)
                startActivity(intent)
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    fun getObserves(){
        vm.greetingTextDomainModelLiveData.observe(this){
            if(it.text !="GreetingTextModel(text=Больше не показывать)") {
                val builder1 = AlertDialog.Builder(this)
                builder1.setMessage(R.string.greetingAlertDialog)
                builder1.setCancelable(true)
                builder1.setPositiveButton("Ok"){
                        dialog, id -> dialog.cancel()
                }

                builder1.setNegativeButton("ОК, больше не показывать"){
                        dialog, id -> dialog.cancel()
                    val greetingTextDomainModel = GreetingTextDomainModel(text = "Больше не показывать")
                    saveGreetingTextUseCase.execute(greetingTextDomainModel)
                }
                val alert11 = builder1.create()
                alert11.show()
                val buttonbackground: Button = alert11.getButton(DialogInterface.BUTTON_NEGATIVE)
                buttonbackground.setTextColor(Color.BLACK)
                val buttonbackground1: Button = alert11.getButton(DialogInterface.BUTTON_POSITIVE)
                buttonbackground1.setTextColor(Color.BLACK)
            }
        }
        tokenViewModel.countAddKorsinaLiveData.observe(this){
            binding.appBarMain.fabText.text = it.toString()
            binding.appBarMain.fab.backgroundTintList = getColorStateList(R.color.lightred)
            binding.appBarMain.fabText.setTextColor(getColorStateList(R.color.lightgrey))
        }
        tokenViewModel.countDelKorsinaLiveData.observe(this){
            binding.appBarMain.fabText.text = it.toString()
            if(it == 0){
                binding.appBarMain.fab.backgroundTintList = getColorStateList(R.color.lightgrey)
                binding.appBarMain.fabText.setTextColor(getColorStateList(R.color.brown))
            }
        }
    }

    fun chekKorsinaColor(){
        val korsinaDomainModel = workWithKorzinaUseCase.getBasket()
        finishKorsinsList = korsinaDomainModel.korsinaList
        binding.appBarMain.fabText.text = finishKorsinsList.size.toString()
        if(korsinaDomainModel.korsinaList.size > 0){
            binding.appBarMain.fab.backgroundTintList = getColorStateList(R.color.lightred)
            binding.appBarMain.fabText.setTextColor(getColorStateList(R.color.lightgrey))
        }
        if(korsinaDomainModel.korsinaList.size ==0){
            binding.appBarMain.fab.backgroundTintList = getColorStateList(R.color.lightgrey)
            binding.appBarMain.fabText.setTextColor(getColorStateList(R.color.brown))
        }
    }

    fun makeKorsinaColorWhite(){
        binding.appBarMain.fabText.text = "0"
        binding.appBarMain.fab.backgroundTintList = getColorStateList(R.color.lightgrey)
        binding.appBarMain.fabText.setTextColor(getColorStateList(R.color.brown))
    }

    override fun onResume() {
        super.onResume()
        chekKorsinaColor()
    }
}

