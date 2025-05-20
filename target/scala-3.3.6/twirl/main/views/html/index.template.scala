
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<!-- app/views/index.scala.html -->
 """),_display_(/*2.3*/()),format.raw/*2.5*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dice Probability Versus</title>
  <link href='"""),_display_(/*8.16*/("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css")),format.raw/*8.91*/("""' rel='stylesheet'>
  <script src="https://kit.fontawesome.com/a2e0da0e76.js" crossorigin="anonymous"></script>
  <style>
    .dice-icon """),format.raw/*11.16*/("""{"""),format.raw/*11.17*/(""" """),format.raw/*11.18*/("""font-size: 2em; color: #007bff; margin: 0.1em; """),format.raw/*11.65*/("""}"""),format.raw/*11.66*/("""
    """),format.raw/*12.5*/(""".dice-row """),format.raw/*12.15*/("""{"""),format.raw/*12.16*/(""" """),format.raw/*12.17*/("""display: flex; flex-wrap: wrap; gap: 0.2em; """),format.raw/*12.61*/("""}"""),format.raw/*12.62*/("""
    """),format.raw/*13.5*/(""".card """),format.raw/*13.11*/("""{"""),format.raw/*13.12*/(""" """),format.raw/*13.13*/("""border-radius: 16px; box-shadow: 0 0 20px rgba(0,0,0,0.1); """),format.raw/*13.72*/("""}"""),format.raw/*13.73*/("""
  """),format.raw/*14.3*/("""</style>
</head>
<body class="bg-light">

  <div class="container py-5">
    <h1 class="text-center mb-4">🎲 Dice Probability Versus</h1>

    <div class="row g-4">
      <div class="col-md-6">
        <div class="card p-4">
          <h4>Roll A</h4>
          <label class="form-label">Number of Dice</label>
          <input type="number" id="diceA" class="form-control mb-2" value="2" min="1">
          <label class="form-label">Threshold (e.g. 4+)</label>
          <input type="number" id="thresholdA" class="form-control mb-2" value="4" min="1" max="6">
          <label class="form-label">Target Successes</label>
          <input type="number" id="targetA" class="form-control mb-2" value="1" min="0">
          <div id="diceDisplayA" class="dice-row mt-2"></div>
        </div>
      </div>

      <div class="col-md-6">
        <div class="card p-4">
          <h4>Scenario B</h4>
          <label class="form-label">Number of Dice</label>
          <input type="number" id="diceB" class="form-control mb-2" value="2" min="1">
          <label class="form-label">Threshold (e.g. 5+)</label>
          <input type="number" id="thresholdB" class="form-control mb-2" value="5" min="1" max="6">
          <label class="form-label">Target Successes</label>
          <input type="number" id="targetB" class="form-control mb-2" value="1" min="0">
          <div id="diceDisplayB" class="dice-row mt-2"></div>
        </div>
      </div>
    </div>

    <div class="text-center mt-4">
      <button id="compareBtn" class="btn btn-primary btn-lg">Compare Probabilities</button>
    </div>

    <div id="result" class="text-center mt-5"></div>
  </div>

  <script>
    function renderDice(containerId, count) """),format.raw/*57.45*/("""{"""),format.raw/*57.46*/("""
      """),format.raw/*58.7*/("""const container = document.getElementById(containerId);
      container.innerHTML = '';
      for (let i = 0; i < count; i++) """),format.raw/*60.39*/("""{"""),format.raw/*60.40*/("""
        """),format.raw/*61.9*/("""const dice = document.createElement("i");
        dice.className = "fas fa-dice dice-icon";
        container.appendChild(dice);
      """),format.raw/*64.7*/("""}"""),format.raw/*64.8*/("""
    """),format.raw/*65.5*/("""}"""),format.raw/*65.6*/("""

    """),format.raw/*67.5*/("""function updateDiceDisplays() """),format.raw/*67.35*/("""{"""),format.raw/*67.36*/("""
      """),format.raw/*68.7*/("""renderDice("diceDisplayA", parseInt(document.getElementById("diceA").value));
      renderDice("diceDisplayB", parseInt(document.getElementById("diceB").value));
    """),format.raw/*70.5*/("""}"""),format.raw/*70.6*/("""

    """),format.raw/*72.5*/("""document.getElementById("compareBtn").addEventListener("click", () => """),format.raw/*72.75*/("""{"""),format.raw/*72.76*/("""
      """),format.raw/*73.7*/("""const diceA = parseInt(document.getElementById("diceA").value);
      const thresholdA = parseInt(document.getElementById("thresholdA").value);
      const targetA = parseInt(document.getElementById("targetA").value);

      const diceB = parseInt(document.getElementById("diceB").value);
      const thresholdB = parseInt(document.getElementById("thresholdB").value);
      const targetB = parseInt(document.getElementById("targetB").value);

      updateDiceDisplays();

      fetch("/roll", """),format.raw/*83.22*/("""{"""),format.raw/*83.23*/("""
        """),format.raw/*84.9*/("""method: "POST",
        headers: """),format.raw/*85.18*/("""{"""),format.raw/*85.19*/(""" """),format.raw/*85.20*/(""""Content-Type": "application/json" """),format.raw/*85.55*/("""}"""),format.raw/*85.56*/(""",
        body: JSON.stringify("""),format.raw/*86.30*/("""{"""),format.raw/*86.31*/(""" """),format.raw/*86.32*/("""diceA, thresholdA, targetA, diceB, thresholdB, targetB """),format.raw/*86.87*/("""}"""),format.raw/*86.88*/(""")
      """),format.raw/*87.7*/("""}"""),format.raw/*87.8*/(""")
      .then(res => res.json())
      .then(data => """),format.raw/*89.21*/("""{"""),format.raw/*89.22*/("""
        """),format.raw/*90.9*/("""document.getElementById("result").innerHTML = `
          <div class="card p-4 mt-4">
            <h5>Results</h5>
            <p><strong>Scenario A:</strong> $"""),format.raw/*93.46*/("""{"""),format.raw/*93.47*/(""" """),format.raw/*93.48*/("""(data.probA * 100).toFixed(2) """),format.raw/*93.78*/("""}"""),format.raw/*93.79*/("""% chance</p>
            <p><strong>Scenario B:</strong> $"""),format.raw/*94.46*/("""{"""),format.raw/*94.47*/(""" """),format.raw/*94.48*/("""(data.probB * 100).toFixed(2) """),format.raw/*94.78*/("""}"""),format.raw/*94.79*/("""% chance</p>
            <h3 class="mt-3">🏆 Winner: $"""),format.raw/*95.42*/("""{"""),format.raw/*95.43*/("""data.winner"""),format.raw/*95.54*/("""}"""),format.raw/*95.55*/("""</h3>
          </div>
        `;
      """),format.raw/*98.7*/("""}"""),format.raw/*98.8*/(""");
    """),format.raw/*99.5*/("""}"""),format.raw/*99.6*/(""");

    document.getElementById("diceA").addEventListener("input", updateDiceDisplays);
    document.getElementById("diceB").addEventListener("input", updateDiceDisplays);
    window.onload = updateDiceDisplays;
  </script>

</body>
</html>
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: ed1e6de252a2d18e31decb8519bdf70bbedbd403
                  MATRIX: 849->0|912->38|933->40|960->41|1107->162|1202->237|1367->374|1396->375|1425->376|1500->423|1529->424|1561->429|1599->439|1628->440|1657->441|1729->485|1758->486|1790->491|1824->497|1853->498|1882->499|1969->558|1998->559|2028->562|3767->2273|3796->2274|3830->2281|3984->2407|4013->2408|4049->2417|4211->2552|4239->2553|4271->2558|4299->2559|4332->2565|4390->2595|4419->2596|4453->2603|4646->2769|4674->2770|4707->2776|4805->2846|4834->2847|4868->2854|5390->3348|5419->3349|5455->3358|5516->3391|5545->3392|5574->3393|5637->3428|5666->3429|5725->3460|5754->3461|5783->3462|5866->3517|5895->3518|5930->3526|5958->3527|6039->3580|6068->3581|6104->3590|6292->3750|6321->3751|6350->3752|6408->3782|6437->3783|6523->3841|6552->3842|6581->3843|6639->3873|6668->3874|6750->3928|6779->3929|6818->3940|6847->3941|6914->3981|6942->3982|6976->3989|7004->3990
                  LINES: 27->1|28->2|28->2|29->3|34->8|34->8|37->11|37->11|37->11|37->11|37->11|38->12|38->12|38->12|38->12|38->12|38->12|39->13|39->13|39->13|39->13|39->13|39->13|40->14|83->57|83->57|84->58|86->60|86->60|87->61|90->64|90->64|91->65|91->65|93->67|93->67|93->67|94->68|96->70|96->70|98->72|98->72|98->72|99->73|109->83|109->83|110->84|111->85|111->85|111->85|111->85|111->85|112->86|112->86|112->86|112->86|112->86|113->87|113->87|115->89|115->89|116->90|119->93|119->93|119->93|119->93|119->93|120->94|120->94|120->94|120->94|120->94|121->95|121->95|121->95|121->95|124->98|124->98|125->99|125->99
                  -- GENERATED --
              */
          