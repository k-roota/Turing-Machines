package translator

import tm_general.{Defaults, OneTapeInstr, TmParser, Move}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Using

object Translator {
  def translate(path: String): Vector[OneTapeInstr] = {
    // read two-tape instructions from a file
    val baseInstructions = Using.resource(Source.fromFile(path)) { file =>
      val parser = new TmParser[TwoTapeInstr](file) {
        override def lineToInstr(line: String): TwoTapeInstr = TwoTapeInstr.fromString(line)
      }
      parser.toSeq
    }

    val resultBuffer = ArrayBuffer[OneTapeInstr]()

    if(baseInstructions.nonEmpty) {
      //states in two-tape machine
      val baseStates = baseInstructions.flatMap(x => Seq(x.currState, x.newState)).distinct

      // letters used in two-tape machine
      val baseLetters = (baseInstructions.flatMap(x => Seq(x.currLetters, x.newLetters)).flatten :+ Defaults.blank).distinct

      val st = new StateTranslator(baseStates)
      val lt = new LetterTranslator(baseLetters.max)

      // new start state name
      val newStart = st.getNewState(Defaults.startState)
      val newBaseStates = baseStates.filter(_ != Defaults.startState) :+ newStart

      // add one-tape transitions
      addInit(resultBuffer, st, lt, newStart, baseLetters)
      addFromStartPos(resultBuffer, st, lt, newBaseStates, baseLetters)
      addReturn(resultBuffer, st, lt, newBaseStates, baseLetters)
      addSearchNotFound(resultBuffer, st, lt, newBaseStates, baseLetters)
      addMoveHead(resultBuffer, st, lt, newBaseStates, baseLetters)
      addSearchFound(resultBuffer, st, lt, newStart, baseLetters, baseInstructions)
    }
    resultBuffer.toVector
  }

  private def addInit(
                       buffer: ArrayBuffer[OneTapeInstr],
                       st: StateTranslator,
                       lt: LetterTranslator,
                       newStart: String,
                       baseLetters: Seq[Int]
                     ): Unit = {
    for (letter <- baseLetters) {
      // start
      buffer += OneTapeInstr(Defaults.startState, letter, st.getPutHeadsState(letter), lt.getSpecial, Move.R)
      for (stateLetter <- baseLetters) {

        // init heads
        buffer += OneTapeInstr(
          st.getPutHeadsState(stateLetter),
          lt.get(letter, Defaults.blank, withFirstHead = false, withSecondHead = false),
          st.getInitMoveState(letter),
          lt.get(stateLetter, Defaults.blank, withFirstHead = true, withSecondHead = true),
          Move.R
        )

        letter match {
          case Defaults.blank =>
            // when init ends
            buffer += OneTapeInstr(
              st.getInitMoveState(stateLetter),
              lt.get(Defaults.blank, Defaults.blank, withFirstHead = false, withSecondHead = false),
              st.getCompositeState(newStart, SubState.Return, 0, Defaults.blank),
              lt.get(stateLetter, Defaults.blank, withFirstHead = false, withSecondHead = false),
              Move.L
            )
          case nonBlank =>
            // when init continues
            buffer += OneTapeInstr(
              st.getInitMoveState(stateLetter),
              lt.get(nonBlank, Defaults.blank, withFirstHead = false, withSecondHead = false),
              st.getInitMoveState(nonBlank),
              lt.get(stateLetter, Defaults.blank, withFirstHead = false, withSecondHead = false),
              Move.R
            )
        }
      }
    }
  }

  // return letters sequence for given cases
  private def lettersSeq(lt: LetterTranslator, firstLetters: Seq[Int], secondLetters: Seq[Int], firstHeadOpts: Seq[Boolean], secondHeadOpts: Seq[Boolean]) =
    for (firstLetter <- firstLetters;
         secondLetter <- secondLetters;
         withFirstHead <- firstHeadOpts;
         withSecondHead <- secondHeadOpts)
      yield lt.get(firstLetter, secondLetter, withFirstHead, withSecondHead)

  private def addFromStartPos(
                               buffer: ArrayBuffer[OneTapeInstr],
                               st: StateTranslator,
                               lt: LetterTranslator,
                               newBaseStates: Seq[String],
                               baseLetters: Seq[Int]
                             ): Unit = {
    for (state <- newBaseStates;
         tape <- 0 until 2;
         stateLetter <- baseLetters) {
      if (state != Defaults.accState && state != Defaults.rejState)
        // stop returning and start searching
        buffer += OneTapeInstr(
          st.getCompositeState(state, SubState.Return, tape, stateLetter),
          lt.getSpecial,
          st.getCompositeState(state, SubState.Search, tape, stateLetter),
          lt.getSpecial,
          Move.R
        )

      val moveState = st.getCompositeState(state, SubState.Move, tape, stateLetter)
      // head moved to start position
      buffer += OneTapeInstr(
        moveState,
        lt.getSpecial,
        moveState,
        lt.getSpecial,
        Move.R
      )
    }
  }

  private def addReturn(
                         buffer: ArrayBuffer[OneTapeInstr],
                         st: StateTranslator,
                         lt: LetterTranslator,
                         newBaseStates: Seq[String],
                         baseLetters: Seq[Int]
                       ): Unit = {
    for (state <- newBaseStates if state != Defaults.accState && state != Defaults.rejState;
         tape <- 0 until 2;
         stateLetter <- baseLetters;
         letter <- lettersSeq(lt, baseLetters, baseLetters, Seq(false, true), Seq(false, true))) {
      val compositeState = st.getCompositeState(state, SubState.Return, tape, stateLetter)

      // return to start position
      buffer += OneTapeInstr(
        compositeState,
        letter,
        compositeState,
        letter,
        Move.L
      )
    }
  }

  private def addSearchNotFound(
                                 buffer: ArrayBuffer[OneTapeInstr],
                                 st: StateTranslator,
                                 lt: LetterTranslator,
                                 newBaseStates: Seq[String],
                                 baseLetters: Seq[Int]
                               ): Unit = {
    for (state <- newBaseStates if state != Defaults.accState && state != Defaults.rejState;
         stateLetter <- baseLetters) {

      // tape 0
      val tape0Search = st.getCompositeState(state, SubState.Search, 0, stateLetter)
      for (letter <- lettersSeq(lt, baseLetters, baseLetters, Seq(false), Seq(false, true))) {
        // continue head search
        buffer += OneTapeInstr(
          tape0Search,
          letter,
          tape0Search,
          letter,
          Move.R
        )
      }

      // tape 1
      val tape1Search = st.getCompositeState(state, SubState.Search, 1, stateLetter)
      for (letter <- lettersSeq(lt, baseLetters, baseLetters, Seq(false, true), Seq(false))) {
        // continue head search
        buffer += OneTapeInstr(
          tape1Search,
          letter,
          tape1Search,
          letter,
          Move.R
        )
      }
    }
  }

  private def addMoveHead(
                           buffer: ArrayBuffer[OneTapeInstr],
                           st: StateTranslator,
                           lt: LetterTranslator,
                           newBaseStates: Seq[String],
                           baseLetters: Seq[Int]
                         ): Unit = {
    for (state <- newBaseStates;
         stateLetter <- baseLetters;
         firstLetter <- baseLetters;
         secondLetter <- baseLetters;
         withOtherHead <- Seq(false, true)){

      // tape 0 moves
      buffer += OneTapeInstr(
        st.getCompositeState(state, SubState.Move, 0, stateLetter),
        lt.get(firstLetter, secondLetter, withFirstHead = false, withSecondHead = withOtherHead),
        st.getCompositeState(state, SubState.Return, 1, stateLetter),
        lt.get(firstLetter, secondLetter, withFirstHead = true, withSecondHead = withOtherHead),
        Move.L
      )

      val newState = if (state == Defaults.accState || state == Defaults.rejState)
        state
      else
        st.getCompositeState(state, SubState.Return, 0, secondLetter)
      // tape 1 moves
      buffer += OneTapeInstr(
        st.getCompositeState(state, SubState.Move, 1, stateLetter),
        lt.get(firstLetter, secondLetter, withOtherHead, withSecondHead = false),
        newState,
        lt.get(firstLetter, secondLetter, withOtherHead, withSecondHead = true),
        Move.L
      )
    }
  }

  private def addSearchFound(
                              buffer: ArrayBuffer[OneTapeInstr],
                              st: StateTranslator,
                              lt: LetterTranslator,
                              newStart: String,
                              baseLetters: Seq[Int],
                              baseInstructions: Seq[TwoTapeInstr]
                            ): Unit = {
    def changeStart(state: String) = if (state == Defaults.startState) newStart else state

    for (instruction <- baseInstructions) {

      // tape 0
      val lettersTuples0 = baseLetters.map(letter => (letter, false)) :+ (instruction.currLetters(1), true)
      for (letterTuple <- lettersTuples0) {
        val (secondLetter, withSecondHead) = letterTuple

        // head 0 found
        buffer += OneTapeInstr(
          st.getCompositeState(changeStart(instruction.currState), SubState.Search, 0, instruction.currLetters(1)),
          lt.get(instruction.currLetters(0), secondLetter, withFirstHead = true, withSecondHead = withSecondHead),
          st.getCompositeState(changeStart(instruction.currState), SubState.Move, 0, instruction.currLetters(0)),
          lt.get(instruction.newLetters(0), secondLetter, withFirstHead = false, withSecondHead = withSecondHead),
          instruction.moves(0)
        )
      }

      // tape 1
      for (firstLetter <- baseLetters;
           withFirstHead <- Seq(false, true)) {

        // head 1 found
        buffer += OneTapeInstr(
          st.getCompositeState(changeStart(instruction.currState), SubState.Search, 1, instruction.currLetters(0)),
          lt.get(firstLetter, instruction.currLetters(1), withFirstHead, withSecondHead = true),
          st.getCompositeState(changeStart(instruction.newState), SubState.Move, 1, instruction.currLetters(0)),
          lt.get(firstLetter, instruction.newLetters(1), withFirstHead, withSecondHead = false),
          instruction.moves(1)
        )
      }
    }
  }
}
