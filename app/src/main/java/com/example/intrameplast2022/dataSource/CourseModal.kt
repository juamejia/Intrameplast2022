data class CourseModal     // creating constructor for our variables.
    (// creating getter and setter methods.
    // variables for our course
    // name and description.
    val courseName: String,
    val courseDescription: String

) {
    fun getCourseName(): Any {
        return courseName
    }
    fun getCourseDescription(): Any {
        return  courseDescription
    }
}