package extension

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

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
fun Route.get(path: String, param: String, getModel : (Int) -> Any){
    this.get(path){
        val courseId = call.parameters[param]!!.toInt()
        val model = getModel(courseId)
        call.respondText ( Gson().toJson(model), ContentType.Application.Json)
    }
}