import com.google.gson.Gson
import dao.CourseDao
import extension.delete
import extension.get
import extension.post
import extension.put
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.request.receive
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import model.Course

fun main(args: Array<String>) = configureWebServer()

// --- CONFIGURATION ---
val gson = Gson()
const val REST_ENDPOINT = "/courses"

private fun configureWebServer(){
    val server = embeddedServer(Netty, port = 8081) {
        install(DefaultHeaders) {
        }
        install(ContentNegotiation) {
            //register((ContentType.Application.Json, gson.fromJson(String, Course::class.java))
            gson {

            }
        }
        routing {
            get("/", Constants.welcomeMessage)
            CourseDao.getTopCourse()?.let { get("$REST_ENDPOINT/top", it) }
            get("$REST_ENDPOINT/{id}", "id") { id ->  CourseDao.getCourse(id) }
            get("$REST_ENDPOINT") { CourseDao.getAllCourse() }
            post("$REST_ENDPOINT")
            delete("$REST_ENDPOINT/{id}", "id")
            delete("$REST_ENDPOINT")
            put("$REST_ENDPOINT/{id}", "id")
        }
    }.start(wait = true)
}


private operator fun Gson.invoke(unit: () -> Unit) {

}







