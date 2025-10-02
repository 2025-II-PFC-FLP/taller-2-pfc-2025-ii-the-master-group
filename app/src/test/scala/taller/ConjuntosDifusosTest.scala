package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosTest extends AnyFunSuite {

  val conjuntos = new ConjuntosDifusos()
  //  TEST DE CONJUNTO DIFUSO PARA NUMEROS GRANDES
  test("Verificacion de valores pequenios para n") {
    val resultado = conjuntos.grande(d = 1, e = 2)
    val pertenencia = resultado(1)
    assert(pertenencia < 0.3, s"$pertenencia")
  }
}
