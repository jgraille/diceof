package models

object GameService {
  /**
   * Computes the binomial coefficient C(n, k) = n! / (k! * (n - k)!).
   * This is used to calculate the number of ways to choose `k` successes from `n` trials.
   * Only works for reasonably small values (n <= 20)
   *
   * @param n Number of dice rolled
   * @param k Number of successful dice rolls.
   * @return The binomial coefficient C(n, r).
   */
  def comb(n: Int, k: Int): Int =
    if k > n then throw new IllegalArgumentException("k cannot be greater than n")
    else if k == 0 || k == n then 1
    else
      val numerator = (n - k + 1 to n).product
      val denominator = (1 to k).product
      numerator / denominator

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
   */
  def binomialProbability(n: Int, expectedValues: Int, k: Int, mode: String): Double =
    if expectedValues < 1 || expectedValues > 6 then
      throw new IllegalArgumentException("expectedValues must be between 1 and 6")
    val p: Double = mode match
      case "exact"   => 1.0 / 6                  // Only one face matches
      case "atLeast" => (7 - expectedValues).toDouble / 6 // Faces equal or greater than threshold
      case _         => throw new IllegalArgumentException("Mode must be 'exact' or 'atLeast'")

    val q: Double = 1 - p

    comb(n, k).toDouble * math.pow(p, k) * math.pow(q, n - k)
    /*
    (k to n).map { x =>
      comb(n, x).toDouble * math.pow(p, x) * math.pow(q, n - x)
    }.sum
    */

  /**
   * Compares the success probabilities of two different dice roll configurations.
   *
   * @param nbDicesA Number of dice
   * @param expectedValueA Expected value "exact" or "atLeast"
   * @param occurrencesA Number of occurrences / successes to achieve
   * @param nbDicesB Number of dice for
   * @param expectedValueB Expected value "exact" or "atLeast"
   * @param occurrencesB Number of occurrences / successes to achieve
   * @param modeA expected value mode for roll configuration A ("exact" or "atLeast")
   * @param modeB expected value mode for roll configuration B ("exact" or "atLeast")
   * @return Tuple of (probA, probB, winner)
   */
  def compareProbabilities(
      nbDicesA: Int, expectedValueA: Int, occurrencesA: Int,
      nbDicesB: Int, expectedValueB: Int, occurrencesB: Int,
      modeA: String, modeB: String
  ): (Double, Double, String) =
    val probA: Double = binomialProbability(nbDicesA,expectedValueA,occurrencesA,modeA)
    val probB: Double = binomialProbability(nbDicesB,expectedValueB,occurrencesB,modeB)

    val winner: String =
      if probA > probB then "A"
      else if probB > probA then "B"
      else "Draw"

    (probA, probB, winner)
}
