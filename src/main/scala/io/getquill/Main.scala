package io.getquill

import scala.reflect._

class MainClass {

  enum Shape:
    case Square(width: Int, height: Int) extends Shape
    case Circle(radius: Int) extends Shape

  given EnumerateNames[Int] with {
    def apply: String = "int"
  }
  implicit inline def auto[T]:EnumerateNames[T] = EnumerateNames.derived

  

  def deriveEnumerateNames[T](using en: EnumerateNames[T]) = en.apply
  
  def run: Unit = {
    println( deriveEnumerateNames[Shape] )
  }
}

object Main {
  def main(args: Array[String]) = {
    new MainClass().run
  }
} 