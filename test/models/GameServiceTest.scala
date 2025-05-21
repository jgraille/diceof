import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import models.GameService
import models.{DiceMode, Exact, AtLeast}

class GameServiceTest extends AnyFlatSpec with Matchers {

  "comb" should "return 1 for C(0, 0)" in {
    GameService.comb(0, 0) should be (1)
  }

  it should "return 1 for C(1,0)" in {
    GameService.comb(1, 0) should be (1)
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

  it should "throw IllegalArgumentException for number of successful dice rolls higher than number of dice" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.comb(2, 4)
    }
  }

  it should "throw IllegalArgumentException for n > 20" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.comb(21, 10)
    }
  }

  it should "throw IllegalArgumentException for negative n" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.comb(-1, 0)
    }
  }

  it should "throw IllegalArgumentException for negative k" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.comb(5, -1)
    }
  }

  "binomialProbability" should "calculate correct value for Exact mode" in {
    val result = GameService.binomialProbability(n = 2, expectedValues = 4, k = 1, mode = Exact)
    result shouldEqual 5.0 / 18
  }

  it should "calculate correct value for AtLeast mode" in {
    val result = GameService.binomialProbability(n = 5, expectedValues = 5, k = 2, mode = AtLeast)
    val roundedResult = BigDecimal(result).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    val roundedExpected = BigDecimal(80.0 / 243).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    roundedResult shouldEqual roundedExpected
  }

  it should "throw IllegalArgumentException when the expected value is not between 1 and 6" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = 2, expectedValues = 7, k = 1, mode = Exact)
    }
  }

  it should "throw IllegalArgumentException when k > n" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = 2, expectedValues = 1, k = 3, mode = Exact)
    }
  }

  it should "throw IllegalArgumentException when n is negative" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = -1, expectedValues = 4, k = 1, mode = Exact)
    }
  }

  it should "throw IllegalArgumentException when k is negative" in {
    an [IllegalArgumentException] should be thrownBy {
      GameService.binomialProbability(n = 2, expectedValues = 4, k = -1, mode = Exact)
    }
  }

  "compareProbabilities" should "correctly identify winner A" in {
    val (probA, probB, winner) = GameService.compareProbabilities(
      nbDicesA = 5, expectedValueA = 4, occurrencesA = 2,
      nbDicesB = 3, expectedValueB = 5, occurrencesB = 1,
      modeA = AtLeast, modeB = AtLeast
    )
    winner shouldBe "A"
  }

  it should "correctly identify winner B" in {
    val (probA, probB, winner) = GameService.compareProbabilities(
      nbDicesA = 2, expectedValueA = 6, occurrencesA = 2,
      nbDicesB = 4, expectedValueB = 4, occurrencesB = 2,
      modeA = Exact, modeB = AtLeast
    )
    winner shouldBe "B"
  }

  it should "correctly identify a draw" in {
    val (probA, probB, winner) = GameService.compareProbabilities(
      nbDicesA = 3, expectedValueA = 4, occurrencesA = 1,
      nbDicesB = 3, expectedValueB = 4, occurrencesB = 1,
      modeA = Exact, modeB = Exact
    )
    winner shouldBe "Draw"
  }
}
