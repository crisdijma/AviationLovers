package com.example.aviationlovers

class DataFile {
    var dataRoute: String? = null
    var dataCompany: String? = null
    var dataMiles: String? = null
    var dataImage: String? = null

    constructor(dataRoute: String?, dataCompany: String?, dataMiles: String?, dataImage: String?){
        this.dataRoute = dataRoute
        this.dataCompany = dataCompany
        this.dataMiles = dataMiles
        this.dataImage = dataImage
    }

    constructor(){

    }
}