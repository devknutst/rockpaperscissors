package mapping


import model.Decision
import play.api.data.{FormError, Forms, Mapping}
import play.api.data.format.Formatter


object DecisionMapping {

  implicit val appTypeFormatter = new Formatter[Decision.Decision] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Decision.Decision] = {
      data.get(key).map { value =>
        try {
          Right(Decision.withName(value))
        } catch {
          case e: NoSuchElementException => error(key, value + " is not a valid decision type")
        }
      }.getOrElse(error(key, "No decision here."))
    }

    private def error(key: String, msg: String) = Left(List(new FormError(key, msg)))

    override def unbind(key: String, value: Decision.Decision): Map[String, String] = {
      Map(key -> value.toString())
    }
  }

  def decisionType: Mapping[Decision.Decision] = Forms.of[Decision.Decision]

}
