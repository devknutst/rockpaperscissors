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
      "human" -> number,
      "computer" -> number,
      "roundsMade" -> number,
      "roundsToGo" -> number,
      "decision" -> DecisionMapping.decisionType,
      "msg" -> text
    )(Score.apply)(Score.unapply)
  )



  def index() = Action { implicit request: Request[AnyContent] =>

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


  def make() = Action { implicit request =>

    scoreForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.make(formWithErrors))
      },
      decision => {
        Ok(views.html.make(scoreForm))
      }
    )

  }

}
