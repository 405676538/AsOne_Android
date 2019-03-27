package com.example.asone_android.bean

class MusicAlbum {
    var imgUrl = ""
    var musicList = mutableListOf<Music>()
    var title = ""
    var albumId = ""

    fun getListIds():String{
        var sb = ""
        if (musicList.isEmpty()){
            return ""
        }
        for (position in musicList.indices){
            if (position == musicList.size - 1){
                sb += musicList[position].musicId
            }else{
                sb = sb + musicList[position].musicId+"~"
            }
        }
        return sb
    }
}