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

  def play(score: Score): Score = {

    val compDecision = randomTurn
    val roundsMade = score.roundsMade + 1
    val roundsToGo = score.roundsToGo - 1
    val msg = "Du:"+ score.decision + " - Computer:" + compDecision
    val win:Score = Score(score.human +1, score.computer, roundsMade, roundsToGo, compDecision, "Gewonnen! " + msg)
    val lose:Score = Score(score.human, score.computer+1, roundsMade, roundsToGo, compDecision, "Verloren! " + msg)
    val draw:Score = Score(score.human, score.computer, score.roundsMade, score.roundsToGo, compDecision, "Unentschieden! " + msg)

    (score.decision, compDecision) match {
      case (Decision.Paper, Decision.Stone) => win
      case (Decision.Scissor, Decision.Paper) => win
      case (Decision.Stone, Decision.Scissor) => win
      case (a, b) if (a==b) => draw
      case _ => lose
    }
  }

  def result(score: Score): Score = {

    (score.human, score.computer) match {
      case (a,b) if (a==b) => Score(score.human, score.computer, score.roundsMade, 1, Decision.Paper, "Einzelrunde wg. Gleichstand")
      case (a,b) if (a > b) => Score(score.human, score.computer, score.roundsMade, 0, Decision.Paper, score.msg +  " ::: Du gewinnst Gesamtspiel!")
      case _ => Score(score.human, score.computer, score.roundsMade, 0, Decision.Paper, score.msg +  " ::: Computer gewinnt Gesamtspiel!")
    }

  }

}

