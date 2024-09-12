package controllers

import javax.inject._
import play.api.mvc._

import model.ViewValueHome
import lib.model.Todo
import lib.persistence.TodoRepository

@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def list() = Action { implicit req =>
    val vv = ViewValueHome(
      title  = "Todo一覧",
      cssSrc = Seq("main.css"),
      jsSrc  = Seq("main.js")
    )

    val todoRepository = new TodoRepository()

    Ok(views.html.todo.list(vv))
  }
}