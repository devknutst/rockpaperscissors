package service

import javax.inject.Inject

import play.api.i18n.{Lang, Langs}
import play.api.mvc.{BaseController, ControllerComponents}

class ScalaI18nService @Inject()(langs: Langs) {
  val availableLangs: Seq[Lang] = langs.availables
}