package lib.model

import ixias.model._
import ixias.util.EnumStatus

import java.time.LocalDateTime

// TODOを表すモデル
//~~~~~~~~~~~~~~~~~~~~
case class Category(
  id:        Option[Category.Id],
  name:      String,
  slug:      String,
  color:     Category.Color,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Todo.Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object Category {

  val  Id = the[Identity[Id]]
  type Id = Long @@ Todo
  type WithNoId = Entity.WithNoId [Id, Category]
  type EmbeddedId = Entity.EmbeddedId[Id, Category]

  sealed abstract class Color(val code: Short) extends EnumStatus
  object Color extends EnumStatus.Of[Color] {
    case object RED       extends Color(code = 1)
    case object BLUE      extends Color(code = 2)
    case object YELLOW    extends Color(code = 3)
  }
}
