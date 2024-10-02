package lib.model

import ixias.model._
import ixias.util.EnumStatus

import java.time.LocalDateTime

// TODOを表すモデル
//~~~~~~~~~~~~~~~~~~~~
case class Todo(
  id:        Option[Todo.Id],
  title:     String,
  status:    Todo.Status,
  category:  Todo.Category,
  content:   String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Todo.Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object Todo {

  val  Id = the[Identity[Id]]
  type Id = Long @@ Todo
  type WithNoId = Entity.WithNoId [Id, Todo]
  type EmbeddedId = Entity.EmbeddedId[Id, Todo]

  sealed abstract class Status(val code: Short, val name: String) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object TODO       extends Status(code = 0,   name = "TODO")
    case object INPROGRESS extends Status(code = 1, name = "進行中")
    case object COMPRETED  extends Status(code = 2, name = "完了")
  }

  sealed abstract class Category(val code: Short, val name: String) extends EnumStatus
  object Category extends EnumStatus.Of[Category] {
    case object FRONTEND       extends Category(code = 1, name = "フロントエンド")
    case object BACKEND        extends Category(code = 2, name = "バックエンド")
    case object INFRASTRUCTURE extends Category(code = 3, name = "インフラ")
  }
}
