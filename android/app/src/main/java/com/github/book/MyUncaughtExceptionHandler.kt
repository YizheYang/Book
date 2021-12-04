package com.github.book

import android.content.Context
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class MyUncaughtExceptionHandler(private val mContext: Context) : Thread.UncaughtExceptionHandler {
    private val path = (ContextCompat.getExternalFilesDirs(mContext.applicationContext, null)[0].absolutePath
            + "/crashLog")

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("程序出现异常了", "Thread = ${t.name}Throwable = ${e.message}".trimIndent())
        val stackTraceInfo = getStackTraceInfo(e)
        Log.e("stackTraceInfo", stackTraceInfo)
        saveThrowableMessage(stackTraceInfo)
        Toast.makeText(mContext, "抱歉，我们发生了错误，需要退出应用", Toast.LENGTH_LONG).show()
        Process.killProcess(Process.myPid())
        exitProcess(1)
    }

    /**
     * 获取错误的信息
     * @param throwable 抛出的异常信息
     * @return 异常信息的字符串
     */
    private fun getStackTraceInfo(throwable: Throwable): String {
        var pw: PrintWriter? = null
        val writer: Writer = StringWriter()
        try {
            pw = PrintWriter(writer)
            throwable.printStackTrace(pw)
        } catch (e: Exception) {
            return ""
        } finally {
            pw?.close()
        }
        return writer.toString()
    }

    /**
     * 保存异常信息
     * @param errorMessage 接收的异常信息的内容
     */
    private fun saveThrowableMessage(errorMessage: String) {
        if (errorMessage.isEmpty()) {
            return
        }
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        writeStringToFile(errorMessage, file)
    }

    /**
     * 将字符串写入本地
     * @param errorMessage 要写入的字符串
     * @param file 写入的文件位置
     */
    private fun writeStringToFile(errorMessage: String, file: File) {
        Thread {
            var outputStream: FileOutputStream? = null
            try {
                val inputStream = ByteArrayInputStream(errorMessage.toByteArray())
                outputStream = FileOutputStream(
                    File(
                        file,
                        SimpleDateFormat("yyyyMMddHHmmSS").format(Date(System.currentTimeMillis())) + ".txt"
                    )
                )
                var len = 0
                val bytes = ByteArray(1024)
                while (inputStream.read(bytes).also { len = it } != -1) {
                    outputStream.write(bytes, 0, len)
                }
                outputStream.flush()
                Log.e("程序出异常了", "写入本地文件成功：" + file.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }
}