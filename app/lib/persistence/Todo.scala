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
import javax.inject._

// TodoRepository: TodoTableへのクエリ発行を行うRepository層の定義
//~~~~~~~~~~~~~~~~~~~~~~
class TodoRepository @Inject()(implicit val ec: ExecutionContext) extends SlickRepository[Todo.Id, Todo] {
  val master: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://master/to_do")).build())
  )
  val slave: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://slave/to_do")).build())
  )

  val todoTable = TableQuery[TodoTable]

  /**
    * Get Todo Data by Id
    */
  def getById(id: Todo.Id): Future[Option[Todo#EmbeddedId]] = {
    slave.run(todoTable.filter(_.id === id).result.headOption).map(_.map(_.toEmbeddedId))
  }

  /**
    * Get All Todo Data
    */
  def getTodoAll(): Future[Seq[Todo#EmbeddedId]] = {
    slave.run(todoTable.result).map(_.map(_.toEmbeddedId))
  }

}