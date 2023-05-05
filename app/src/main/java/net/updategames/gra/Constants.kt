package net.updategames.gra

import net.updategames.gra.custom.CustomContainer2

class Constants {

    companion object {
        private fun getDecryptedValue(encryptedValue: String): String {
            return CustomContainer2.SimpleXorCipher.decrypt(encryptedValue)
        }

        val APP_DEV_KEY: String
            get() = getDecryptedValue("\u000c+t\b\u001B\u0007\u0003!3\u0017\u0012z\u0007&'\u00121p*-\u000E\u0006")
        val CAMPAIGN: String
            get() = getDecryptedValue("ccmf`okm")
        val URL1: String
            get() = getDecryptedValue("~}}|u{?n")
        val URL2: String
            get() = getDecryptedValue("n{w~y.p`|{`/")
    }
}