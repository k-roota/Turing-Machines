package translator

class LetterTranslator(maxLetter: Int) {

  // translate two-tape letter to one-tape letter
  def get(firstLetter: Int, secondLetter: Int, withFirstHead: Boolean, withSecondHead: Boolean): Int = {
    (((withSecondHead * 2) + withFirstHead) * (maxLetter + 1) + secondLetter) * (maxLetter + 1) + firstLetter
  }

  // get special letter
  def getSpecial: Int = get(maxLetter, maxLetter, withFirstHead = true, withSecondHead = true) + 1

}
