package ru.yangel.hackathon.login.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenLocalStorage(context: Context) {
    private val masterKey: MasterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private companion object {
        const val TOKEN_REFERENCES = "secret_shared_prefs"
    }

    private val encryptedSharedPreferences =
        EncryptedSharedPreferences.create(
            context, TOKEN_REFERENCES, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun deleteToken() {
        encryptedSharedPreferences.edit().remove("token").apply()
    }

    fun getToken(): String {
        return encryptedSharedPreferences.getString("token", "")
            ?: ""
    }

    fun saveToken(token: String) {
        encryptedSharedPreferences.edit().putString("token", token).apply()
    }

}