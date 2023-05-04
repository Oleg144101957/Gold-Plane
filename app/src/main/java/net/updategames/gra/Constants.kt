package net.updategames.gra

import net.updategames.gra.custom.CustomContainer2

class Constants {

    companion object {
        private fun getDecryptedValue(encryptedValue: String): String {
            return CustomContainer2.SimpleXorCipher.decrypt(encryptedValue)
        }

        val APP_DEV_KEY1: String
            get() = getDecryptedValue("3xwt<SUKw}?E")
        val APP_DEV_KEY2: String
            get() = getDecryptedValue("{cd`{s`tkvC")
        val CAMPAIGN: String
            get() = getDecryptedValue("ccmf`okm")
        val URL1: String
            get() = getDecryptedValue("~}}|u{?n")
        val URL2: String
            get() = getDecryptedValue("n{w~y.p`|{`/")
    }
}