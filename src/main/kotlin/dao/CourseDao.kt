package dao

import exception.CourseException
import model.Course
import model.Error

object CourseDao {

    fun getTopCourse() = CourseDao.getCourse(1)

    fun getCourse(courseId: Int) =
            try {
                CourseDao.get(courseId)
            } catch (e: CourseException) {
                Error(404, Constants.errorCourseNotFound)
            }

    // ---

    private fun get(id: Int) =
        when (id){
            1 -> Course(1, "How to troll a Troll ?", 5, true)
            2 -> Course(2, "Kotlin for troll", 1, true)
            3 -> Course(3, "How to not use Java ?", 3, false)
            else -> throw CourseException()
        }
}