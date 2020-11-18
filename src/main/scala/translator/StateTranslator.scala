package translator

import translator.SubState._

import scala.collection.mutable

class StateTranslator(baseStates: Seq[String]) {
  // all so for used states
  private val statesSet = baseStates.to(mutable.Set)

  // main states
  private val compositeMap = mutable.Map[(String, SubState, Int, Int), String]()

  // states for shifting right during initialization
  private val initMap = mutable.Map[Int, String]()

  // states for putting heads on the tape during initialization
  private val putHeadsMap = mutable.Map[Int, String]()

  // save new state and change its name if a state with the same name was used
  def getNewState(state: String): String = {
    var i = 1
    var newState = state
    while (statesSet.contains(newState)) {
      i += 1
      newState = s"${state}v$i"
    }

    statesSet.add(newState)
    newState
  }

  // add a state to map or use saved one
  private def getMappedState[K](key: K, map: mutable.Map[K, String])(createState: K => String) = {
    if (!map.contains(key)) {
      val state = createState(key)
      map.addOne((key, getNewState(state)))
    }
    map(key)
  }

  /**
   * Get one-tape main states names
   * @param baseState a state from two-tape machine
   * @param subState searching for a head, moving a head or returning to start position case
   * @param tape editing tape nr 0 or 1
   * @param letter a letter stored in state
   * @return a state name
   */
  def getCompositeState(baseState: String, subState: SubState, tape: Int, letter: Int): String =
    getMappedState((baseState, subState, tape, letter), compositeMap)(k => s"${k._1}x${k._2}${k._3}x${k._4}".toLowerCase)

  /**
   * Get initial copying state name
   * @param letter a stored letter
   * @return a state name
   */
  def getInitMoveState(letter: Int): String =
    getMappedState(letter, initMap)(k => s"initmove$k")

  /**
   * Get initial putting heads ont the tape state name
   * @param letter a stored letter
   * @return a state name
   */
  def getPutHeadsState(letter: Int): String =
    getMappedState(letter, putHeadsMap)(k => s"putheads$k")

}
