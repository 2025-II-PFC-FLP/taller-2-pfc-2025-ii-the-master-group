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

  val conjuntoA: conjuntos.ConjDifuso = conjuntos.grande(d = 5, e = 2)
  val conjuntoB: conjuntos.ConjDifuso = conjuntos.grande(d = 20, e = 4)

  test("Prueba 1: Complemento de un conjunto") {
    val complementoA = conjuntos.complemento(conjuntoA)
    val valorOriginal = conjuntoA(10) // Valor de pertenencia de 10 en A
    val valorComplemento = complementoA(10) // Valor de pertenencia de 10 en ¬A
    assert(valorComplemento == 1.0 - valorOriginal)
  }

  test("Prueba 2: Unión de dos conjuntos") {
    val unionAB = conjuntos.union(conjuntoA, conjuntoB)
    val valorUnion = unionAB(50) // Valor de pertenencia de 50 en A U B
    val valorEsperado = Math.max(conjuntoA(50), conjuntoB(50))
    assert(valorUnion == valorEsperado)
  }

  test("Prueba 3: Intersección de dos conjuntos") {
    val interseccionAB = conjuntos.interseccion(conjuntoA, conjuntoB)
    val valorInterseccion = interseccionAB(25) // Valor de pertenencia de 25 en A ∩ B
    val valorEsperado = Math.min(conjuntoA(25), conjuntoB(25))
    assert(valorInterseccion == valorEsperado)
  }

  test("Prueba 4: Propiedad de la unión con el complemento") {
    val complementoA = conjuntos.complemento(conjuntoA)
    val unionConComplemento = conjuntos.union(conjuntoA, complementoA)
    // Para cualquier elemento 'n', el grado de pertenencia en A U ¬A debe ser >= 0.5
    assert(unionConComplemento.values.forall(_ >= 0.5))
  }

  test("Prueba 5: Propiedad de la intersección con el complemento") {
    val complementoB = conjuntos.complemento(conjuntoB)
    val interseccionConComplemento = conjuntos.interseccion(conjuntoB, complementoB)
    // Para cualquier elemento 'n', el grado de pertenencia en B ∩ ¬B debe ser <= 0.5
    assert(interseccionConComplemento.values.forall(_ <= 0.5))
  }

    val setBase: conjuntos.ConjDifuso = conjuntos.grande(d = 10, e = 2)
    val setIdentico: conjuntos.ConjDifuso = conjuntos.grande(d = 10, e = 2)
    val setSuperconjunto: conjuntos.ConjDifuso = conjuntos.grande(d = 5, e = 2)
    val setDistinto: conjuntos.ConjDifuso = conjuntos.grande(d = 20, e = 3)

    test("Inclusión - Caso Verdadero (un conjunto está contenido en otro)") {
      assert(conjuntos.inclusion(setBase, setSuperconjunto) === true)
    }

    test("Inclusión - Caso Falso (un conjunto no está contenido en otro)") {
      assert(conjuntos.inclusion(setSuperconjunto, setBase) === false)
    }

    test("Igualdad - Caso Verdadero (dos conjuntos idénticos)") {
      // Dos conjuntos generados con los mismos parámetros deben ser iguales.
      assert(conjuntos.igualdad(setBase, setIdentico) === true)
    }

    test("Igualdad - Caso Falso (dos conjuntos distintos)") {
      assert(conjuntos.igualdad(setBase, setDistinto) === false)
    }

    test("Propiedad Reflexiva - Un conjunto siempre se incluye a sí mismo") {
      assert(conjuntos.inclusion(setDistinto, setDistinto) === true)
    }
  }
