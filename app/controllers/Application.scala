package controllers

import javax.inject._

import _root_.mapping.DecisionMapping
import model.{Decision, Generator, Score}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {


  val scoreForm:Form[Score] = Form(
    mapping(
      "human" -> default(number, 0),
      "computer" -> default(number, 0),
      "roundsMade" -> default(number, 0),
      "roundsToGo" -> default(number, 0),
      "decision" -> default(DecisionMapping.decisionType, Decision.Paper),
      "msg" -> default(text, "Erster Zug")
    )(Score.apply)(Score.unapply)
  )



  def index = Action { implicit request: Request[AnyContent] =>

    scoreForm.fill(Score(0, 0, 0, 0, Decision.Paper, ""))

    scoreForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      decision => {
        Ok(views.html.index(scoreForm))
      }
    )
  }


  def make = Action { implicit request =>

    scoreForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.make(formWithErrors, scoreForm.get))
      },
      score => {
        scoreForm.fill(score)
        if (score.msg == "Erster Zug") Ok(views.html.make(scoreForm, score))
        else {
          val next = Generator.play(score)
          next.roundsToGo match {
            case 0 => Ok(views.html.make(scoreForm, Generator.result(score = next)))
            case a if (a > 0) => Ok(views.html.make(scoreForm, next))
            case _ => Ok(views.html.index(scoreForm))
          }
        }
      }
    )

  }

}
