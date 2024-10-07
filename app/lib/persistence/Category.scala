package lib.persistence

import com.zaxxer.hikari.HikariDataSource

import scala.concurrent.{ExecutionContext, Future}
import ixias.model._
import ixias.slick.SlickRepository
import ixias.slick.builder.{DatabaseBuilder, HikariConfigBuilder}
import ixias.slick.jdbc.MySQLProfile.api._
import ixias.slick.model.DataSourceName
import lib.model.Category
import lib.model.Category.Id
import lib.persistence.db.CategoryTable
import slick.dbio.Effect
import slick.sql.FixedSqlAction

// UserRepository: UserTableへのクエリ発行を行うRepository層の定義
//~~~~~~~~~~~~~~~~~~~~~~
class CategoryRepository()(implicit val ec: ExecutionContext) extends SlickRepository[Category.Id, Category] {
  val master: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://master/to_do")).build())
  )
  val slave: Database = DatabaseBuilder.fromHikariDataSource(
    new HikariDataSource(HikariConfigBuilder.default(DataSourceName("ixias.db.mysql://slave/to_do")).build())
  )

  val categoryTable = TableQuery[CategoryTable]


  def getCategoryAll(): Future[Seq[Category]] = {
    slave.run(categoryTable.result)
  }

}