import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import models.GameService

class GameServiceTest extends AnyFlatSpec with Matchers {

  "comb" should "return 1 for C(1, 0)" in {
    GameService.comb(0, 0) should be (1)
  }

  it should "return 5 for C(5, 1)" in {
    GameService.comb(5, 1) should be (5)
  }

  it should "return 10 for C(5, 2)" in {
    GameService.comb(5, 2) should be (10)
  }

  it should "return 10 for C(5, 3)" in {
    GameService.comb(5, 3) should be (10)
  }

  it should "return 1 for C(5, 5)" in {
    GameService.comb(5, 5) should be (1)
  }

  it should "raise a value error for number of sucessful dices rolls higher than number of dices" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.comb(2, 4)
    }
  }

  "binomialProbability" should "calculate correct value for exact 'mode'" in {
    val result = GameService.binomialProbability(n = 2, expectedValues = 4, k = 1, mode = "exact")
    result shouldEqual 5.0/ 18
  }

  it should "calculate correct value for atLeast 'mode'" in {
    val result = GameService.binomialProbability(n = 5, expectedValues = 5, k = 2, mode = "atLeast")
    val roundedResult = BigDecimal(result).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val roundedExpected = BigDecimal(80.0/ 243).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    roundedResult shouldEqual roundedExpected
  }

  it should "throw IllegalArgumentException for invalid " in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = 2, expectedValues = 7, k = 1, mode = "exact")
    }
  }

  it should "throw IllegalArgumentException for invalid mode" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = 2, expectedValues = 4, k = 1, mode = "invalid")
    }
  }
}
