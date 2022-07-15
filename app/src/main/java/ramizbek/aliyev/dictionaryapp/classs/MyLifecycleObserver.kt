package ramizbek.aliyev.dictionaryapp.classs

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

interface ImageCallBack{
    fun imageSelected(photoPath: String?)
}

class MyLifecycleObserver(private val registry: ActivityResultRegistry, val imageCallBack: ImageCallBack, val context: Context) :
    DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<Uri>
    var photoPath:String? = null

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.TakePicture()) { bool  ->
            if (bool) {
                imageCallBack.imageSelected(photoPath)
            }
        }
    }

    fun selectImage() {
        val photoURI: Uri = FileProvider.getUriForFile(
            context,
            "ramizbek.aliyev.dictionaryapp",
            createImageFile()
        )
        getContent.launch(photoURI)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            photoPath = absolutePath
        }
    }

}