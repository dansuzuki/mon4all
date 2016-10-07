package mon4all.db

import java.sql.{Connection, ResultSet, SQLException, Statement}

object Helpers {

  def getCurrentTimeStamp = {
		val today = new java.util.Date()
		new java.sql.Timestamp(today.getTime())
	}

  def printSQLException(e: SQLException) {
    val msg: String =
      "SQL State: " + e.getSQLState + "\n" +
      "Message: " + e.getMessage + "\n\n"
    println(msg)
  }

  def executeSQL(sql: String, stmt: Statement, ignore: Set[String] = Set()) {
    try stmt.execute(sql)
    catch {
      case e: SQLException =>
        if(ignore(e.getSQLState())) println("SQLException ignored")
        else printSQLException(e)
    }
  }

  /**
   * ignore if table already exists, exception is X0Y32
   * reference: http://stackoverflow.com/questions/5866154/how-to-create-table-if-it-doesnt-exist-using-derby-db
   * list at http://db.apache.org/derby/docs/10.8/ref/rrefexcept71493.html
   */
  def createTables(sql: String*)(implicit conn: Connection) {
    sql foreach(s => executeSQL(s, conn.createStatement, Set("X0Y32")))
  }

  def dropTables(tables: String*)(implicit conn: Connection) {
    tables foreach(table => executeSQL("DROP TABLE " + table, conn.createStatement))
  }


  trait ResultSetToList {
    def toList[T](retrieve: ResultSet => T): List[T]
  }
  implicit def wrapResultSet(rs: ResultSet) = new ResultSetToList {
    def toList[T](retrieve: ResultSet => T): List[T] =
      new Iterator[T] {
        def hasNext = rs.next()
        def next() = retrieve(rs)
      }
      .toList
  }

}
