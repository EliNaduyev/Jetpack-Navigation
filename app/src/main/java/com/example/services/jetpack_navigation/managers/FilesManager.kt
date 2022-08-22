package com.example.services.jetpack_navigation.managers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Eli Naduyev 16.8.22
 */
class FilesManager(
    private val context: Context,
    private val isPrivateData: Boolean,
    private val isExternalStorage: Boolean) {

    private val TAG = "MyFileManager"
    private val dirName = "test_logs"
    private val fileName = "test_log_file"
    private var fileLogger: File? = null
    val MAX_FILE_SIZE_IN_MB = 2

    //permission check
    private val isWriteStoragePermissionGranted
        get() = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private fun hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionType
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isPermissionsValid(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (!isPrivateData && !isWriteStoragePermissionGranted) {
                Log.e(TAG, "you don't have permissions to access External Storage")
                return false
            }
        }

        return true
    }

    init {
        fileLogger = getOrCreateFile(isPrivateData, isExternalStorage)
    }

    private fun getOrCreateFile(isPrivateData: Boolean, isExternalStorage: Boolean): File? {
        var fileLogger: File? = null

        if(isPermissionsValid()){
            val storagePath: File? =
                getPath(isPrivateData, isExternalStorage, Environment.DIRECTORY_DOWNLOADS)

            // folder creation
            val folderPath = File(storagePath, "/$dirName")
            val isFolderCreatedOrExist: Boolean
            if (!folderPath.exists()) {
                if (folderPath.mkdir()) {
                    Log.d(TAG, "Folder created - $dirName")
                    isFolderCreatedOrExist = true
                } else {
                    Log.d(TAG, "Error while creating Folder - $dirName")
                    isFolderCreatedOrExist = false
                }
            } else
                isFolderCreatedOrExist = true

            if (isFolderCreatedOrExist) {
                // file creation
                val filePath = File(folderPath, "/$fileName.txt")

                if (!filePath.exists()) {
                    try {
                        if (filePath.createNewFile()) {
                            Log.d(TAG, "Creating new file - $fileName")
                            fileLogger = filePath
                        } else {
                            Log.d(TAG, "Error while creating file")
                            fileLogger = null
                        }
                    } catch (e: IOException) {
                        Log.d(TAG, "Exception while creating file: $e")
                        fileLogger = null
                    }
                } else {
                    Log.d(TAG, "File already created")
                    fileLogger = filePath
                }
            }
        }

        Log.d(TAG, "getOrCreateFile: file path is: $fileLogger")
        return fileLogger
    }


    fun makeLog(tag: String, logContent: String, logLevel: LogLevel): Boolean {
        Log.d(tag, "logContent: $logContent")

        if(isPermissionsValid()){

            if(fileLogger == null){
                Log.e(TAG, "file path is null, creating new file")
                fileLogger = getOrCreateFile(isPrivateData, isExternalStorage)
                return false
            }

            if(isDeleteFileRequired()){ // Resetting file
                Log.d(TAG, "File has reached its maximum size")
                if(fileLogger!!.delete()){
                    Log.d(TAG, "Deletion of the file is succeeded!")
                    fileLogger = getOrCreateFile(isPrivateData, isExternalStorage)
                }
                else
                    Log.e(TAG, "Deletion of the file is FAILED!")
            }

            try {
                saveDataViaBufferWriter(tag, logContent, logLevel)
                Log.d(TAG, "log added successfully")
                return true
            } catch (e: Exception) {
                Log.e(TAG, "makeLog error - ${e.message}")
                return false
            }
        }
        else {
            Log.e(TAG, "makeLog: NoPermission")
            return false
        }
    }


    private fun isDeleteFileRequired(): Boolean {
        fileLogger?.let {
            val fileSizeInBytes = it.length()
            val fileSizeInKB = fileSizeInBytes.div(1024f)
            if(fileSizeInKB > 0){
                val fileSizeInMB = fileSizeInKB.div(1024f)
                Log.d(TAG, "fileSizeInMB: $fileSizeInMB, MAX_FILE_SIZE_IN_MB: $MAX_FILE_SIZE_IN_MB")
                return fileSizeInMB > MAX_FILE_SIZE_IN_MB
            }
        }


        return false
    }

    private fun saveDataViaBufferWriter(tag: String, logContent: String, logLevel: LogLevel) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
        val date = dateFormat.format(Calendar.getInstance().time)

        val buf = BufferedWriter(FileWriter(fileLogger, true))

        buf.append("-------------")
        buf.newLine()
        buf.append("tag: $tag")
        buf.newLine()
        buf.append("importance: $logLevel")
        buf.newLine()
        buf.append("date: $date")
        buf.newLine()
        buf.append("body: $logContent")
        buf.newLine()
        buf.append("-------------")
        buf.newLine()
        buf.newLine()
        buf.close()
    }

    private fun getPath(
        isPrivateData: Boolean,
        isExternalStorage: Boolean,
        envFolder: String? = null
    ): File? {
        return when {
            isPrivateData && isExternalStorage -> context.getExternalFilesDir(null)

            !isPrivateData && isExternalStorage -> {
                return if (envFolder == null)
                    Environment.getExternalStorageDirectory() // root folder, This returns null on Android 11
                else
                    Environment.getExternalStoragePublicDirectory(envFolder) //envFolder - from type = Environment.DIRECTORY_DOWNLOADS
            }

            isPrivateData && !isExternalStorage -> context.filesDir

            else -> {
                Log.e(TAG, "getPath: no such path")
                null
            }
        }
    }

    fun deleteFile(){
        fileLogger?.delete()
    }


    fun readFileFromStorage(): String {
        return try {
            val fileContent = fileLogger?.readText() ?: "No File"
            fileContent
        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")
            "Error occurred while reading from file"
        }
    }

    // this function not working something with the path not
    // work, need to check later
    fun readFileFromStorage(
        dirName: String = this.dirName,
        fileName: String = this.fileName,
        isPrivateData: Boolean = this.isPrivateData,
        isExternalStorage: Boolean = this.isExternalStorage
    ): String {
        return try {
            val basePath = getPath(isPrivateData, isExternalStorage)
            val filePath = "${basePath}/$dirName/$fileName"
            Log.d(TAG, "Full path: $filePath")
            val file = File(filePath)
            val fileContent = file.readText()
            Log.d(TAG, "file content: $fileContent")
            fileContent
        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")
            "Error occurred while reading from file"
        }
    }

    //NOT work - solution check send multiply
    fun shareViaEmail() {
        try {
            fileLogger?.let {
                val fileLocation: String = it.absolutePath.toString()
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.type = "text/plain"
                val message = "File to be shared is ${it.name}."
                intent.putExtra(Intent.EXTRA_SUBJECT, "Logs Debug file")
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$fileLocation"))
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.data = Uri.parse("mailto:")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, intent, null)
            } ?: run {
                Log.e(TAG, "shareViaEmail: File Not Exist")
            }
        } catch (e: Exception) {
            Log.e(TAG, "exception while sharing File via Email $e")
        }
    }

    /**
     * Need to check if this function is work, i delete the authority maybe its work with out this
     */
    fun shareViaEmail2(){
        Log.d(TAG, "sendMail")
        fileLogger?.let {
            val message = "File to be shared is ${it.name}."
            val subject = "Logs File"
            val authority = ""

            val path = FileProvider.getUriForFile(context, authority, it)
            Log.d(TAG, "sendMail: take file from path: $path")
            val intent = Intent()
            intent.action = Intent.ACTION_SEND_MULTIPLE
            intent.type = "message/rfc822"
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayListOf(path))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            context.startActivity(Intent.createChooser(intent, "send"))
        }
    }

    companion object {
        enum class LogLevel {
            Low,
            Medium,
            High
        }
    }

    fun printStorageInfo() {
        Log.d(TAG, "sdkVersion: ${Build.VERSION.SDK_INT}")
        /**
         * Files meant for your app's use only(Private) - Internal storage that not enforce permissions
         * and for this path the system will take care of deleting the files
         * if the application is uninstalled.
         *
         * files can found in:
         * Device Explorer -> data -> data -> package name
         * Note: In the same path i can find SHARED PREFERENCES file with values.
         */
        Log.d(TAG, "filesDir: ${context.filesDir}")
        /**
         * Files meant for your app's use only(Private) - External storage that not enforce permissions
         * and for this path the system will take care of deleting the files
         * if the application is uninstalled.
         *
         * files can found in:
         * Device Explorer -> sdcard -> android -> data -> package name
         */
        Log.d(TAG, "getExternalFilesDir: ${context.getExternalFilesDir(null)}")

        /**
         * Files saved in shared storage(Public) and needed permissions
         * not deleted if the app is uninstalled
         * Device Explorer -> emulated -> 0
         */
        Log.d(TAG, "getExternalStorageDirectory: ${Environment.getExternalStorageDirectory()}")
    }
}


/**
 * More info about STORAGE
 * Internal storage: It should say 'private storage' because it belongs to the app and cannot
 * be shared. Where it's saved is based on where the app installed If the app was installed
 * on an SD card (I mean the external storage card you put more into a cell phone
 * for more space to store images, videos, ...), your file will belong to the app means
 * your file will be in an SD card. And if the app was installed on an Internal card
 * (I mean the onboard storage card coming with your cell phone),your file will be in an Internal card.
 * External storage: It should say 'public storage' because it can be shared.
 * And this mode divides into 2 groups: private external storage and public external storage.
 * Basically, they are nearly the same, you can consult more from this site:
 * https://developer.android.com/training/data-storage/files
 * A real SD card (I mean the external storage card you put more into a cell phone
 * for more space to store images, videos, ...): this was not stated clearly
 * on Android docs, so many people might be confused with how to save files in this card.
 * Here is the link to source code for cases I mentioned above:
 * https://github.com/mttdat/utils/blob/master/utils/src/main/java/mttdat/utils/FileUtils.java
 **/