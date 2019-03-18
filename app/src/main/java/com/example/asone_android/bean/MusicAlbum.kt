package com.example.asone_android.bean

class MusicAlbum {
    var imgUrl = ""
    var musicList = mutableListOf<Music>()
    var title = ""

    fun getListIds():String{
        var sb = ""
        for (music in musicList){
            sb = sb + music.musicId+"~"
        }
        return sb
    }
}