data class CourseModal     // creating constructor for our variables.
    (// creating getter and setter methods.
    // variables for our course
    // name and description.
    val photo: String,
    val basicInfo: ArrayList<String>,
    val tableInfo: ArrayList<String>

) {
    fun getPhoto(): Any?{
        return photo
    }

    @JvmName("getBasicInfo1")
    fun getBasicInfo(): ArrayList<String> {
        return basicInfo
    }

    @JvmName("getTableInfo1")
    fun getTableInfo(): ArrayList<String> {
        return tableInfo
    }
}