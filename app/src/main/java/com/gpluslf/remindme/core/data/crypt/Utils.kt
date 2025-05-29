package com.gpluslf.remindme.core.data.crypt


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import java.util.Base64


val Context.protoDataStore: DataStore<EncryptedUser> by dataStore(
    fileName = "user.json",
    serializer = UserSerializer
)

fun decodeString(str: String): ByteArray = Base64.getDecoder().decode(str)

fun encodeArray(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)
