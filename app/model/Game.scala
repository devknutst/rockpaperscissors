package model

import model.Decision.Decision
import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}


object Decision extends Enumeration {
  type Decision = Value
  val Paper, Scissor, Stone = Value


  implicit val enumFormat = new Format[Decision] {

    override def writes(o: Decision) = JsString(o.toString)

    override def reads(json: JsValue) = JsSuccess(Decision.withName(json.as[String]))
  }
}


case class Score(human: Int, computer: Int, roundsMade: Int, roundsToGo: Int, decision: Decision, msg: String)


object Generator {

  def randomTurn = Decision(scala.util.Random.nextInt(3))

}

