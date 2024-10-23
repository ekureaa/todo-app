package lib.model

import ixias.model._
import ixias.util.EnumStatus

import java.time.LocalDateTime
import play.mvc.With

// Categoryを表すモデル
//~~~~~~~~~~~~~~~~~~~~
case class Category(
  id:        Option[Category.Id],
  name:      String,
  slug:      String,
  color:     Category.Color,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Category.Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object Category {

  val  Id = the[Identity[Id]]
  type Id = Long @@ Category
  type WithNoId = Entity.WithNoId [Id, Category]
  type EmbeddedId = Entity.EmbeddedId[Id, Category]

  sealed abstract class Color(val code: Short, val colorCode: String) extends EnumStatus
  object Color extends EnumStatus.Of[Color] {
    case object RED   extends Color(code = 1, colorCode="#ff0000")
    case object GREEN extends Color(code = 2, colorCode="#00ff00")
    case object BLUE  extends Color(code = 3, colorCode="#0000ff")
  }
}
