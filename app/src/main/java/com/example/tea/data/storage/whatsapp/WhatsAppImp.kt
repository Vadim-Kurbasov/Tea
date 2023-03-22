package com.example.tea.data.storage.whatsapp

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.tea.data.storage.WhatsAppInterface
import com.example.tea.data.models.WhatsAppModel
import com.example.tea.presentation.ui.korsinadialog.KorsinaDialogFragment

class WhatsAppImp(val fragment: KorsinaDialogFragment,val context: Context): WhatsAppInterface {

    override fun sendWhatsApp(whatsAppModel: WhatsAppModel){

        val smsNumber = "79509062011" // E164 format without '+' sign
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${whatsAppModel.strToSend}")
        sendIntent.putExtra(
            "jid",
            "$smsNumber@s.whatsapp.net"
        ) //phone number without "+" prefix

        sendIntent.setPackage("com.whatsapp")
        if (sendIntent.resolveActivity(fragment.requireActivity().packageManager) == null) {
            Toast.makeText(context, "Установите WhatsApp!", Toast.LENGTH_LONG).show()
        }
        else {
            fragment.startActivity(sendIntent)
        }
    }
}