
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
          <input type="number" id="nbDicesA" class="form-control mb-2" value="5" min="1">
          <label>Expected values
          <select id="expectedValueA" class="form-control">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option>4</option>
              <option>5</option>
              <option>6</option>
              <option>2+</option>
              <option>3+</option>
              <option>4+</option>
              <option selected>5+</option>
            </select>
          </label>
          <label class="form-label">Number of occurrences</label>
          <input type="number" id="occurrencesA" class="form-control mb-2" value="2" min="0">
          <div id="diceDisplayA" class="dice-row mt-2"></div>
        </div>
      </div>

      <div class="col-md-6">
        <div class="card p-4">
          <h4>Roll B</h4>
          <label class="form-label">Number of Dice</label>
          <input type="number" id="nbDicesB" class="form-control mb-2" value="2" min="1">
          <label>Expected values
          <select id="expectedValueB" class="form-control">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option>4</option>
              <option>5</option>
              <option>6</option>
              <option>2+</option>
              <option>3+</option>
              <option>4+</option>
              <option>5+</option>
            </select>
          </label>
          <label class="form-label">Number of occurrences</label>
          <input type="number" id="occurrencesB" class="form-control mb-2" value="1" min="0">
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
    function renderDice(containerId, count) """),format.raw/*81.45*/("""{"""),format.raw/*81.46*/("""
      """),format.raw/*82.7*/("""const container = document.getElementById(containerId);
      container.innerHTML = '';
      for (let i = 0; i < count; i++) """),format.raw/*84.39*/("""{"""),format.raw/*84.40*/("""
        """),format.raw/*85.9*/("""const dice = document.createElement("i");
        dice.className = "fas fa-dice dice-icon";
        container.appendChild(dice);
      """),format.raw/*88.7*/("""}"""),format.raw/*88.8*/("""
    """),format.raw/*89.5*/("""}"""),format.raw/*89.6*/("""

    """),format.raw/*91.5*/("""function updateDiceDisplays() """),format.raw/*91.35*/("""{"""),format.raw/*91.36*/("""
      """),format.raw/*92.7*/("""renderDice("diceDisplayA", parseInt(document.getElementById("nbDicesA").value));
      renderDice("diceDisplayB", parseInt(document.getElementById("nbDicesB").value));
    """),format.raw/*94.5*/("""}"""),format.raw/*94.6*/("""

    """),format.raw/*96.5*/("""document.getElementById("compareBtn").addEventListener("click", () => """),format.raw/*96.75*/("""{"""),format.raw/*96.76*/("""
      """),format.raw/*97.7*/("""const payload = """),format.raw/*97.23*/("""{"""),format.raw/*97.24*/("""
        """),format.raw/*98.9*/("""nbDicesA: parseInt(document.getElementById("nbDicesA").value),
        expectedValueA: document.getElementById("expectedValueA").value,
        occurrencesA: parseInt(document.getElementById("occurrencesA").value),
        nbDicesB: parseInt(document.getElementById("nbDicesB").value),
        expectedValueB: document.getElementById("expectedValueB").value,
        occurrencesB: parseInt(document.getElementById("occurrencesB").value)
      """),format.raw/*104.7*/("""}"""),format.raw/*104.8*/(""";

      updateDiceDisplays();

      fetch("/roll", """),format.raw/*108.22*/("""{"""),format.raw/*108.23*/("""
        """),format.raw/*109.9*/("""method: "POST",
        headers: """),format.raw/*110.18*/("""{"""),format.raw/*110.19*/(""" """),format.raw/*110.20*/(""""Content-Type": "application/json" """),format.raw/*110.55*/("""}"""),format.raw/*110.56*/(""",
        body: JSON.stringify(payload)
      """),format.raw/*112.7*/("""}"""),format.raw/*112.8*/(""")
      .then(res => res.json())
      .then(data => """),format.raw/*114.21*/("""{"""),format.raw/*114.22*/("""
        """),format.raw/*115.9*/("""document.getElementById("result").innerHTML = `
          <div class="card p-4 mt-4">
            <h5>Results</h5>
            <p><strong>Roll A:</strong> $"""),format.raw/*118.42*/("""{"""),format.raw/*118.43*/(""" """),format.raw/*118.44*/("""(data.probA * 100).toFixed(2) """),format.raw/*118.74*/("""}"""),format.raw/*118.75*/("""% chance</p>
            <p><strong>Roll B:</strong> $"""),format.raw/*119.42*/("""{"""),format.raw/*119.43*/(""" """),format.raw/*119.44*/("""(data.probB * 100).toFixed(2) """),format.raw/*119.74*/("""}"""),format.raw/*119.75*/("""% chance</p>
            <h3 class="mt-3">🏆 Winner: $"""),format.raw/*120.42*/("""{"""),format.raw/*120.43*/("""data.winner"""),format.raw/*120.54*/("""}"""),format.raw/*120.55*/("""</h3>
          </div>
        `;
      """),format.raw/*123.7*/("""}"""),format.raw/*123.8*/(""");
    """),format.raw/*124.5*/("""}"""),format.raw/*124.6*/(""");

    document.getElementById("nbDicesA").addEventListener("input", updateDiceDisplays);
    document.getElementById("nbDicesB").addEventListener("input", updateDiceDisplays);
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
                  HASH: 3d93df5702ded1c496982b481c17b5bd7e53e8db
                  MATRIX: 849->0|912->38|933->40|960->41|1107->162|1202->237|1367->374|1396->375|1425->376|1500->423|1529->424|1561->429|1599->439|1628->440|1657->441|1729->485|1758->486|1790->491|1824->497|1853->498|1882->499|1969->558|1998->559|2028->562|4406->2912|4435->2913|4469->2920|4623->3046|4652->3047|4688->3056|4850->3191|4878->3192|4910->3197|4938->3198|4971->3204|5029->3234|5058->3235|5092->3242|5291->3414|5319->3415|5352->3421|5450->3491|5479->3492|5513->3499|5557->3515|5586->3516|5622->3525|6093->3968|6122->3969|6204->4022|6234->4023|6271->4032|6333->4065|6363->4066|6393->4067|6457->4102|6487->4103|6561->4149|6590->4150|6672->4203|6702->4204|6739->4213|6924->4369|6954->4370|6984->4371|7043->4401|7073->4402|7156->4456|7186->4457|7216->4458|7275->4488|7305->4489|7388->4543|7418->4544|7458->4555|7488->4556|7556->4596|7585->4597|7620->4604|7649->4605
                  LINES: 27->1|28->2|28->2|29->3|34->8|34->8|37->11|37->11|37->11|37->11|37->11|38->12|38->12|38->12|38->12|38->12|38->12|39->13|39->13|39->13|39->13|39->13|39->13|40->14|107->81|107->81|108->82|110->84|110->84|111->85|114->88|114->88|115->89|115->89|117->91|117->91|117->91|118->92|120->94|120->94|122->96|122->96|122->96|123->97|123->97|123->97|124->98|130->104|130->104|134->108|134->108|135->109|136->110|136->110|136->110|136->110|136->110|138->112|138->112|140->114|140->114|141->115|144->118|144->118|144->118|144->118|144->118|145->119|145->119|145->119|145->119|145->119|146->120|146->120|146->120|146->120|149->123|149->123|150->124|150->124
                  -- GENERATED --
              */
          