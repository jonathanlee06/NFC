package com.jonathanlee.nfc

import android.content.Context
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.util.Log

object NfcToggleHelper {
    private var nfcAdapter: NfcAdapter? = null
    private const val TAG = "NfcToggleHelper"
    private const val WRITE_SECURE_PERMISSION = "android.permission.WRITE_SECURE_SETTINGS"

    fun checkNfcState(context: Context): Boolean {
        return NfcAdapter.getDefaultAdapter(context).isEnabled
    }

    fun setNfc(context: Context, state: Boolean): Boolean {
        var result = false
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter != null) {
            try {
                val nfcManager = Class.forName(nfcAdapter!!.javaClass.name)
                val setNfcEnabled = nfcManager.getDeclaredMethod(if (state) "enable" else "disable")
                setNfcEnabled.isAccessible = true
                result = setNfcEnabled.invoke(nfcAdapter) as Boolean
            } catch (e: Exception) {
                Log.e(TAG, Log.getStackTraceString(e))
            }
        }
        return result
    }

    fun isPermissionGiven(context: Context): Boolean {
        val permissionStatus = context.checkCallingOrSelfPermission(WRITE_SECURE_PERMISSION)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }
}