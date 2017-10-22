package controllers

import javax.inject._

import _root_.mapping.DecisionMapping
import model.{Decision, Score}
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
      "msg" -> default(text, "kein Zug")
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

    val test = scoreForm.apply("roundsToGo")



    scoreForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.make(formWithErrors))
      },
      decision => {
        println(decision.toString)
        Ok(views.html.make(scoreForm))
      }
    )

  }

}
