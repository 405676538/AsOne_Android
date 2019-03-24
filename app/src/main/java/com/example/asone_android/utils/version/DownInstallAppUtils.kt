package com.example.asone_android.utils.version

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.webkit.MimeTypeMap
import com.example.asone_android.app.Constant
import com.example.asone_android.utils.ACache
import com.example.asone_android.utils.FileUtils
import com.example.asone_android.utils.version.NotificationUtils.NOTIFICATION_ID_UPDATE
import java.io.File

/**
 * @author sonia
 * @date 2019/1/30
 */
class DownInstallAppUtils {
    companion object {
        //启动安装
        fun openFile(mContext: Context, path: String): Intent{
            val file = File(path)
            val downloadFileUri = getUriFromFile(mContext, file)
            var intent = Intent()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //避免重复打开
                mContext.startActivity(intent)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = Intent(Intent.ACTION_VIEW)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive")
                mContext.startActivity(intent)
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.action = Intent.ACTION_VIEW
                val mimeType = getMIMEType(file)
                intent.setDataAndType(downloadFileUri, mimeType)
                try {
                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return intent
        }

        private fun getMIMEType(file: File): String? {
            val name = file.name
            val fileName = name.substring(name.lastIndexOf("") + 1, name.length).toLowerCase()
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileName)
        }

        private fun getUriFromFile(mContext: Context, file: File): Uri {
            val uri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(mContext, mContext.packageName + ".FileProvider", file)
            } else {
                uri = Uri.fromFile(file)
            }
            return uri
        }

        lateinit var installDialog: VersionUpdataHelper.CustomDialog
        fun showInstallDialog(mContext: Context,filePath: String) {
            val builder = VersionUpdataHelper.CustomDialog.Builder(mContext)
            builder.setMessage("检测到新版本，是否安装？")
            builder.setPositiveButton("是") { dialog, _ ->
                openFile(mContext, filePath)
                dialog.dismiss()
                ACache.get().put(Constant.VERSION_STATUS, -1)
            }
            builder.setNegativeButton("否") { dialog, _ ->
                NotificationUtils.getInstance(mContext).cancel(NOTIFICATION_ID_UPDATE)
                dialog.dismiss()
                FileUtils.deleteFile(filePath)
                ACache.get().put(Constant.VERSION_STATUS, -1)
            }
            installDialog = builder.create()
            installDialog.setCancelable(false)
            if (!installDialog.isShowing) {
                installDialog.show()
            }
        }
    }

}