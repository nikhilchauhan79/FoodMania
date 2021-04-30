val empty = "name, 2012, 2017,123,456".filter { !it.isWhitespace() }.split(",")

var text = filterQuery(empty as ArrayList<String>)
println(text)

fun filterQuery(newString: ArrayList<String>):String? {
    var newStr=""
    for ((index, element) in newString.withIndex()) {
        println("$index -> $element")
        if(index==0){
            newStr+=element+","
        }else {
            if(index==newString.lastIndex){
                newStr += "+" + element
                return newStr

            }
            newStr += "+" + element+","
        }

    }
    return newStr
}

