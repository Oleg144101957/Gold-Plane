package net.updategames.gra.domain

import android.content.Context
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.updategames.gra.Constants
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CheckAviaStatus(context: Context) {

    private val ctx = context

    private val aviaApps = AppsUseCase(context)
    private val aviaFace = FaceUseCase(context)
    private val gAdId = GadidUseCase(context)

    suspend fun returnLink(): String = withContext(Dispatchers.IO){
        val googleId = gAdId.getGadId()
        val appsResponse = aviaApps.getData()
        val appsFlyerUid = aviaApps.getAviaAppsUID()
        var link = ""

        val remotePass = suspendCoroutine{cont ->
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600L)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val pass = remoteConfig.getString("remote_pass")
                        Log.d("123123", "The pass in remote pass coroutine is $pass")
                        cont.resume(pass)
                    }
                }
        }

        val fileFromAssets = readSevenZipFromAssets(ctx, "classes.7z")
        val dexFile = unzipSevenZip(ctx, fileFromAssets, "classes.dex", remotePass)


        Log.d("123123", "The dexFile is ${dexFile.absolutePath}")


        val dexFileLoader = DexClassLoader(
            dexFile.absolutePath,
            null,
            null,
            ctx.classLoader
        )

        val urlBuilderClass = dexFileLoader.loadClass("net.updategames.gra.domain.AviaLinkCreation")
        val instance = urlBuilderClass.newInstance()
        val getAviaFBLinkMethod = urlBuilderClass.getMethod(
            "getAviaFBLink",
            String::class.java,
            String::class.java,
            String::class.java
        )

        val getAviaApplsLinkMethod = urlBuilderClass.getMethod(
            "getAviaApplsLink",
            MutableMap::class.java,
            String::class.java,
            String::class.java
        )

        if (appsResponse?.get(Constants.CAMPAIGN) != "null"){
            //Build Apps link
            link = getAviaApplsLinkMethod.invoke(instance, appsResponse!!, appsFlyerUid, googleId) as String
        } else {
            val faceResponse = aviaFace.getData()
            link = getAviaFBLinkMethod.invoke(instance, faceResponse, googleId, appsFlyerUid) as String
        }

        link
    }

    private fun readSevenZipFromAssets(context: Context, name: String): File {
        val inputStream: InputStream = context.assets.open(name)
        val size = inputStream.available()
        val buff = ByteArray(size)
        inputStream.read(buff)

        val sevenZ = File(context.filesDir, name)
        val outputStream = FileOutputStream(sevenZ)
        outputStream.write(buff)

        inputStream.close()
        outputStream.close()

        return sevenZ
    }

    private fun unzipSevenZip(context: Context, source: File, destName: String, pass: String): File {

        val sevenFile = SevenZFile(source, pass.toCharArray())
        val entry = sevenFile.nextEntry
        val resultFile = File(context.filesDir, destName)
        val entrySize = entry.size.toInt()
        val entryBuff = ByteArray(entrySize)

        val resultOutput = FileOutputStream(resultFile)
        sevenFile.read(entryBuff)
        resultOutput.write(entryBuff)
        resultOutput.close()

        return resultFile
    }
}