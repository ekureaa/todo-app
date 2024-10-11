package controllers

import javax.inject._
import play.api.mvc._

import model.ViewValueHome
import lib.model.Todo
import lib.persistence.TodoRepository
import lib.model.Category
import lib.persistence.CategoryRepository
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TodoController @Inject()(
  val controllerComponents: ControllerComponents,
  todoRepository:           TodoRepository,
  categoryRepository:       CategoryRepository
  ) extends BaseController {

  def list() = Action.async { implicit req =>
    val vv = ViewValueHome(
      title  = "Todo一覧",
      cssSrc = Seq("main.css"),
      jsSrc  = Seq("main.js")
    )

    for {
      todoFuture     <- todoRepository.getTodoAll()
      categoryFuture <- categoryRepository.getCategoryAll()
    } yield {
      Ok(views.html.todo.list(vv, todoFuture, categoryFuture))
    }

  }
}