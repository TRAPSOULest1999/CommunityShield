package com.example.communityshield.model

class NewsData {
    /** Set Data **/
    var heading :String? = null
    var info :String? = null
    var img :String? = null

    constructor(){}

    constructor(
        heading:String?,
        info:String?,
        img:String?
        ){
        this.heading = heading
        this.info = info
        this.img =img
    }
}