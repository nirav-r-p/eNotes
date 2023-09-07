package com.example.notestaker.model
import com.google.gson.Gson
import com.example.notestaker.data.Note

interface Converter {
    fun getObjToString(obj:Note):String
    fun getStrToObject(str:String):Note
}

class NoteConvert:Converter{
    private val gson = Gson()
    override fun getObjToString(obj: Note): String {
        return gson.toJson(obj);
    }

    override fun getStrToObject(str: String): Note {
        return gson.fromJson(str, Note::class.java)
    }

}