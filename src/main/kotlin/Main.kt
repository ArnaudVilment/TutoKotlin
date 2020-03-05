import dao.CourseDao
import extension.get
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = configureWebServer()

// --- CONFIGURATION ---

private fun configureWebServer(){
    val server = embeddedServer(Netty, port = 8081) {
        routing {
            get("/", Constants.welcomeMessage)
            get("/course/top", CourseDao.getTopCourse())
            get("/course/{id}", "id") { id ->  CourseDao.getCourse(id) }
        }
    }.start(wait = true)
}



