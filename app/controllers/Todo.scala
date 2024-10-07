package controllers

import javax.inject._
import play.api.mvc._

import model.ViewValueHome
import lib.model.Todo
import lib.model.Todo.Id
import lib.persistence.TodoRepository
import lib.model.Category
import lib.model.Category.Id
import lib.persistence.CategoryRepository
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.util.Success

@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def list() = Action.async { implicit req =>
    val vv = ViewValueHome(
      title  = "Todo一覧",
      cssSrc = Seq("main.css"),
      jsSrc  = Seq("main.js")
    )

    val todoRepository = new TodoRepository()
    val categoryRepository = new CategoryRepository()

    for {
      todoFuture <- todoRepository.getTodoAll()
      categoryFuture <- categoryRepository.getCategoryAll()
    } yield {
      Ok(views.html.todo.list(vv, todoFuture, categoryFuture))
    }

  }
}