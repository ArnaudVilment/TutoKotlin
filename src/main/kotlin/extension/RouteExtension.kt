package extension

import com.google.gson.Gson
import dao.CourseDao
import dao.CourseDao.changeCourse
import dao.CourseDao.getAllCourse
import dao.CourseDao.removeAllCourse
import dao.CourseDao.removeCourseById
import extension.put
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.routing.get
import model.Course

/**
 * Builds a route to match `GET` requests with specified [path] showing specified [text]
 */
fun Route.get(path: String, text: String) = this.get(path){ call.respondText { text } }

/**
 * Builds a route to match `GET` requests with specified [path] showing specified [model] as a Json
 */
fun Route.get(path: String, model: Any){
    this.get(path){ call.respondText ( Gson().toJson(model), ContentType.Application.Json) }
}

/**
 * Builds a route to match `GET` requests with specified [path] and specified param [param] showing specified [getModel] as a Json
 */
fun Route.get(path: String, param: String, getModel: (Int) -> Any?){
    this.get(path){
        val courseId = call.parameters[param]!!.toInt()
        val model = getModel(courseId)
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}

/**
 * Builds a route to match `GET` requests with specified [path] and specified param [param] showing specified [getModel] as a Json
 */
fun Route.get(path: String, getAllCourse: (Any) -> Any?){
    this.get(path){
        val model = getAllCourse()
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}

fun Route.post(path: String) {
    this.post(path) {
        val course = call.receive<String>()
        println("Received Post Request: $course")
        CourseDao.addCourse(Gson().fromJson(course, Course::class.java))
        val model = getAllCourse()
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}

fun Route.delete(path: String, param: String) {
    this.delete(path) {
        val courseId = call.parameters[param]!!.toInt()
        removeCourseById(courseId)
        val model = getAllCourse()
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}

fun Route.delete(path: String) {
    this.delete(path) {
        removeAllCourse()
        val model = getAllCourse()
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}

fun Route.put(path: String, param: String) {
    this.put(path) {
        val courseId = call.parameters[param]!!.toInt()
        val course = call.receive<String>()
        changeCourse(courseId, Gson().fromJson(course, Course::class.java))
        val model = getAllCourse()
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}