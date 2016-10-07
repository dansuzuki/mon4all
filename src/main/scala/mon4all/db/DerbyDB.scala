package mon4all.db

import Helpers._
import java.sql.{Connection, DriverManager, SQLException}

abstract class DerbyDB(val connectionString: String) {

  Class.forName("org.apache.derby.jdbc.EmbeddedDriver")

  final private var connected = false

  /**
   * the connection object
   */
  lazy protected val connection: Connection = {
    var conn: Option[Connection] = None
    try {
      conn = Some(DriverManager.getConnection(connectionString))
      conn.get.setAutoCommit(false)
      connected = true
    }
    catch {
      case e: SQLException => printSQLException(e)
    }
    conn.get
  }

  /**
   * disconnect when app is shutdown
   */
  scala.sys.addShutdownHook({
    if(connected) {
      println("rolling back all transactions.")
      connection.rollback()
      println("rollbacked!")
      connection.close()
      println("connection close.")
    }
  })
}
