package uz.khan.portfolioadmin

/**
 * @Author: Temur
 * @Date: 01/08/2022
 */


fun interface OnClick<T> {
    fun onClick(data: T?)
}

fun ArrayList<String>.asString(): String {
    var formatted = ""
    forEachIndexed { index, s ->
        s
        if (index != this.size - 1) formatted += "$s$IMAGE_REGEX"
        else formatted += "$s"
    }
    return formatted
}

fun String.toImageList(): List<String> {
    return split(IMAGE_REGEX)
}

const val IMAGE_REGEX = "[TEMUR_ABBOS]"