package com.jonathanlee.nfc

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

class NfcTileToggle : TileService() {
    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        val permissionStatus = NfcToggleHelper.isPermissionGiven(this)
        if (permissionStatus) {
            val currentState = NfcToggleHelper.checkNfcState(this)
            if (currentState) {
                NfcToggleHelper.setNfc(context = this, state = false)
                Toast.makeText(applicationContext, "NFC Disabled", Toast.LENGTH_SHORT).show()
                qsTile.state = Tile.STATE_INACTIVE
            } else {
                NfcToggleHelper.setNfc(context = this, state = true)
                Toast.makeText(applicationContext, "NFC Enabled", Toast.LENGTH_SHORT).show()
                qsTile.state = Tile.STATE_ACTIVE
            }
        } else {
            Toast.makeText(applicationContext, "Permission not given", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTile() {
        val currentState = NfcToggleHelper.checkNfcState(this)
        if (currentState) {
            if (qsTile.state == Tile.STATE_ACTIVE) {
                qsTile.state = Tile.STATE_INACTIVE
            }
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
    }
}