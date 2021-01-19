package io.getquill

import scala.quoted._
import scala.compiletime._
import scala.deriving._
import scala.reflect._

object EnumerateNames {

  inline def summonNext[T] =
    summonFrom {
      case en: EnumerateNames[T] => en
    }

  inline def walkThrough[Types <: Tuple]: List[String] =
    inline erasedValue[Types] match
      case _: (tpe *: tpes) =>
        summonNext[tpe].apply +: walkThrough[tpes]
      case _: EmptyTuple =>
        Nil
      

  inline def derived[T]: EnumerateNames[T] = 
    summonFrom {
      case ev: Mirror.Of[T] =>
        new EnumerateNames[T] {
          def apply =
            inline ev match
              case m: Mirror.ProductOf[T] => walkThrough[m.MirroredElemTypes].mkString(", ")
              case m: Mirror.SumOf[T] => walkThrough[m.MirroredElemTypes].mkString(", ")
        }
    }
}

trait EnumerateNames[T] {
  def apply: String
}
