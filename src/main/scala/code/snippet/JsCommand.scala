package code.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.SHtml
import net.liftweb.json._

/**
 * User: ggarcia
 * Date: 3/5/13
 * Time: 10:43 PM
 */

class JsCommand {
  implicit val formats = net.liftweb.json.DefaultFormats

  var persons = "Carly" :: "Joe" :: "John" :: "Mary" :: Nil

  def loadData = {
    def funcBody(json: JValue) = {
      val p = json.extract[String]

      persons = persons.filterNot(_ == p)

      JsRaw(
        """
          $("#person [value='""" + p + """']").remove()
        """).cmd &
      JsCmds.Alert(p + " was removed")
    }

   Script(JsCmds.Function("ajaxDeletePeople", List("person"), SHtml.jsonCall(JsRaw("person"), (value: JValue) => funcBody(value))))
  }

  def jsFunction = {

    "script *" #> SHtml.ajaxInvoke(() => JsCmds.Alert("responding to the ajax call"))
  }
}