package lib.persistence

import com.zaxxer.hikari.HikariDataSource

import scala.concurrent.{ExecutionContext, Future}
import ixias.model._
import ixias.slick.SlickRepository
import ixias.slick.builder.{DatabaseBuilder, HikariConfigBuilder}
import ixias.slick.jdbc.MySQLProfile.api._
import ixias.slick.model.DataSourceName
import lib.model.Todo
import lib.model.Todo.Id
import lib.persistence.db.TodoTable
import slick.dbio.Effect
import slick.sql.FixedSqlAction

// UserRepository: UserTableへのクエリ発行を行うRepository層の定義
//~~~~~~~~~~~~~~~~~~~~~~
class TodoRepository()(implicit val ec: ExecutionContext) extends SlickRepository[Todo.Id, Todo] {
  val master: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://master/to_do")).build())
  )
  val slave: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://slave/to_do")).build())
  )

  val todoTable = TableQuery[TodoTable]

  /**
    * Get User Data
    */
  def getById(id: Todo.Id): Future[Option[Todo]] = {
    slave.run(todoTable.filter(_.id === id).result.headOption)
  }

  def getTodoAll(): Future[Option[Todo]] = {
    slave.run(todoTable.result.headOption)
  }

}