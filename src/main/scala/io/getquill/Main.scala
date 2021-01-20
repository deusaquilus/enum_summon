package io.getquill

import scala.reflect._

// File this, as well as the issue with malformed class names
class MainClass {

  // Note: Moving this out to a static area fixes the issue
  enum Shape:
    case Square(width: Int, height: Int) extends Shape
    case Circle(radius: Int) extends Shape

  given EnumerateNames[Int] with {
    def apply: String = "int"
  }
  inline given auto[T]:EnumerateNames[T] = EnumerateNames.derived

  // Also breaks:
  //inline def auto[T]:EnumerateNames[T] = EnumerateNames.derived

  def deriveEnumerateNames[T](using en: EnumerateNames[T]) = en.apply
  
  def run: Unit = println( deriveEnumerateNames[Shape] )
}

object Main {
  def main(args: Array[String]) = {
    new MainClass().run
  }
} 