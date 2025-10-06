package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosTest extends AnyFunSuite {

  val conjuntos = new ConjuntosDifusos()
  //  ---TESTS DE CONJUNTO DIFUSO PARA NUMEROS GRANDES---
  test("Verificacion de valores pequenios para n") {
    val resultado = conjuntos.grande(d = 1, e = 2)
    val pertenencia = resultado(1)
    assert(pertenencia < 0.3, s"$pertenencia")
  }

  test("Valores en el rango [0, 1]") {
    val resultado2 = conjuntos.grande(d = 5, e = 3)
    assert(resultado2.values.forall(v => v >= 0.0 && v <= 1.0))
  }

  test("Que el valor de pertcia. aumente con n") {
    val resultado3 = conjuntos.grande(d = 2, e = 2)
    val a = resultado3(10)
    val b = resultado3(100)
    val c = resultado3(1000)
    assert(a < b && b < c, s"Valores: $a, $b, $c")
  }

  test("Comprobar caso hipotetico si d < 1") {
    intercept[IllegalArgumentException] {
      conjuntos.grande(d = 0, e = 2)
    }
  }

  test("Comprobar si n es un valor intermedio") {
    val resultado4 = conjuntos.grande(d = 3, e = 2)
    val pertenencia = resultado4(5000)
    assert(pertenencia > 0.95, s"$pertenencia")
  }
}
//--------------------------------------------------------