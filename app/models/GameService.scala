package models

sealed trait DiceMode
case object Exact extends DiceMode
case object AtLeast extends DiceMode

object GameService {
  // Cache for memoization of combinations with size limit
  private val MaxCacheSize = 1000
  private val combCache = new java.util.LinkedHashMap[Tuple2[Int, Int], Int](MaxCacheSize, 0.75f, true) {
    override protected def removeEldestEntry(eldest: java.util.Map.Entry[Tuple2[Int, Int], Int]): Boolean =
      size() > MaxCacheSize
  }

  /**
   * Computes the binomial coefficient C(n, k) = n! / (k! * (n - k)!).
   * This is used to calculate the number of ways to choose `k` successes from `n` trials.
   *
   * @param n Number of dice rolled (must be <= 20 for numerical stability)
   * @param k Number of successful dice rolls
   * @return The binomial coefficient C(n, k)
   * @throws IllegalArgumentException if k > n or n > 20
   */
  def comb(n: Int, k: Int): Int = {
    require(n <= 20, "n must be <= 20 for numerical stability")
    require(k <= n, "k cannot be greater than n")
    require(n >= 0 && k >= 0, "n and k must be non-negative")

    val key = (n, k)
    Option(combCache.get(key)).getOrElse {
      val result =
        if (k == 0 || k == n) 1
        else {
          val numerator = (n - k + 1 to n).product
          val denominator = (1 to k).product
          numerator / denominator
        }
      
      combCache.put(key, result)
      result
    }
  }

  /**
   * Computes the probability of rolling `k` successes when rolling `n` dice.
   * A success is defined as rolling a value that meets the given threshold.
   * 
   * For instance, rolling 5 dices with occurences of 2 to do 5 or 6
   * It gives:
   * C(5, 2) * (2/6)^2 * (4/6)^(5-2)
   *
   * @param n Number of dice rolled.
   * @param expectedValues Value to compare each dice against.
   * @param k Number of successful dice rolls.(occurences)
   * @param mode Whether the threshold is "exact" or "atLeast".
   *             - "exact": count dice that roll exactly `threshold`.
   *             - "atLeast": count dice that roll `threshold` or higher.
   * @return Probability of getting at least `k` successful dice.
   * @throws IllegalArgumentException if parameters are invalid
   */
  def binomialProbability(n: Int, expectedValues: Int, k: Int, mode: DiceMode): Double = {
    require(expectedValues >= 1 && expectedValues <= 6, "expectedValues must be between 1 and 6")
    require(n >= k, "Number of successes cannot exceed number of dice")
    require(k >= 0, "Number of successes must be non-negative")
    require(n >= 0, "Number of dice must be non-negative")

    val p: Double = mode match {
      case Exact => 1.0 / 6  // Only one face matches
      case AtLeast => (7 - expectedValues).toDouble / 6  // Faces equal or greater than threshold
    }

    val q: Double = 1 - p
    comb(n, k).toDouble * math.pow(p, k) * math.pow(q, n - k)
  }

  /**
   * Compares the success probabilities of two different dice roll configurations.
   *
   * @param nbDicesA Number of dice for configuration A
   * @param expectedValueA Expected value for configuration A (1-6)
   * @param occurrencesA Number of successes needed for configuration A
   * @param nbDicesB Number of dice for configuration B
   * @param expectedValueB Expected value for configuration B (1-6)
   * @param occurrencesB Number of successes needed for configuration B
   * @param modeA Mode for configuration A
   * @param modeB Mode for configuration B
   * @return Tuple of (probability A, probability B, winner)
   */
  def compareProbabilities(
      nbDicesA: Int, expectedValueA: Int, occurrencesA: Int,
      nbDicesB: Int, expectedValueB: Int, occurrencesB: Int,
      modeA: DiceMode, modeB: DiceMode
  ): (Double, Double, String) = {
    val probA = binomialProbability(nbDicesA, expectedValueA, occurrencesA, modeA)
    val probB = binomialProbability(nbDicesB, expectedValueB, occurrencesB, modeB)

    val winner =
      if (probA > probB) "A"
      else if (probB > probA) "B"
      else "Draw"

    (probA, probB, winner)
  }
}
