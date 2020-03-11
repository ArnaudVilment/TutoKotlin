package dao

import exception.CourseException
import model.Course
import model.Error
import java.util.concurrent.atomic.AtomicInteger

object CourseDao {

    private val idCounter = AtomicInteger()

    private val courses = mutableListOf<Course>()

    fun addCourse(course: Course) {
        if(courses.contains(course)) {
            Error(404, Constants.errorCourseNotFound)
        } else {
            course.id = idCounter.incrementAndGet() - 1
            courses.add(course)
        }
    }

    fun changeCourse(courseId: Int, course: Course) {
        try {
            if (get(courseId) != null) {
                course.id = courseId
                courses[course.id!!] = course
            }

        } catch (e: CourseException) {
            Error(404, Constants.errorCourseNotFound)
        }
    }

    fun getTopCourse() = courses.maxBy { it.level }

    fun getAllCourse() = courses

    fun getCourse(courseId: Int) =
            try {
                get(courseId)
            } catch (e: CourseException) {
                Error(404, Constants.errorCourseNotFound)
            }

    fun removeCourse(course: Course) {
        if (!courses.contains(course)) {
            Error(404, Constants.errorCourseNotFound)
        }
        courses.remove(course)
    }

    fun removeCourseById(courseId: Int) {
        try {
            get(courseId)
            courses.remove(get(courseId))
        } catch (e: CourseException) {
            Error(404, Constants.errorCourseNotFound)
        }
    }

    fun removeAllCourse() = courses.clear()

    private fun get(id: Int) =
        if(!courses.none { it.id == id }) {
            courses.find { it.id == id }
        } else throw CourseException()
}
