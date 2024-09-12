package lib.persistence.db

import java.time.LocalDateTime
import ixias.slick.jdbc.MySQLProfile.api._
import ixias.slick.builder._
import ixias.util._ 
import lib.model.Todo

import java.time

// TodoTable: Todoテーブルへのマッピングを行う
//~~~~~~~~~~~~~~
case class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {
    // Columns
    /* @1 */ def id          = column[Todo.Id]       ("id",          UInt64, O.PrimaryKey, O.AutoInc)
    /* @3 */ def title       = column[String]        ("title", Utf8Char255)
    /* @5 */ def state       = column[Todo.Status]   ("state", UInt8)
    /* @2 */ def categoryId  = column[Long]          ("category_id", UInt64)
    /* @4 */ def body        = column[String]        ("body", Text)
    /* @6 */ def updatedAt   = column[LocalDateTime] ("updated_at",  TsCurrent)
    /* @7 */ def createdAt   = column[LocalDateTime] ("created_at",  Ts)

    // DB <=> Scala の相互のmapping定義
    def * = (id.?, title, state, categoryId, body, updatedAt, createdAt).<> (
      (Todo.apply _).tupled,
      (Todo.unapply _).andThen(_.map(_.copy(
        _6 = LocalDateTime.now()
      )))
    )
}