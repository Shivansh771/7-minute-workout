package com.example.a7minutewprkout

class ExerciseModel (private var id:Int, private var name:String, private var image:Int, private var isCompleted:Boolean=false, private var isSelected:Boolean=false){

    fun getId():Int{
        return id
    }
    fun setId(id:Int){
        this.id=id
    }
    fun getname():String{
        return name
    }
    fun setName(name:String){
        this.name=name

    }
    fun getImage():Int{
        return image
    }
    fun setImage(image:Int){
        this.image=image
    }

    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted=isCompleted
    }    fun getIsCompleted():Boolean{
        return isCompleted
    }
    fun setIsSelected(id:Boolean){
        this.isSelected=isSelected
}
    fun getIsSelected():Boolean{
        return isSelected
    }
    }