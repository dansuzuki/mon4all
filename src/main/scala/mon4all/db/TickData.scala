package mon4all.db

import Helpers._

import java.sql.{DriverManager, Connection, PreparedStatement, SQLException, Statement}

import scala.collection.JavaConversions._
import scala.util.{Try}


import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

trait Json4SFormats {
  implicit val formats = DefaultFormats
}

object Item extends Json4SFormats {
  def apply(json: String): Item = parse(json).extract[Item]
}

case class Item(
  name: String,
  status: Int)

object Job extends Json4SFormats {
  def apply(json: String): Job = parse(json).extract[Job]
}
case class Job(
  name: String,
  itemList: List[Item],
  isCompleted: Boolean,
  initialStatus: Int,
  completeStatus: Int)

object TickData extends DerbyDB("jdbc:derby:.db/;create=true") {

  //type Status = Int

  implicit val conn = connection

  def newJob(job: Job): Boolean = {
    var ret: Boolean = false
    try {
      val tickJobsSelect = conn.prepareStatement(
        """
          SELECT id FROM tick_jobs WHERE name = ?
        """
        )

      tickJobsSelect.setString(1, job.name)
      val id = Try[Int](tickJobsSelect.executeQuery().toList(_.getInt(1)).head).getOrElse(-1)

      val jobId: Int = if(id < 0) {
        val tickJobsInsert = conn.prepareStatement(
          """
          INSERT INTO tick_jobs(name, is_completed, initial_status, complete_status)
          VALUES (?, ?, ?, ?)
          """, Array(1))

        tickJobsInsert.setString(1, job.name)
        tickJobsInsert.setBoolean(2, false)
        tickJobsInsert.setInt(3, job.initialStatus)
        tickJobsInsert.setInt(4, job.completeStatus)
        tickJobsInsert.execute()
        tickJobsInsert.getGeneratedKeys().toList(_.getInt(1)).head
      }
      else {
        id
      }

      val tickItemsInsert = conn.prepareStatement(
        """
        INSERT INTO tick_job_items(job_id, name, status, last_update)
        VALUES (?, ?, ?, ?)
        """)

      job.itemList.foreach(item => {
        tickItemsInsert.setInt(1, jobId)
        tickItemsInsert.setString(2, item.name)
        tickItemsInsert.setInt(3, job.initialStatus)
        tickItemsInsert.setTimestamp(4, getCurrentTimeStamp)
        tickItemsInsert.addBatch()
      })
      tickItemsInsert.executeBatch()

      conn.commit()
      ret = true
    }
    catch {
      case e: SQLException => {
        conn.rollback()
        printSQLException(e)
      }
      case e: Exception => {
        println("Non SQLException caught.")
        println(e.getMessage)
        conn.rollback()
      }
    }
    ret
  }
/*
  def getJob(jobName: String): Job = {

  }

  def getAllJobs: List[Job] = {

  }

  def updateJobItem(jobName: String, item: Item): Boolean = {

  }
*/
  def dropObjects = dropTables("tick_jobs", "tick_job_items")

  def createObjects =
    createTables(
      """
      CREATE TABLE tick_jobs
      (
        id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
        name VARCHAR(100) NOT NULL CONSTRAINT name_unique UNIQUE,
        is_completed BOOLEAN,
        initial_status INTEGER,
        complete_status INTEGER
      )
      """,
      """
      CREATE TABLE tick_job_items
      (
        id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
        job_id INT,
        name VARCHAR(100),
        status INTEGER,
        last_update TIMESTAMP
      )
      """
    )

}
