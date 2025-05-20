
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
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
                  HASH: cb9b03e932f92ab32488c539ecf365c946f2bfdf
                  MATRIX: 733->1|848->23|875->25|900->42|939->44|968->47|1399->450|1428->451|1462->458|1625->593|1654->594|1690->603|1751->636|1780->637|1809->638|1872->673|1901->674|1960->705|1989->706|2018->707|2059->720|2088->721|2123->729|2151->730|2218->769|2247->770|2283->779|2381->849|2410->850|2448->860|2477->861|2506->862|2546->874|2575->875|2613->885|2642->886|2698->914|2727->915|2766->926|2795->927|2845->950|2873->951|2907->958|2935->959|2981->975
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|36->12|36->12|37->13|39->15|39->15|40->16|41->17|41->17|41->17|41->17|41->17|42->18|42->18|42->18|42->18|42->18|43->19|43->19|43->19|43->19|44->20|45->21|45->21|45->21|45->21|45->21|45->21|45->21|45->21|45->21|46->22|46->22|46->22|46->22|48->24|48->24|49->25|49->25|51->27
                  -- GENERATED --
              */
          