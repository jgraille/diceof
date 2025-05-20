
package views.html

import _root_.play.twirl.api.TwirlFeatureImports.*
import _root_.play.twirl.api.TwirlHelperImports.*
import scala.language.adhocExtensions
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Request[_],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(request: Request[_]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Dice Game")/*3.19*/ {_display_(Seq[Any](format.raw/*3.21*/("""
  """),format.raw/*4.3*/("""<div class="container mt-5">
    <h1>Dice Game</h1>
    <label>Dice A: <input type="number" id="diceA" value="1" min="1"></label>
    <label>Dice B: <input type="number" id="diceB" value="1" min="1"></label>
    <button id="rollBtn" class="btn btn-primary">Roll</button>
    <div id="result" class="mt-3"></div>
  </div>
  <script>
    document.getElementById("rollBtn").addEventListener("click", () => """),format.raw/*12.72*/("""{"""),format.raw/*12.73*/("""
      """),format.raw/*13.7*/("""const diceA = document.getElementById("diceA").value;
      const diceB = document.getElementById("diceB").value;
      fetch("/roll", """),format.raw/*15.22*/("""{"""),format.raw/*15.23*/("""
        """),format.raw/*16.9*/("""method: "POST",
        headers: """),format.raw/*17.18*/("""{"""),format.raw/*17.19*/(""" """),format.raw/*17.20*/(""""Content-Type": "application/json" """),format.raw/*17.55*/("""}"""),format.raw/*17.56*/(""",
        body: JSON.stringify("""),format.raw/*18.30*/("""{"""),format.raw/*18.31*/(""" """),format.raw/*18.32*/("""diceA, diceB """),format.raw/*18.45*/("""}"""),format.raw/*18.46*/(""")
      """),format.raw/*19.7*/("""}"""),format.raw/*19.8*/(""").then(res => res.json()).then(data => """),format.raw/*19.47*/("""{"""),format.raw/*19.48*/("""
        """),format.raw/*20.9*/("""document.getElementById("result").innerHTML = `
          <p>Dice A: $"""),format.raw/*21.23*/("""{"""),format.raw/*21.24*/("""data.rollA"""),format.raw/*21.34*/("""}"""),format.raw/*21.35*/(""" """),format.raw/*21.36*/("""vs Dice B: $"""),format.raw/*21.48*/("""{"""),format.raw/*21.49*/("""data.rollB"""),format.raw/*21.59*/("""}"""),format.raw/*21.60*/("""</p>
          <h3>Winner: $"""),format.raw/*22.24*/("""{"""),format.raw/*22.25*/("""data.winner"""),format.raw/*22.36*/("""}"""),format.raw/*22.37*/("""</h3>
        `;
      """),format.raw/*24.7*/("""}"""),format.raw/*24.8*/(""");
    """),format.raw/*25.5*/("""}"""),format.raw/*25.6*/(""");
  </script>
""")))}),format.raw/*27.2*/("""
"""))
      }
    }
  }

  def render(request:Request[_]): play.twirl.api.HtmlFormat.Appendable = apply(request)

  def f:((Request[_]) => play.twirl.api.HtmlFormat.Appendable) = (request) => apply(request)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: 461e3d8d297800ab24ddc4f82d7cd893e340ceaf
                  MATRIX: 771->1|886->23|913->25|938->42|977->44|1006->47|1437->450|1466->451|1500->458|1663->593|1692->594|1728->603|1789->636|1818->637|1847->638|1910->673|1939->674|1998->705|2027->706|2056->707|2097->720|2126->721|2161->729|2189->730|2256->769|2285->770|2321->779|2419->849|2448->850|2486->860|2515->861|2544->862|2584->874|2613->875|2651->885|2680->886|2736->914|2765->915|2804->926|2833->927|2883->950|2911->951|2945->958|2973->959|3019->975
                  LINES: 22->1|27->2|28->3|28->3|28->3|29->4|37->12|37->12|38->13|40->15|40->15|41->16|42->17|42->17|42->17|42->17|42->17|43->18|43->18|43->18|43->18|43->18|44->19|44->19|44->19|44->19|45->20|46->21|46->21|46->21|46->21|46->21|46->21|46->21|46->21|46->21|47->22|47->22|47->22|47->22|49->24|49->24|50->25|50->25|52->27
                  -- GENERATED --
              */
          