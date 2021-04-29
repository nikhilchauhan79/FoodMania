package com.example.foodrecipes.model


data class ResponseRec(val offset:Int?,val number:Int?,val results: Results?)

data class Results(val food: List<Food>?)

data class Food(val calories:String?,val carbs:String? ,val fat:String?,val image:String?,val imageType:String?,val protein:String?,val title:String?)

data class Nutrition(val nutrients:List<Nutrients>)

data class Nutrients(val title:String?,val amount:Float?,val unit:String?)