package com.gpluslf.remindme.core.data.crypt

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<EncryptedUser> {
    private val cryptoData = CryptoData()

    override val defaultValue: EncryptedUser
        get() = EncryptedUser()

    override suspend fun readFrom(input: InputStream): EncryptedUser {
        val bytes = input.readBytes().decodeToString()
        val decodeStr = cryptoData.decrypt(decodeString(bytes))?.decodeToString()
        println(decodeStr)
        val res = Gson().fromJson(
            decodeStr, EncryptedUser::class.java
        )
        println(res)
        return res
    }


    override suspend fun writeTo(t: EncryptedUser, output: OutputStream) {
        val json = Gson().toJson(t)
        println(json)
        val bytes = json.toByteArray()
        val str = encodeArray(cryptoData.encrypt(bytes))
        withContext(Dispatchers.IO) {
            output.write(str.toByteArray())
        }
    }
}